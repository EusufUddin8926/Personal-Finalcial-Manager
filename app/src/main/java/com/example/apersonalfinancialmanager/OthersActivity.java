package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OthersActivity extends AppCompatActivity implements com.example.apersonalfinancialmanager.SingleClickInterface {
    private RecyclerView otherrecyclerView;
    String[] othertype;
    private ArrayList<com.example.apersonalfinancialmanager.OthersModel> othersModelArrayList;
    private com.example.apersonalfinancialmanager.OthersModel Model;
    private com.example.apersonalfinancialmanager.OthersAdapter othersAdapter;
    private AppCompatImageView backBtnothers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        othertype = getResources().getStringArray(R.array.othersType);


        othersModelArrayList = new ArrayList<>();
        backBtnothers = findViewById(R.id.backBtnothers);
        otherrecyclerView = findViewById(R.id.otherrecyclerId);


        for(int i = 0; i<othertype.length;i++){

            Model = new com.example.apersonalfinancialmanager.OthersModel(othertype[i]);
            othersModelArrayList.add(Model);

        }

        othersAdapter = new com.example.apersonalfinancialmanager.OthersAdapter(othersModelArrayList, com.example.apersonalfinancialmanager.OthersActivity.this);
        otherrecyclerView.setLayoutManager(new LinearLayoutManager(com.example.apersonalfinancialmanager.OthersActivity.this));
        otherrecyclerView.setHasFixedSize(true);
        otherrecyclerView.setAdapter(othersAdapter);

        backBtnothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MainActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onItemClick(int position) {

        String value= othertype[position];
        Intent intent = new Intent(getBaseContext(), com.example.apersonalfinancialmanager.OthersDetails.class);
        intent.putExtra("value",value);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }




}