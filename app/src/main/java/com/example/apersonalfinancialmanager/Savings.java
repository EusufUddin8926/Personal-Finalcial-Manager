package com.example.apersonalfinancialmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Savings extends AppCompatActivity {

    //public static ClickListener clickListner;

    private FloatingActionButton depositButton, withdrawalButton;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    double depositAmount, withdrawalAmount, remainingAmount,tempNetBal;
    TextView netBalance, totalDepositTextView, totalWithdrawalTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        netBalance = findViewById(R.id.netBalanceSV);
        totalDepositTextView = findViewById(R.id.totaldepositTextViewT);
        totalWithdrawalTextView = findViewById(R.id.totalwithdrawalTextViewT);

        depositButton = findViewById(R.id.depositId);
        withdrawalButton = findViewById(R.id.withdrawalId);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser.getUid() + "/SonchoyT");
        mDatabase.keepSynced(true);


        // mDatabase.keepSynced(true);
        //get values from firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    try {

                        if (!childSnapShot.child("deposit").getValue().equals("")) {
                            depositAmount = Double.parseDouble(childSnapShot.child("deposit").getValue().toString()) + depositAmount;
                        }

                        if (!childSnapShot.child("withdrawal").getValue().equals("")) {
                            withdrawalAmount = Double.parseDouble(childSnapShot.child("withdrawal").getValue().toString()) + withdrawalAmount;

                        }

                    } catch (Exception e) {

                    }
                    DecimalFormat df = new DecimalFormat("00.00");
                    if (depositAmount > withdrawalAmount) {

                        remainingAmount = depositAmount - withdrawalAmount;
                        netBalance.setText("\u09F3" +df.format(remainingAmount));
                        tempNetBal=remainingAmount;
                        totalDepositTextView.setText("\u09F3" +df.format(depositAmount));
                        totalWithdrawalTextView.setText("\u09F3" +df.format(withdrawalAmount));
                    }
                    if (withdrawalAmount > depositAmount) {

                        remainingAmount = withdrawalAmount - depositAmount;
                        netBalance.setText("\u09F3" + "-" +df.format(remainingAmount));
                        tempNetBal=remainingAmount;
                        totalDepositTextView.setText("\u09F3" +df.format(depositAmount));
                        totalWithdrawalTextView.setText("\u09F3" +df.format(withdrawalAmount));

                    }
                    if (depositAmount == withdrawalAmount) {
                        remainingAmount = withdrawalAmount - depositAmount;
                        netBalance.setText("\u09F3" +df.format(remainingAmount));
                        tempNetBal=remainingAmount;
                        totalDepositTextView.setText("\u09F3" +df.format(depositAmount));
                        totalWithdrawalTextView.setText("\u09F3" +df.format(withdrawalAmount));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        recyclerView = findViewById(R.id.viewReportListRecycler);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        //backBtnViewReport onclick listner
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnSV);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), com.example.apersonalfinancialmanager.InsertDeposit.class);
                startActivity(intent);
            }
        });

        withdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), InsertWithdrawal.class);
                String dpamt =String.valueOf(tempNetBal);
                intent.putExtra("dpamt",dpamt);
                startActivity(intent);
                finish();
            }
        });


    }




    @Override
    protected void onStart() {

        super.onStart();


        ArrayList<com.example.apersonalfinancialmanager.ViewSavigsList> lists = new ArrayList<>();
        Collections.reverse(lists);


        FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.ViewSavigsList, ViewSavingsListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.ViewSavigsList, ViewSavingsListViewHolder>
                (com.example.apersonalfinancialmanager.ViewSavigsList.class, R.layout.savings_list, ViewSavingsListViewHolder.class, mDatabase) {


            @Override
            protected void populateViewHolder(ViewSavingsListViewHolder viewSVListViewHolder, final com.example.apersonalfinancialmanager.ViewSavigsList viewSVList, final int i) {
                viewSVListViewHolder.setCategory(viewSVList.getCategory());
                viewSVListViewHolder.setDate(viewSVList.getDate());
                viewSVListViewHolder.setDeposit(viewSVList.getDeposit());
                viewSVListViewHolder.setWithdrawal(viewSVList.getWithdrawal());




                viewSVListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String  transaction_id = getRef(i).getKey();

                        mDatabase.child(transaction_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if ((snapshot.exists()) && (snapshot.hasChild("date"))) {
                                    String date = snapshot.child("date").getValue().toString();
                                    String category = snapshot.child("category").getValue().toString();
                                    String deposit = snapshot.child("deposit").getValue().toString();
                                    String withdrawal = snapshot.child("withdrawal").getValue().toString();
                                    String note = snapshot.child("note").getValue().toString();
                                    String netSavingss= String.valueOf(tempNetBal);

                                    Intent intent = new Intent(getBaseContext(), com.example.apersonalfinancialmanager.DWDetails.class);
                                    intent.putExtra("date",date);
                                    intent.putExtra("category",category);
                                    intent.putExtra("deposit",deposit);
                                    intent.putExtra("withdrawal",withdrawal);
                                    intent.putExtra("note",note);
                                    intent.putExtra("id",transaction_id);
                                    intent.putExtra("netsavings",netSavingss);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    public static class ViewSavingsListViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public ViewSavingsListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


        }

        public void setCategory(String category) {
            TextView postCategory= mView.findViewById(R.id.transactionsourceCategory);
            postCategory.setText(category);
        }

        public void setDate(String date) {
            TextView postDate = mView.findViewById(R.id.transactionDate);
            postDate.setText(date);
        }

        public void setDeposit(String amount) {
            TextView postDeposit = mView.findViewById(R.id.transactionDeposite);
            postDeposit.setText(amount);
        }

        public void setWithdrawal(String amount) {
            TextView postWithdrawal = mView.findViewById(R.id.transactionWithdrawal);
            postWithdrawal.setText(amount);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }



}