package com.example.apersonalfinancialmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class DenaPawnaFragment extends Fragment {

    private DatabaseReference mDatabase,mDatabase2,mDatabaseX,mDatabaseCC;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    double youGaveAmount,youGotAmount,remainingAmount=0,uGivePreAmount=0,uGetPreAmount=0;
    double remainingAmountX=0,youGaveAmountX,youGotAmountX,FyouGaveAmountX,FyouGotAmountX;


   //TextView uWillGiveTextView,uWillGetTextView;
    TextView uWillGiveTextView2,uWillGetTextView2,netBalance;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"/DP_Customers");

        View view = inflater.inflate(R.layout.fragment_denapawna,container,false);

        //searchView implementation
        searchView = view.findViewById(R.id.searchCustomer);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSerach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSerach(newText);
                return false;
            }


        });


       // uWillGiveTextView = view.findViewById(R.id.uWillGiveTextView);
       // uWillGetTextView = view.findViewById(R.id.uWillGetTextView);
        uWillGiveTextView2 = view.findViewById(R.id.uWillGiveTextView2);
        uWillGetTextView2 = view.findViewById(R.id.uWillGetTextView2);
        netBalance = view.findViewById(R.id.dpnetbalance);

        recyclerView = view.findViewById(R.id.customerListRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);





        return view;

    }

    private void firebaseSerach(String searchText)
    {
        Query firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerList,CustomerListViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerList, CustomerListViewHolder>
                        (com.example.apersonalfinancialmanager.CustomerList.class,R.layout.customer_list_view,CustomerListViewHolder.class,firebaseSearchQuery) {
                    @Override
                    protected void populateViewHolder(CustomerListViewHolder customerListViewHolder, final com.example.apersonalfinancialmanager.CustomerList customerList, int i) {

                        customerListViewHolder.setName(customerList.getName());

                        customerListViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String custname = customerList.getName();
                                String phonenumber = customerList.getPhonenumber();

                                Intent intent = new Intent(getContext(),CustomerPage.class);
                                intent.putExtra("custname",custname);
                                intent.putExtra("phonenumber",phonenumber);
                                startActivity(intent);
                            }
                        });

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        makeDashboard();

        remainingAmount = 0;
        youGaveAmount = 0;
        youGotAmount = 0;

        uGetPreAmount = 0;
        uGivePreAmount = 0;

        FyouGaveAmountX=0;
        FyouGotAmountX=0;

        String statusPabo= getResources().getString(R.string.dp_pabo);
       // Log.i("pabooooooooooooooo", String.valueOf(statusPabo));
        String statusDebo= getResources().getString(R.string.dp_debo);
       // Log.i("deboooooooooooo", String.valueOf(statusDebo));
        String statusPorishod= getResources().getString(R.string.dp_porishod);

        TextView postStatus = getView().findViewById(R.id.credit_status);




        FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerList,CustomerListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerList, CustomerListViewHolder>
                (com.example.apersonalfinancialmanager.CustomerList.class,R.layout.customer_list_view,CustomerListViewHolder.class,mDatabase) {

            private List<com.example.apersonalfinancialmanager.CustomerList> exampleList;
            private  List<com.example.apersonalfinancialmanager.CustomerList> exampleListFull;

            @Override
            protected void populateViewHolder(final CustomerListViewHolder customerListViewHolder, final com.example.apersonalfinancialmanager.CustomerList customerList, final int i) {
                customerListViewHolder.setName(customerList.getName());
                final String custName = customerList.getName();

                customerListViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String custname = customerList.getName();
                        String phonenumber = customerList.getPhonenumber();
                        String Transaction_id = getRef(i).getKey();

                        Intent intent = new Intent(getContext(),CustomerPage.class);
                        intent.putExtra("custname",custname);
                        intent.putExtra("phonenumber",phonenumber);
                        intent.putExtra("Transaction_id",Transaction_id);
                        startActivity(intent);
                    }
                });

                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"/DP_Customers/"+custName+"/denaPawnaT");

                //get data from database



                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                                if(childSnapShot.exists()) {

                                    if (!(childSnapShot.child("yougave").getValue() == null)) {

                                        if(childSnapShot.child("yougave").getValue().toString().length() > 0) {
                                            youGaveAmount = Double.parseDouble(childSnapShot.child("yougave").getValue().toString()) + youGaveAmount;

                                        }
                                    }

                                    if (!(childSnapShot.child("yougot").getValue() == null)) {
                                        if (childSnapShot.child("yougot").getValue().toString().length() > 0) {
                                            youGotAmount = Double.parseDouble(childSnapShot.child("yougot").getValue().toString()) + youGotAmount;
                                        }
                                    }
                                }
                            }


                            DecimalFormat df = new DecimalFormat("00.00");

                            if (youGaveAmount > youGotAmount) {
                                remainingAmount = youGaveAmount - youGotAmount;
                                customerListViewHolder.setAmount("\u09F3" + df.format(remainingAmount));

                                customerListViewHolder.setCreditStatus(statusPabo);
                            }
                            if (youGotAmount > youGaveAmount) {
                                remainingAmount = youGotAmount - youGaveAmount;
                                customerListViewHolder.setAmount("\u09F3" + df.format(remainingAmount));
                              //  postStatus.setTextColor(ContextCompat.getColor(getActivity(),R.color.dp_debo));

                                customerListViewHolder.setCreditStatus(statusDebo);
                            }
                            if (youGaveAmount == youGotAmount) {
                                customerListViewHolder.setAmount("");
                               // postStatus.setTextColor(ContextCompat.getColor(getActivity(),R.color.dp_porishid));
                                customerListViewHolder.setCreditStatus(statusPorishod);
                            }

                            if (customerListViewHolder.getCreditStatus() == statusDebo) {
                                setUGiveCreditAmount(remainingAmount);
                                remainingAmount = 0;
                                youGaveAmount = 0;
                                youGotAmount = 0;
                            }

                            if (customerListViewHolder.getCreditStatus() == statusPabo) {
                                setUGetCreditAmount(remainingAmount);
                                remainingAmount = 0;
                                youGaveAmount = 0;
                                youGotAmount = 0;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void makeDashboard() {

        mDatabaseCC = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"/DP_Customers/");
      //  Log.i("mmmmmmmmmmm", String.valueOf(mDatabaseCC));

        youGaveAmountX = 0;
        youGotAmountX = 0;

        //get data from database

        mDatabaseCC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                    String cuNamee = dataSnapshot1.child("name").getValue(String.class);



                    //////////////////////////////////////////////


                    mDatabaseX = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"/DP_Customers/"+cuNamee);
                    //get data from database
                    mDatabaseX.addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                remainingAmountX = 0;
                                youGaveAmountX = 0;
                                youGotAmountX = 0;
                                for (DataSnapshot childSnapShot : dataSnapshot.child("denaPawnaT").getChildren()) {
                                    if (childSnapShot.exists()) {


                                        if (!(childSnapShot.child("yougave").getValue() == null)) {
                                            if (childSnapShot.child("yougave").getValue().toString().length() > 0) {
                                                youGaveAmountX = Double.parseDouble(childSnapShot.child("yougave").getValue().toString()) + youGaveAmountX;
                                            }
                                        }
                                        if (!(childSnapShot.child("yougot").getValue() == null)) {
                                            if (childSnapShot.child("yougot").getValue().toString().length() > 0) {
                                                youGotAmountX = Double.parseDouble(childSnapShot.child("yougot").getValue().toString()) + youGotAmountX;
                                            }
                                        }
                                    }
                                }
                                DecimalFormat df = new DecimalFormat("00.00");
                                if (youGaveAmountX > youGotAmountX) {
                                    remainingAmountX = youGaveAmountX - youGotAmountX;
                                   // youGaveAmountX = remainingAmountX + youGaveAmountX;
                                    FyouGaveAmountX = FyouGaveAmountX+remainingAmountX;

                                    uWillGiveTextView2.setText("\u09F3" +df.format(FyouGaveAmountX));
                                }
                                if (youGotAmountX > youGaveAmountX) {
                                    remainingAmountX = youGotAmountX - youGaveAmountX;
                                  //  youGotAmountX = remainingAmountX + youGotAmountX;

                                    FyouGotAmountX = FyouGotAmountX+ remainingAmountX;
                                    //  Log.i("Our youGotAmountX ", String.valueOf(youGotAmountX));
                                    // Log.i("Our FyouGotAmountX ", String.valueOf(FyouGotAmountX));
                                    uWillGetTextView2.setText("\u09F3" +df.format(FyouGotAmountX));
                                }
                                if (youGaveAmountX == youGotAmountX) {
                                    //Nothing todo
                                }
                                double netBal= FyouGaveAmountX-FyouGotAmountX;
                                netBalance.setText("\u09F3" +df.format(netBal));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //////////////////////////////////////////////


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static  class CustomerListViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public CustomerListViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setName(String title)
        {
            TextView postName = mView.findViewById(R.id.customerName);
            postName.setText(title);
        }

        public void setAmount(String amount)
        {
            TextView postAmount = mView.findViewById(R.id.customer_amount);
            postAmount.setText(amount);
        }


        //
        public void setCreditStatus(String status )
        {

            TextView postStatus = mView.findViewById(R.id.credit_status);
            String statusPabo= mView.getResources().getString(R.string.dp_pabo);
            String statusDebo= mView.getResources().getString(R.string.dp_debo);
            String statusPorishod= mView.getResources().getString(R.string.dp_porishod);

           if(status.equals(statusPabo)){

               //postStatus.setTextColor(Color.parseColor("#09A906"));
               // postStatus.setTextColor(ContextCompat.getColor(mView.getResources().getColor(,R.color.dp_pabo)));
               postStatus.setTextColor(mView.getResources().getColor(R.color.dp_pabo));

            }
            else if(status.equals(statusDebo)){

               postStatus.setTextColor(mView.getResources().getColor(R.color.dp_debo));
            }
            else if(status.equals(statusPorishod)){
               postStatus.setTextColor(mView.getResources().getColor(R.color.dp_porishid));

            }
            postStatus.setText(status);

        }

        public String getCreditStatus()
        {
            TextView postStatus = mView.findViewById(R.id.credit_status);
            return postStatus.getText().toString();
        }
    }

    public void setUGiveCreditAmount(double amount)
    {
        uGivePreAmount = uGivePreAmount + amount;
        //uWillGiveTextView.setText("\u09F3" + Integer.toString(uGivePreAmount));
    }

    public void setUGetCreditAmount(double amount)
    {
        uGetPreAmount  = uGetPreAmount + amount;
       // uWillGetTextView.setText("\u09F3" + Integer.toString(uGetPreAmount));
    }
}