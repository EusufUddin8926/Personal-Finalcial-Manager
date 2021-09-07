package com.example.apersonalfinancialmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Settings extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser currentUser;
    TextView stDP,stJK,stSC,stMR;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        stDP=findViewById(R.id.ST_denapawna);
        stJK=findViewById(R.id.ST_jomakhoroch);
        stSC=findViewById(R.id.ST_sonchoy);
        stMR=findViewById(R.id.ST_memoroshid);
        AppCompatImageView backBtnSettings = findViewById(R.id.backBtnSettings);
        //get data from firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("MemoRoshid").child(currentUser.getUid());
        backBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }
        });
        stDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.Settings.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDeleteDP_data);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DatabaseReference dTReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()+"/DP_Customers");
                        dTReference.removeValue();
                        dTReference.keepSynced(true);
                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        mytoast.setText(R.string.alertBuilderYesDeleteDP_dataConf);
                        mytoast.show();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/
            }
        });
        stJK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.Settings.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDeleteJK_data);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DatabaseReference dTReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()+"/JomaKhorochT");
                        dTReference.removeValue();
                        dTReference.keepSynced(true);
                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        mytoast.setText(R.string.alertBuilderYesDeleteJK_dataConf);
                        mytoast.show();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/
            }
        });
        stSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.Settings.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDeleteSC_data);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DatabaseReference dTReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()+"/SonchoyT");
                        dTReference.removeValue();
                        dTReference.keepSynced(true);
                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        mytoast.setText(R.string.alertBuilderYesDeleteSC_dataConf);
                        mytoast.show();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/
            }
        });
        stMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.Settings.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDeleteMR_data);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DatabaseReference dTReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()+"/Memoroshid");
                        dTReference.removeValue();
                        dTReference.keepSynced(true);
                        //storageReference.delete();
                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        mytoast.setText(R.string.alertBuilderYesDeleteMR_dataConf);
                        mytoast.show();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}