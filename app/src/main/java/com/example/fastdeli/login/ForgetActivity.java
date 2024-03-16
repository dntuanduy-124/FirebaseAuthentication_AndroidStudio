package com.example.fastdeli.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastdeli.databinding.ActivityForgetBinding;
import com.example.fastdeli.R;
//import com.example.fastdeli.login.ForgetActivity;
//import com.example.login.databinding.ActivityForgetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgetActivity extends AppCompatActivity {
    ActivityForgetBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forget);
        binding=ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Create Your Account");
        progressDialog.setMessage("Please Wait");

        binding.forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.txtEmailFg.getText().toString();
                progressDialog.dismiss();
                if(email.isEmpty()){

                    binding.txtEmailFg.setError("NHập email của bạn!");


                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(ForgetActivity.this,"Hãy kiểm tra trong email của bạn!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetActivity.this,LoginActivity.class));

                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(ForgetActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetActivity.this,LoginActivity.class));

            }
        });

    }
}