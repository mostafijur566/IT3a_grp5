package com.example.it3a_grp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_email, txt_password, re_password, txt_name;
    private Button register;

    LoadingDialog loadingDialog = new LoadingDialog(RegisterActivity.this);

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private String cashIn = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText)findViewById(R.id.txt_password);
        re_password = (EditText)findViewById(R.id.re_password);
        txt_name = (EditText)findViewById(R.id.txt_name);

        register = (Button)findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();
        String email = txt_email.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

    }

    public void userRegister()
    {
        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String confirmPassowrd = re_password.getText().toString().trim();

        if(name.isEmpty())
        {
            txt_name.setError("Enter your Full name");
            txt_name.requestFocus();
            return;
        }

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

        if(!password.equals(confirmPassowrd))
        {
            re_password.setError("Not matched");
            re_password.requestFocus();
            return;
        }

        UserInformation userInformation = new UserInformation(cashIn, name);

        loadingDialog.startLoadingDialog();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                loadingDialog.dismissDialog();
                if (task.isSuccessful()) {


                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                userID = user.getUid();
                                databaseReference.child(userID).setValue(userInformation);
                                Toast.makeText(RegisterActivity.this, "An email has been sent. Please verify your email address.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(RegisterActivity.this, "A user already exists with this email address", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RegisterActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}