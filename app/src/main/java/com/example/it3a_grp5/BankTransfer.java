package com.example.it3a_grp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BankTransfer extends AppCompatActivity {

    private Button btn_BPI, btn_chinaBank, btn_metroBank, btn_palawan;
    private TextView txt_userid;

    String name;
    String accountAmount;
    String bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);

        btn_BPI = (Button)findViewById(R.id.btn_BPI);
        btn_chinaBank = (Button)findViewById(R.id.btn_chinaBank);
        btn_metroBank = (Button)findViewById(R.id.btn_metroBank);
        btn_palawan = (Button)findViewById(R.id.btn_palawan);

        txt_userid = (TextView)findViewById(R.id.txt_userid);

        name = getIntent().getStringExtra("Name");
        accountAmount = getIntent().getStringExtra("Amount");

        txt_userid.setText(name);

        btn_BPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName = "BPI";
                Intent intent = new Intent(BankTransfer.this, SendMoney.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", accountAmount);
                intent.putExtra("bankName", bankName);
                startActivity(intent);
            }
        });

        btn_chinaBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName = "ChinaBank";
                Intent intent = new Intent(BankTransfer.this, SendMoney.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", accountAmount);
                intent.putExtra("bankName", bankName);
                startActivity(intent);
            }
        });

        btn_metroBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName = "MetroBank";
                Intent intent = new Intent(BankTransfer.this, SendMoney.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", accountAmount);
                intent.putExtra("bankName", bankName);
                startActivity(intent);
            }
        });

        btn_palawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName = "Palawan";
                Intent intent = new Intent(BankTransfer.this, SendMoney.class);
                intent.putExtra("Name", name);
                intent.putExtra("Amount", accountAmount);
                intent.putExtra("bankName", bankName);
                startActivity(intent);
            }
        });
    }
}