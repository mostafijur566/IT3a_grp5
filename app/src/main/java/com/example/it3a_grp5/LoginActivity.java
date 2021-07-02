package com.example.it3a_grp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email, txt_password;
    private Button login;
    private TextView resetPassword;

    LoadingDialog loadingDialog = new LoadingDialog(this);

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText)findViewById(R.id.txt_password);

        resetPassword = (TextView)findViewById(R.id.resetPassword);

        login = (Button)findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("Reset Password");
                alertDialogBuilder.setMessage("Enter your email to received reset link");
                alertDialogBuilder.setView(resetEmail);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetEmail.getText().toString().trim();

                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void userLogin()
    {
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if(email.isEmpty())
        {
            txt_email.setError("Enter your email");
            txt_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            txt_email.setError("Enter a valid email address");
            txt_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            txt_password.setError("Enter your password");
            txt_password.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            txt_password.setError("Password minimum length should be 6");
            txt_password.requestFocus();
            return;
        }

        loadingDialog.startLoadingDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                loadingDialog.dismissDialog();
                if(task.isSuccessful())
                {
                    if(mAuth.getCurrentUser().isEmailVerified())
                    {
                        Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(LoginActivity.this, "The password or email that your entered is incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}