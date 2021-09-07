package com.example.apersonalfinancialmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OthersAdapter extends RecyclerView.Adapter<com.example.apersonalfinancialmanager.OthersAdapter.OthersViewHolder>{

    private ArrayList<com.example.apersonalfinancialmanager.OthersModel> othersModels;
    private com.example.apersonalfinancialmanager.SingleClickInterface othersClickInterface;

    public OthersAdapter(ArrayList<com.example.apersonalfinancialmanager.OthersModel> othersModels, com.example.apersonalfinancialmanager.SingleClickInterface othersClickInterface) {
        this.othersModels = othersModels;
        this.othersClickInterface = othersClickInterface;
    }

    @NonNull
    @Override
    public OthersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otherssinglelayout,parent,false);
        return new OthersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OthersViewHolder holder, int position) {
        holder.otherstype.setText(othersModels.get(position).getOthertype());
    }

    @Override
    public int getItemCount() {
        return othersModels.size();
    }

    public class OthersViewHolder extends RecyclerView.ViewHolder{

        private TextView otherstype;

        public OthersViewHolder(@NonNull View itemView) {
            super(itemView);

            otherstype = itemView.findViewById(R.id.othersdhoronId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    othersClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
