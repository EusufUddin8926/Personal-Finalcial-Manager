package com.example.apersonalfinancialmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<com.example.apersonalfinancialmanager.ContactAdapter.mViewHolder> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    private Context mContext;
    private LayoutInflater inflater;
    private List<com.example.apersonalfinancialmanager.ModelContacts> mlistContacts;

    public ContactAdapter(Context context, List<com.example.apersonalfinancialmanager.ModelContacts> listContacts)
    {
        mlistContacts = listContacts;

        mContext = context;
    }


    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_contacts,parent,false);
        mViewHolder viewHolder = new mViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        TextView contactName,contactNumber;

        contactName = holder.contactName;
        contactNumber = holder.contactNumber;

        contactName.setText(mlistContacts.get(position).getName());
        contactNumber.setText(mlistContacts.get(position).getNumber());

        final String name = mlistContacts.get(position).getName();
        final String number = mlistContacts.get(position).getNumber();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //store data in firebase
                firebaseAuth = FirebaseAuth.getInstance();
                currentUser = firebaseAuth.getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("users/"+currentUser.getUid());

                reference.child("DP_Customers/"+name).child("name").setValue(name);
                reference.child("DP_Customers/"+name).child("phonenumber").setValue(number);

                Intent intent = new Intent(mContext, com.example.apersonalfinancialmanager.CustomerPage.class);
                intent.putExtra("custname",name);
                intent.putExtra("phonenumber",number);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistContacts.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        TextView contactName,contactNumber;
        CardView cardView;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName  = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);
            cardView = itemView.findViewById(R.id.contacts_CardView);
        }
    }

    public void updateList(List<com.example.apersonalfinancialmanager.ModelContacts> newList)
    {
        mlistContacts.addAll(newList);
        notifyDataSetChanged();
    }
}
