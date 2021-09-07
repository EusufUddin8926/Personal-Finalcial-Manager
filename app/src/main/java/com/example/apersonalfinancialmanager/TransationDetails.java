package com.example.apersonalfinancialmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TransationDetails extends AppCompatActivity {
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseUser currentUser;
    TextInputLayout rootgiveEdiText,rootgotEdTtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transation_details);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        rootgiveEdiText = findViewById(R.id.rootgiveEditext);
        rootgotEdTtext = findViewById(R.id.rootgotEditext);

        TextInputEditText gaveAMED = findViewById(R.id.gaveamTK);
        TextInputEditText gotAMED = findViewById(R.id.gotamTK);
        TextInputEditText note = findViewById(R.id.notec);
        TextView dateTextview = findViewById(R.id.date);

        TextView toolbarH = findViewById(R.id.dp_details);
        mDisplayDate = findViewById(R.id.datepickerId);
        AppCompatButton deleteButton = findViewById(R.id.deletebuttonID);
        AppCompatButton updateButton = findViewById(R.id.updatebuttonID);
        AppCompatImageView backbtn =findViewById(R.id.backBtnDp);



        final String gaveam = getIntent().getStringExtra("gaveam");
        final String gotam = getIntent().getStringExtra("gotam");
        final String datex = getIntent().getStringExtra("date");
        final String notee = getIntent().getStringExtra("note");
        final String custname = getIntent().getStringExtra("custname");
        final String phonenumber = getIntent().getStringExtra("phonenumber");


        if (gaveam.equals("")){
            rootgiveEdiText.setVisibility(View.GONE);
            toolbarH.setText(R.string.cp_pawna);
        }
        else{
            gaveAMED.setText(gaveam);
        }
        if (gotam.equals("")){
            rootgotEdTtext.setVisibility(View.GONE);
            toolbarH.setText(R.string.cp_dena);
        }
        else{
            gotAMED.setText(gotam);
        }
        dateTextview.setText(datex);
        note.setText(notee);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(com.example.apersonalfinancialmanager.TransationDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makedateString(day, month, year);
                dateTextview.setText(date);
            }
        };
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String transaction_id = getIntent().getStringExtra("id");
               DatabaseReference  dTReference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentUser.getUid()+ "/DP_Customers/"+custname+"/denaPawnaT/").child(transaction_id);

                /*```````````````````````````````````````````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.TransationDetails.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDelete);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dTReference.removeValue();
                        Log.i("Our youGotAmountX ", String.valueOf(phonenumber));

                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesDeleteConfirmation);
                        mytoast.show();

                        Intent intent1 = new Intent(com.example.apersonalfinancialmanager.TransationDetails.this, CustomerPage.class);
                        intent1.putExtra("custname",custname);
                        intent1.putExtra("phonenumber",phonenumber);
                        startActivity(intent1);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````````````````````````````````````````````````*/
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_gaveAM = gaveAMED.getText().toString();
                String new_gotAM = gotAMED.getText().toString();
                String new_note = note.getText().toString();
                String new_date = dateTextview.getText().toString();
                String transaction_id = getIntent().getStringExtra("id");
                String milis = Long.toString(System.currentTimeMillis());

                DatabaseReference  dTReference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentUser.getUid()+ "/DP_Customers/"+custname+"/denaPawnaT/").child(transaction_id);

                DatabaseReference dtUP = FirebaseDatabase.getInstance().getReference("users/"+ currentUser.getUid());

                if (gaveam.equals("")){
                    if(new_gotAM.isEmpty()){
                        rootgotEdTtext.setError(getResources().getString(R.string.iw_got_amount));
                        rootgotEdTtext.requestFocus();
                        return;
                    }
                }
                else if (gotam.equals("")) {
                    if(new_gaveAM.isEmpty()){
                        rootgiveEdiText.setError(getResources().getString(R.string.iw_gave_amount));
                        rootgiveEdiText.requestFocus();
                        return;
                    }
                }




                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.TransationDetails.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderUpdate);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dTReference.removeValue();
                        dtUP.child("/DP_Customers/"+custname+"/denaPawnaT/").child(milis).child("yougave").setValue(new_gaveAM);
                        dtUP.child("/DP_Customers/"+custname+"/denaPawnaT/").child(milis).child("yougot").setValue(new_gotAM);
                        dtUP.child("/DP_Customers/"+custname+"/denaPawnaT/").child(milis).child("note").setValue(new_note);
                        dtUP.child("/DP_Customers/"+custname+"/denaPawnaT/").child(milis).child("date").setValue(new_date);

                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesUpdateConfirmation);
                        mytoast.show();

                        Intent it =new Intent(com.example.apersonalfinancialmanager.TransationDetails.this,CustomerPage.class);
                        it.putExtra("custname",custname);
                        it.putExtra("phonenumber",phonenumber);
                        startActivity(it);
                        finish();
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
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),CustomerPage.class);
                it.putExtra("custname",custname);
                it.putExtra("phonenumber",phonenumber);
                startActivity(it);
                finish();
            }
        });



    }














    private String makedateString(int day, int month, int year){
        return  getDayformat(day) +" "+ getMonthformat(month) +" "+year;
    }

    private String getMonthformat(int s) {
        if(s== 1){
            return "Jan";
        }
        if(s== 2){
            return "Feb";
        }
        if(s== 3){
            return "Mar";
        }
        if(s== 4){
            return "Apr";
        }
        if(s== 5){
            return "May";
        }
        if(s== 6){
            return "Jun";
        }
        if(s== 7){
            return "Jul";
        }
        if(s== 8){
            return "Aug";
        }
        if(s== 9){
            return "Sep";
        }
        if(s== 10){
            return "Oct";
        }
        if(s== 11){
            return "Nov";
        }
        if(s== 12){
            return "Dec";
        }
        return null;
    }

    private String getDayformat(int s) {
        if(s== 1) return "01";
        if(s== 2) return "02";
        if(s== 3) return "03";
        if(s== 4) return "04";
        if(s== 5) return "05";
        if(s== 6) return "06";
        if(s== 7) return "07";
        if(s== 8) return "08";
        if(s== 9) return "09";

        return String.valueOf(s);
    }
   /* @Override
    public void onBackPressed() {
        Intent it = new Intent(getBaseContext(),DenaPawna.class);
        startActivity(it);
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }*/
}