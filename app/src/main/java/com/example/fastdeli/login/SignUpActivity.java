package com.example.fastdeli.login;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.ColorDrawableKt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastdeli.MainActivity;
import com.example.fastdeli.R;
import com.example.fastdeli.model.My_Models;
//import com.example.login.databinding.ActivitySignUpBinding;
//import com.example.login.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    //ActivitySignUpBinding binding;
    EditText edtEmail,edtName,edPassWord,edtphone,edtaddress;
    LinearLayout mLoginBtn;
    Button btnSignUp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    //ProgressDialog progressDialog;
    ProgressBar progressBar;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //
        edtName=findViewById(R.id.edt_name_re);
        edtEmail=findViewById(R.id.edt_email_re);
        edPassWord=findViewById(R.id.edt_pass_re);
        edtphone=findViewById(R.id.edt_phone_re);
        edtaddress=findViewById(R.id.edt_address_re);
        btnSignUp=findViewById(R.id.btn_sign_up);
        mLoginBtn=findViewById(R.id.layout_sign_up);


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainLoginActivity.class));
            finish();
        }
        btnSignUp.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String name = edtName.getText().toString();
            String password = edPassWord.getText().toString();
            String phone = edtphone.getText().toString();
            String address = edtaddress.getText().toString();

            if (TextUtils.isEmpty(email)) {
                edtEmail.setError("Nhập email");
                return;
            }
            if (TextUtils.isEmpty(name)) {
                edtName.setError("Nhập tên bạn");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            //
            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User Create", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("users").document(userID);

                            Map<String, Object>user=new HashMap<>();
                            user.put("fname",name);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("password",password);
                            user.put("address",address);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG","onSuccess:thong tin ca nhan duoc tao"+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(SignUpActivity.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });
    }


}