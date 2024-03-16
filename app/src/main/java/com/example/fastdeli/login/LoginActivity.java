package com.example.fastdeli.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastdeli.MainActivity;
import com.example.fastdeli.R;
//import com.example.login.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //ActivityLoginBinding binding;
    //    private LinearLayout layoutSignUp;
    FirebaseAuth mAuth;
    FirebaseAuth fAuth;
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn,forgotTextLink;
    FirebaseFirestore firestore;
    ProgressBar progressBar;
    //ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //anh xa
        mEmail=findViewById(R.id.edt_email_si);
        mPassword=findViewById(R.id.edt_pass_si);
        progressBar=findViewById(R.id.progressBar2);
        mLoginBtn=findViewById(R.id.btn_login);
        mCreateBtn=findViewById(R.id.createText);
        forgotTextLink=findViewById(R.id.txtForgetPassRe);

        firestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Nhập email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Nhập mật khẩu của bạn");
                    return;
                }
                if(password.length()<5)
                {
                    mPassword.setError("Mật khẩu phải dài hơn 5 ký tự");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //kiem tra user
                fAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Nhập sai tên người dùng hoặc mật khẩu vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                                }
                            }
                        });
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));

            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Đặt lại mật khẩu ?");
                passwordResetDialog.setMessage("Hãy nhập email của bạn!");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this, "Đổi mật khẩu thành công.Hãy kiểm tra email của bạn!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Lỗi . Email gửi không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }


}
