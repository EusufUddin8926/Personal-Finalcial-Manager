package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class OthersDetails extends AppCompatActivity {

    private TextView othertexttittle,otherdescription;
    private AppCompatImageView backBtnothersdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_details);

        backBtnothersdetails = findViewById(R.id.backBtnothersdetails);
        othertexttittle = findViewById(R.id.otherstittleId);
        otherdescription = findViewById(R.id.otherdescription);

        String Value = getIntent().getStringExtra("value");

        if(Value.equals("টাকা জমানোর কিছু কৌশল")){
            othertexttittle.setText("টাকা জমানোর কিছু কৌশল");

            otherdescription.setText(R.string.moneytricks);

        } else if(Value.equals("একটু একটু করে সঞ্চয় করার পদ্ধতি")){
            othertexttittle.setText("একটু একটু করে সঞ্চয় করার পদ্ধতি");

            otherdescription.setText(R.string.moneyearntype);

        }else if(Value.equals("টাকা খরচের রেকর্ড রাখুন")){
            othertexttittle.setText("টাকা খরচের রেকর্ড রাখুন");

            otherdescription.setText(R.string.moneyrecord);

        }else if(Value.equals("তরুণদের টাকা জমানোর সহজ কিছু উপায়")){
            othertexttittle.setText("তরুণদের টাকা জমানোর সহজ কিছু উপায়");

            otherdescription.setText(R.string.moneysavingforyoung);

        }else if(Value.equals("সহজে টাকা জমানোর ৫ টি উপায়")){
            othertexttittle.setText("সহজে টাকা জমানোর ৫ টি উপায়");

            otherdescription.setText(R.string.moneyearnfiveway);

        }else if(Value.equals("টাকা জমানোর ৪ উপায়")){
            othertexttittle.setText("টাকা জমানোর ৪ উপায়");

            otherdescription.setText(R.string.moneyearnfourway);

        }else if(Value.equals("সঞ্চয়ই আনে আনন্দ")){
            othertexttittle.setText("সঞ্চয়ই আনে আনন্দ");

            otherdescription.setText(R.string.sonchoybringjoys);

        }else if(Value.equals("উপার্জন ও সঞ্চয়ের সহজ কিছু কৌশল")){
            othertexttittle.setText("উপার্জন ও সঞ্চয়ের সহজ কিছু কৌশল");

            otherdescription.setText(R.string.sonchoyeasytricks);

        }else if(Value.equals("ধনী হওয়ার শক্তিশালী উপায়")){
            othertexttittle.setText("ধনী হওয়ার শক্তিশালী উপায়");

            otherdescription.setText(R.string.exampleforbeingarichman);

        }


        backBtnothersdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),OthersActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),OthersActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}