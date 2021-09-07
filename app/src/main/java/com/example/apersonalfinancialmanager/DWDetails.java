package com.example.apersonalfinancialmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.text.DecimalFormat;
import java.util.Calendar;

public class DWDetails extends AppCompatActivity {
    private TextInputEditText depositEditeText,withdrawalEditeText,categoryEditeText,noteEditeText;
    private TextInputLayout depositEdittext,rootWithdrawalEdittext,rootcategoryED,rootnoteED;
    private TextView dateTextview, toolbarH;
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_w_details);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        depositEditeText = findViewById(R.id.depositEdittext);
        withdrawalEditeText = findViewById(R.id.withdrawalEdittext);
        categoryEditeText = findViewById(R.id.categoryEdittext);
        dateTextview = findViewById(R.id.dateTextview);
        toolbarH = findViewById(R.id.viewJKHeading);


        noteEditeText = findViewById(R.id.noteEdittext);
        mDisplayDate = findViewById(R.id.datepickerId);
        AppCompatButton deleteButton = findViewById(R.id.deletebuttonID);
        AppCompatButton updateButton = findViewById(R.id.updatebuttonID);
        AppCompatImageView backbtn =findViewById(R.id.backBtnDWD);
        depositEdittext = findViewById(R.id.rootdepositEditext);
        rootWithdrawalEdittext = findViewById(R.id.rootewithdrawalEditext);
        rootcategoryED = findViewById(R.id.rootcategoryED);
        rootnoteED = findViewById(R.id.rootnoteED);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String category = intent.getStringExtra("category");
        String deposit = intent.getStringExtra("deposit");
        String withdrawal = intent.getStringExtra("withdrawal");
        String note = intent.getStringExtra("note");


        ///set data with this activity

        if (deposit.equals("")){

            depositEdittext.setVisibility(View.GONE);
            toolbarH.setText(R.string.transactions_withdrawal);



        }else {
            depositEditeText.setText(deposit);
            rootcategoryED.setHint(R.string.depositcategory);
            rootnoteED.setHint(R.string.notedeposit);

        }

        if (withdrawal.equals("")){
            rootWithdrawalEdittext.setVisibility(View.GONE);
            toolbarH.setText(R.string.transactions_deposits);

        }else {
            withdrawalEditeText.setText(withdrawal);
            rootcategoryED.setHint(R.string.withdrawalcategory);
            rootnoteED.setHint(R.string.notewithdrawal);
        }



        categoryEditeText.setText(category);
        dateTextview.setText(date);
        noteEditeText.setText(note);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        com.example.apersonalfinancialmanager.DWDetails.this,
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
                DatabaseReference dTReference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentUser.getUid()+ "/SonchoyT").child(transaction_id);

                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.DWDetails.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDelete);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dTReference.removeValue();

                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesDeleteConfirmation);
                        mytoast.show();

                        Intent intent1 = new Intent(getBaseContext(), Savings.class);
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
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/


            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ndeposit = depositEditeText.getText().toString();
                String nwithdrawal = withdrawalEditeText.getText().toString();
                String ncategory = categoryEditeText.getText().toString();
                String nnote = noteEditeText.getText().toString();
                String ndate = dateTextview.getText().toString();
                String transaction_id = getIntent().getStringExtra("id");
                String milis = Long.toString(System.currentTimeMillis());

                DatabaseReference  dTReference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentUser.getUid()+ "/SonchoyT").child(transaction_id);
                DatabaseReference dtUP = FirebaseDatabase.getInstance().getReference("users/"+ currentUser.getUid());

                if (deposit.equals("")){
                    String dpamt = getIntent().getStringExtra("netsavings");
                    String wtamt = withdrawalEditeText.getText().toString();
                    if(ncategory.isEmpty()){
                        categoryEditeText.setError(getResources().getString(R.string.iw_withdrawal_catagory));
                        categoryEditeText.requestFocus();
                        return;
                    }
                    else if(nwithdrawal.isEmpty()){

                        rootWithdrawalEdittext.setError(getResources().getString(R.string.iw_withdrawal_amount));
                        rootWithdrawalEdittext.requestFocus();
                        return;
                    }



                    DecimalFormat df = new DecimalFormat("00.00");
                    double idpamt= Double.parseDouble(dpamt);
                    double iwtamt= Double.parseDouble(wtamt);
                    if(iwtamt>idpamt)
                    {
                        Toast mytoast0 = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                        String ertxt= getResources().getString(R.string.iw_withdrawal_amount_Limit_toast);
                        mytoast0.setText(ertxt+df.format(idpamt)+"\u09F3");
                        mytoast0.show();
                        withdrawalEditeText.setError(getResources().getString(R.string.iw_withdrawal_amount_Limit)+df.format(idpamt)+"\u09F3" );
                        withdrawalEditeText.requestFocus();
                        return;
                    }
                }
                else if (withdrawal.equals("")) {
                    if(ncategory.isEmpty()){
                        categoryEditeText.setError(getResources().getString(R.string.iw_deposit_catagory));
                        categoryEditeText.requestFocus();
                        return;
                    }
                    else if(ndeposit.isEmpty()){
                        depositEdittext.setError(getResources().getString(R.string.iw_deposit_amount));
                        depositEdittext.requestFocus();
                        return;
                    }
                }


                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.DWDetails.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderUpdate);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dTReference.removeValue();
                        dtUP.child("SonchoyT").child(milis).child("deposit").setValue(ndeposit);
                        dtUP.child("SonchoyT").child(milis).child("withdrawal").setValue(nwithdrawal);
                        dtUP.child("SonchoyT").child(milis).child("category").setValue(ncategory);
                        dtUP.child("SonchoyT").child(milis).child("note").setValue(nnote);
                        dtUP.child("SonchoyT").child(milis).child("date").setValue(ndate);

                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesUpdateConfirmation);
                        mytoast.show();

                        Intent it =new Intent(getBaseContext(),Savings.class);
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
                Intent it = new Intent(getApplicationContext(),Savings.class);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Savings.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}