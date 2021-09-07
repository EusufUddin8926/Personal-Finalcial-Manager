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

public class RevenueCost extends AppCompatActivity {

    //public static ClickListener clickListner;

    private FloatingActionButton revenueButton, expenseButton;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    double revenueAmount, expenseAmount, remainingAmount;
    TextView netBalance, totalrevenuetextView, totalexpenseTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_cost);

        netBalance = findViewById(R.id.netBalance);
        totalrevenuetextView = findViewById(R.id.totalrevenueTextViewTransaction);
        totalexpenseTextView = findViewById(R.id.totalexpenseTextViewTransaction);
        //Button addCustomerButton = findViewById(R.id.addCustomerBtn);

        revenueButton = findViewById(R.id.revenueId);
        expenseButton = findViewById(R.id.expenseId);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser.getUid() + "/JomaKhorochT");
        mDatabase.keepSynced(true);


        // mDatabase.keepSynced(true);
        //get values from firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    try {

                        if (!childSnapShot.child("revenue").getValue().equals("")) {
                            revenueAmount = Double.parseDouble(childSnapShot.child("revenue").getValue().toString()) + revenueAmount;
                        }

                        if (!childSnapShot.child("expense").getValue().equals("")) {
                            expenseAmount = Double.parseDouble(childSnapShot.child("expense").getValue().toString()) + expenseAmount;
                        }

                    } catch (Exception e) {

                    }
                    DecimalFormat df = new DecimalFormat("00.00");
                    if (revenueAmount > expenseAmount) {

                        remainingAmount = revenueAmount - expenseAmount;
                        netBalance.setText("\u09F3" + df.format(remainingAmount));
                        totalrevenuetextView.setText("\u09F3" +df.format(revenueAmount));
                        totalexpenseTextView.setText("\u09F3" +df.format(expenseAmount));
                    }
                    if (expenseAmount > revenueAmount) {

                        remainingAmount = expenseAmount - revenueAmount;
                        netBalance.setText("\u09F3" + "-" +df.format(remainingAmount));
                        totalrevenuetextView.setText("\u09F3" +df.format(revenueAmount));
                        totalexpenseTextView.setText("\u09F3" +df.format(expenseAmount));
                    }
                    if (revenueAmount == expenseAmount) {
                        remainingAmount = expenseAmount - revenueAmount;
                        netBalance.setText("\u09F3" + df.format(remainingAmount));
                        totalrevenuetextView.setText("\u09F3" + df.format(revenueAmount));
                        totalexpenseTextView.setText("\u09F3" + df.format(expenseAmount));
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
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnJK);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.RevenueCost.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*//addCustomerBtn on click Listner
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RevenueCost.this, InsertRevenue.class);
                startActivity(intent1);
                finish();
            }
        });*/

        revenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.RevenueCost.this, InsertRevenue.class);
                startActivity(intent);
                //  Toast.makeText(RevenueCost.this, "Okk", Toast.LENGTH_SHORT).show();
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.RevenueCost.this, InsertExpense.class);
                startActivity(intent);
            }
        });


    }




    @Override
    protected void onStart() {

        super.onStart();


        ArrayList<ViewReportRevenueCostList> lists = new ArrayList<>();
        Collections.reverse(lists);


        FirebaseRecyclerAdapter<ViewReportRevenueCostList, ViewReportRevenueCostListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ViewReportRevenueCostList, ViewReportRevenueCostListViewHolder>
                (ViewReportRevenueCostList.class, R.layout.revenuecost_list, ViewReportRevenueCostListViewHolder.class, mDatabase) {


            @Override
            protected void populateViewHolder(ViewReportRevenueCostListViewHolder viewReportListViewHolder, final ViewReportRevenueCostList viewReportList, final int i) {
                viewReportListViewHolder.setCategory(viewReportList.getCategory());
                viewReportListViewHolder.setDate(viewReportList.getDate());
                viewReportListViewHolder.setRevenue(viewReportList.getRevenue());
                viewReportListViewHolder.setExpense(viewReportList.getExpense());




                viewReportListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String  transaction_id = getRef(i).getKey();

                        mDatabase.child(transaction_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if ((snapshot.exists()) && (snapshot.hasChild("date"))) {
                                    String date = snapshot.child("date").getValue().toString();
                                    String category = snapshot.child("category").getValue().toString();
                                    String revenue = snapshot.child("revenue").getValue().toString();
                                    String expense = snapshot.child("expense").getValue().toString();
                                    String note = snapshot.child("note").getValue().toString();

                                    Intent intent = new Intent(com.example.apersonalfinancialmanager.RevenueCost.this,RCDetailsActivity.class);
                                    intent.putExtra("date",date);
                                    intent.putExtra("category",category);
                                    intent.putExtra("revenue",revenue);
                                    intent.putExtra("expense",expense);
                                    intent.putExtra("note",note);
                                    intent.putExtra("id",transaction_id);
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


    public static class ViewReportRevenueCostListViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ViewReportRevenueCostListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

          /*  itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);*/
        }


        public void setCategory(String category) {
            TextView postCategory= mView.findViewById(R.id.transactionsourceCategory);
            postCategory.setText(category);
        }

        public void setDate(String date) {
            TextView postDate = mView.findViewById(R.id.transactionDate);
            postDate.setText(date);
        }

        public void setRevenue(String amount) {
            TextView postRevenue = mView.findViewById(R.id.transactionRevenue);
            postRevenue.setText(amount);
        }

        public void setExpense(String amount) {
            TextView postExpense = mView.findViewById(R.id.transactionExpense);
            postExpense.setText(amount);
        }



    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }



}