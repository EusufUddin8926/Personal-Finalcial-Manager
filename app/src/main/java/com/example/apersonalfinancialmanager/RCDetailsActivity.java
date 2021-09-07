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

import java.util.Calendar;

public class RCDetailsActivity extends AppCompatActivity {
    private TextInputEditText revenueEditeText,expenseEditeText,categoryEditeText,noteEditeText;
    private TextInputLayout rootrevenueEdittext,rootexpenseeEdittext,rootcategoryED,rootnoteED;
    private TextView dateTextview, toolbarH;
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_c_details);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // mDatabase = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser.getUid() + "/JomaKhorochT");


        revenueEditeText = findViewById(R.id.depositEdittext);
        expenseEditeText = findViewById(R.id.withdrawalEdittext);
        categoryEditeText = findViewById(R.id.categoryEdittext);
        dateTextview = findViewById(R.id.dateTextview);
        toolbarH = findViewById(R.id.viewJKHeading);


        noteEditeText = findViewById(R.id.noteEdittext);
        mDisplayDate = findViewById(R.id.datepickerId);
        AppCompatButton deleteButton = findViewById(R.id.deletebuttonID);
        AppCompatButton updateButton = findViewById(R.id.updatebuttonID);
        AppCompatImageView backbtn =findViewById(R.id.backBtnDWD);
        rootrevenueEdittext = findViewById(R.id.rootdepositEditext);
        rootexpenseeEdittext = findViewById(R.id.rootewithdrawalEditext);
        rootcategoryED = findViewById(R.id.rootcategoryED);
        rootnoteED = findViewById(R.id.rootnoteED);

        //get data from revenue cost activity

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String category = intent.getStringExtra("category");
        String revenue = intent.getStringExtra("revenue");
        String expense = intent.getStringExtra("expense");
        String note = intent.getStringExtra("note");


        ///set data with this activity

        if (revenue.equals("")){

            rootrevenueEdittext.setVisibility(View.GONE);
            toolbarH.setText(R.string.transactions_expense);


        }else {
            revenueEditeText.setText(revenue);
            rootcategoryED.setHint(R.string.revenuecategory);
            rootnoteED.setHint(R.string.noterevenuet);

        }

        if (expense.equals("")){
            rootexpenseeEdittext.setVisibility(View.GONE);
            toolbarH.setText(R.string.transactions_revenue);

        }else {
            expenseEditeText.setText(expense);
            rootcategoryED.setHint(R.string.expensecategory);
            rootnoteED.setHint(R.string.noteexpenset);
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
                        com.example.apersonalfinancialmanager.RCDetailsActivity.this,
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
                        .child(currentUser.getUid()+ "/JomaKhorochT").child(transaction_id);

                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.RCDetailsActivity.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDelete);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dTReference.removeValue();

                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesDeleteConfirmation);
                        mytoast.show();

                        Intent intent1 = new Intent(com.example.apersonalfinancialmanager.RCDetailsActivity.this, com.example.apersonalfinancialmanager.RevenueCost.class);
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
                String nrevenue = revenueEditeText.getText().toString();
                String nexpense = expenseEditeText.getText().toString();
                String ncategory = categoryEditeText.getText().toString();
                String nnote = noteEditeText.getText().toString();
                String ndate = dateTextview.getText().toString();
                String transaction_id = getIntent().getStringExtra("id");
                String milis = Long.toString(System.currentTimeMillis());

                DatabaseReference  dTReference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentUser.getUid()+ "/JomaKhorochT").child(transaction_id);
                DatabaseReference dtUP = FirebaseDatabase.getInstance().getReference("users/"+ currentUser.getUid());

                if (revenue.equals("")){
                    if(ncategory.isEmpty()){
                        categoryEditeText.setError(getResources().getString(R.string.iw_expence_catagory));
                        categoryEditeText.requestFocus();
                        return;
                    }
                     else if(nexpense.isEmpty()){
                        rootexpenseeEdittext.setError(getResources().getString(R.string.iw_expence_amount));
                        rootexpenseeEdittext.requestFocus();
                        return;
                    }
                }
                else if (expense.equals("")) {
                    if(ncategory.isEmpty()){
                        categoryEditeText.setError(getResources().getString(R.string.iw_revenue_catagory));
                        categoryEditeText.requestFocus();
                        return;
                    }
                    else if(nrevenue.isEmpty()){
                        rootrevenueEdittext.setError(getResources().getString(R.string.iw_revenue_amount));
                        rootrevenueEdittext.requestFocus();
                        return;
                    }
                }

                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.RCDetailsActivity.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderUpdate);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                dTReference.removeValue();
                                dtUP.child("JomaKhorochT").child(milis).child("revenue").setValue(nrevenue);
                                dtUP.child("JomaKhorochT").child(milis).child("expense").setValue(nexpense);
                                dtUP.child("JomaKhorochT").child(milis).child("category").setValue(ncategory);
                                dtUP.child("JomaKhorochT").child(milis).child("note").setValue(nnote);
                                dtUP.child("JomaKhorochT").child(milis).child("date").setValue(ndate);

                                Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                                mytoast.setText(R.string.alertBuilderYesUpdateConfirmation);
                                mytoast.show();

                                Intent it =new Intent(com.example.apersonalfinancialmanager.RCDetailsActivity.this, com.example.apersonalfinancialmanager.RevenueCost.class);
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
                Intent it = new Intent(getApplicationContext(), com.example.apersonalfinancialmanager.RevenueCost.class);
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
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.RevenueCost.class));
       // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}