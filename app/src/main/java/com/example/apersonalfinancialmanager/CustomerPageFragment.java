package com.example.apersonalfinancialmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;


public class CustomerPageFragment extends Fragment {

    public CustomerPageFragment() {
        // Required empty public constructor
    }

    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    double youGaveAmount,youGotAmount,remainingAmount;
    TextView creditAmountTextView,creditStatusTextView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_customer_page, container, false);

        String custname = getArguments().getString("customername");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"/DP_Customers/"+custname+"/denaPawnaT");
         //mDatabase2 = mDatabase.orderByChild("date").equalTo("29 Apr 2021");
        // mDatabase2 = mDatabase.orderByChild("date").limitToLast(2);

        //get data from database
        String cpStatusMotPabo= getResources().getString(R.string.cp_mot_pabo);
        String cpStatusMotDebo= getResources().getString(R.string.cp_mot_debo);
        String cpStatusPrishod= getResources().getString(R.string.cp_porishod);



        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists())
                {
                    LinearLayout linearLayout = view.findViewById(R.id.customerPageLinearLayout);
                    linearLayout.setVisibility(View.INVISIBLE);
                    return;
                }

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren())
                {
                    try {

                        if(!childSnapShot.child("yougave").getValue().equals("")){
                            youGaveAmount = Double.parseDouble(childSnapShot.child("yougave").getValue().toString()) + youGaveAmount;
                        }

                        if(!childSnapShot.child("yougot").getValue().equals(""))
                        {
                            youGotAmount = Double.parseDouble(childSnapShot.child("yougot").getValue().toString()) + youGotAmount;
                        }
                    }
                    catch (Exception e){
                    }
                    DecimalFormat df = new DecimalFormat("00.00");
                    if(youGaveAmount > youGotAmount) {
                        remainingAmount = youGaveAmount - youGotAmount;
                        creditAmountTextView.setText( "\u09F3" +df.format(remainingAmount));
                        creditStatusTextView.setText(cpStatusMotPabo);
                        // creditStatusTextView.setTextColor(getResources().getColor(R.color.dp_pabo));
                    }
                    if(youGotAmount > youGaveAmount)
                    {
                        remainingAmount = youGotAmount - youGaveAmount;
                        creditAmountTextView.setText( "\u09F3" +df.format(remainingAmount));
                        creditStatusTextView.setText(cpStatusMotDebo);
                    }
                    if(youGaveAmount == youGotAmount)
                    {
                        creditStatusTextView.setText(cpStatusPrishod);
                        creditAmountTextView.setText("");
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        recyclerView = view.findViewById(R.id.transactionListRecyclerView);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);



        

        creditAmountTextView = view.findViewById(R.id.credit_amount_customerFragment);
        creditStatusTextView = view.findViewById(R.id.credit_status_customerFragment);


        return view;


    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerTransactionsList,CustomerTransactionListViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<com.example.apersonalfinancialmanager.CustomerTransactionsList, CustomerTransactionListViewHolder>
                (com.example.apersonalfinancialmanager.CustomerTransactionsList.class,R.layout.customer_page_entry_layout,CustomerTransactionListViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(CustomerTransactionListViewHolder customerTransactionListViewHolder, com.example.apersonalfinancialmanager.CustomerTransactionsList customerTransactionsList, int i) {
                customerTransactionListViewHolder.setDate(customerTransactionsList.getDate());
                customerTransactionListViewHolder.setYouGaveAmount(customerTransactionsList.getYougave());
                customerTransactionListViewHolder.setYouGotAmount(customerTransactionsList.getYougot());
                String custname = getArguments().getString("customername");
                String phpneno = getArguments().getString("phonenumber");
                String date= customerTransactionsList.getDate();
                String gaveam= customerTransactionsList.getYougave();
                String gotam= customerTransactionsList.getYougot();
                String note= customerTransactionsList.getNote();
                String  transaction_id = getRef(i).getKey();
                // customerTransactionListViewHolder.mView.onKeyLongPress(new on)
                customerTransactionListViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent = new Intent(getContext(), com.example.apersonalfinancialmanager.TransationDetails.class);
                        intent.putExtra("custname",custname);
                        intent.putExtra("phonenumber",phpneno);
                        intent.putExtra("date",date);
                        intent.putExtra("gaveam",gaveam);
                        intent.putExtra("gotam",gotam);
                        intent.putExtra("note",note);
                        intent.putExtra("id",transaction_id);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CustomerTransactionListViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public CustomerTransactionListViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date)
        {
            TextView postDate = mView.findViewById(R.id.customerFragmentDateColumn);
            postDate.setText(date);
        }

        @SuppressLint("SetTextI18n")
        public void setYouGaveAmount(String amount)
        {
            TextView postYouGaveAmount = mView.findViewById(R.id.customerFragmentYouGave);

            if(amount.isEmpty())
            {
                postYouGaveAmount.setText("");
                return;
            }
            postYouGaveAmount.setText("\u09F3"+ amount);
            postYouGaveAmount.setTextColor(mView.getResources().getColor(R.color.dp_debo));
        }

        @SuppressLint("SetTextI18n")
        public void setYouGotAmount(String amount)
        {
            TextView postYouGotAmount = mView.findViewById(R.id.customerFragmentYouGot);

            if(amount.isEmpty())
            {
                postYouGotAmount.setText("");
                return;
            }

            postYouGotAmount.setText("\u09F3"+amount);
            postYouGotAmount.setTextColor(mView.getResources().getColor(R.color.dp_pabo));
        }
    }

}