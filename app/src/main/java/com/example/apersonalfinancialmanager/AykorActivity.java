 package com.example.apersonalfinancialmanager;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.AppCompatImageView;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import java.util.ArrayList;

 public class AykorActivity extends AppCompatActivity implements com.example.apersonalfinancialmanager.SingleClickInterface {

     private ArrayList<com.example.apersonalfinancialmanager.AikorModel> aikorModel;
     private com.example.apersonalfinancialmanager.AikorModel Model;
     private RecyclerView recyclerView;
     private AikorAdapter aikorAdapter;
     String[] aikortype;
     private AppCompatImageView backBtnaikor;
     private TextView aikornirdesikaviewId,aikorporipotroviewdId;




     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_aykor);
         aikortype= getResources().getStringArray(R.array.aikorType);

         recyclerView = findViewById(R.id.aikorrecyclerId);
         backBtnaikor = findViewById(R.id.backBtnaikor);
         aikornirdesikaviewId = findViewById(R.id.aikornirdesikaviewId);
         aikorporipotroviewdId = findViewById(R.id.aikorporipotroviewdId);


         aikorModel = new ArrayList<>();



         for(int i = 0; i<aikortype.length;i++){

             Model = new com.example.apersonalfinancialmanager.AikorModel(aikortype[i]);
             aikorModel.add(Model);

         }

         aikorAdapter = new AikorAdapter(aikorModel, com.example.apersonalfinancialmanager.AykorActivity.this);
         recyclerView.setLayoutManager(new LinearLayoutManager(com.example.apersonalfinancialmanager.AykorActivity.this));
         recyclerView.setHasFixedSize(true);
         recyclerView.setAdapter(aikorAdapter);


         aikornirdesikaviewId.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent  = new Intent(com.example.apersonalfinancialmanager.AykorActivity.this,
                         com.example.apersonalfinancialmanager.NirdesikaPdfView.class);
                 startActivity(intent);

             }
         });

         aikorporipotroviewdId.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent  = new Intent(com.example.apersonalfinancialmanager.AykorActivity.this,
                         com.example.apersonalfinancialmanager.PoripotroPdfView.class);
                 startActivity(intent);


             }


         });


         backBtnaikor.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getBaseContext(),MainActivity.class));
                 finish();
             }
         });

     }


     @Override
     public void onItemClick(int position) {

         String value= aikortype[position];
         Intent intent = new Intent(getBaseContext(),AikorDetails.class);
         intent.putExtra("value",value);
         startActivity(intent);
     }

     @Override
     public void onBackPressed() {
         startActivity(new Intent(getBaseContext(),MainActivity.class));
         // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
         finish();
     }
 }