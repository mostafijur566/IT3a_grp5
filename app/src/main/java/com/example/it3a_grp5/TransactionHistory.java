package com.example.it3a_grp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionHistory extends AppCompatActivity {

    private TextView txt_toolbar, txt_cashIn;

    private String userID;
    private String sendmoney;

    private String name;
    private String amount;

    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        txt_toolbar = (TextView)findViewById(R.id.txt_toolbar);
        txt_cashIn = (TextView)findViewById(R.id.txt_cashIn);

        txt_toolbar.setText("Transaction History");

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CashIn cashIn = snapshot.getValue(CashIn.class);
                if(cashIn!=null)
                {
                    amount = cashIn.getCashIn();
                    txt_cashIn.setText("Your recent transaction amount: "+amount);
                }
                else
                    txt_cashIn.setText("No transaction history");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TransactionHistory.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

    }
}