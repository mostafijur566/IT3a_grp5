package com.example.it3a_grp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CashInActivity extends AppCompatActivity {

    private EditText txt_amount;
    private Button btn_cashIn;

    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private String name;
    private Double accountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_in);

        txt_amount = (EditText)findViewById(R.id.txt_amount);
        btn_cashIn = (Button)findViewById(R.id.btn_cashIn);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        name = getIntent().getStringExtra("Name");
        accountAmount = Double.parseDouble(getIntent().getStringExtra("Amount"));

        btn_cashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


    public void saveData()
    {
        String amount = txt_amount.getText().toString().trim();

        if(amount.isEmpty())
        {
            txt_amount.setError("No amount entered");
            txt_amount.requestFocus();
            return;
        }
        else
            accountAmount = accountAmount + Double.parseDouble(amount);

        //UserInformation userInformation = new UserInformation(Double.toString(accountAmount), name);
        CashInOnly cashIn = new CashInOnly(Double.toString(accountAmount), name, amount);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        databaseReference.child(userID).setValue(cashIn);
        Intent intent = new Intent(CashInActivity.this, AccountActivity.class);
        startActivity(intent);
    }
}