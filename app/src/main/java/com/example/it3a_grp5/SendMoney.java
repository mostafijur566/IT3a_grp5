 package com.example.it3a_grp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class SendMoney extends AppCompatActivity {

     private Button btn_sendMoney;
     private EditText txt_amount, txt_accountName, txt_accountNumber;
     private TextView txt_toolbar;

     private DatabaseReference databaseReference;
     private FirebaseUser user;
     private String userID;
     private String name;
     private String bankName;
     private Double accountAmount;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_send_money);

         txt_amount = (EditText)findViewById(R.id.txt_amount);
         btn_sendMoney = (Button)findViewById(R.id.btn_sendMoney);
         txt_accountName = (EditText)findViewById(R.id.txt_accountName);
         txt_accountNumber = (EditText)findViewById(R.id.txt_accountNumber);
         txt_toolbar = (TextView)findViewById(R.id.txt_toolbar);

         databaseReference = FirebaseDatabase.getInstance().getReference("users");

         name = getIntent().getStringExtra("Name");
         accountAmount = Double.parseDouble(getIntent().getStringExtra("Amount"));
         bankName = getIntent().getStringExtra("bankName");

         if(bankName != null)
             txt_toolbar.setText(bankName);

         btn_sendMoney.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 saveData();
             }
         });
     }

     public void saveData()
     {
         String amount = txt_amount.getText().toString().trim();
         String accountName = txt_accountName.getText().toString().trim();
         String accountNumber = txt_accountNumber.getText().toString().trim();

         if(accountNumber.isEmpty())
         {
             txt_accountNumber.setError("Enter account number");
             txt_accountNumber.requestFocus();
             return;
         }

         if(accountName.isEmpty())
         {
             txt_accountName.setError("Enter account name");
             txt_accountName.requestFocus();
             return;
         }

         if(amount.isEmpty())
         {
             txt_amount.setError("No amount entered");
             txt_amount.requestFocus();
             return;
         }
         else
             accountAmount = accountAmount - Double.parseDouble(amount);

         if(accountAmount >= 0)
         {
             //UserInformation userInformation = new UserInformation(Double.toString(accountAmount), name);
             CashInOnly cashIn = new CashInOnly(Double.toString(accountAmount), name, amount);

             user = FirebaseAuth.getInstance().getCurrentUser();
             userID = user.getUid();
             databaseReference.child(userID).setValue(cashIn);
             Intent intent = new Intent(SendMoney.this, AccountActivity.class);
             startActivity(intent);
         }
         else {
             Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show();
             accountAmount = accountAmount +  Double.parseDouble(amount);
         }
     }

 }