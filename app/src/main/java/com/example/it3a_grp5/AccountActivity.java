package com.example.it3a_grp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    private TextView txt_userid, txt_amount;
    private Button btn_cashIn, btn_sendMoney, btn_bankTransfer, btn_transactionHistory;

    FirebaseUser user;
    DatabaseReference databaseReference;
    private String userID;
    private String name;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txt_userid = (TextView)findViewById(R.id.txt_userid);
        btn_cashIn = (Button)findViewById(R.id.btn_cashIn);
        btn_sendMoney = (Button)findViewById(R.id.btn_sendMoney);
        btn_bankTransfer = (Button)findViewById(R.id.btn_bankTransfer);
        txt_amount = (TextView)findViewById(R.id.txt_amount);
        btn_transactionHistory = (Button)findViewById(R.id.btn_transactionHistory);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserName userName = snapshot.getValue(UserName.class);
                CashIn cashIn = snapshot.getValue(CashIn.class);
                if(userName != null)
                {
                    name = userName.getName();
                    amount = cashIn.getAmount();
                    txt_userid.setText(name);
                    txt_amount.setText(amount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, CashInActivity.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", amount);
                startActivity(intent);
            }
        });

        btn_sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, SendMoney.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", amount);
                startActivity(intent);
            }
        });

        btn_bankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, BankTransfer.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", amount);
                startActivity(intent);
            }
        });

        btn_transactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, TransactionHistory.class);
                startActivity(intent);
            }
        });
    }
}