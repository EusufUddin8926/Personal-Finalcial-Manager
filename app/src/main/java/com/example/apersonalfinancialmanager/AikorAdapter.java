package com.example.apersonalfinancialmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AikorAdapter extends RecyclerView.Adapter<com.example.apersonalfinancialmanager.AikorAdapter.MyViewHolder>{

    private ArrayList<com.example.apersonalfinancialmanager.AikorModel> aikorModelList;
    private com.example.apersonalfinancialmanager.SingleClickInterface aikorClickInterface;


    public AikorAdapter(ArrayList<com.example.apersonalfinancialmanager.AikorModel> aikorModelList, com.example.apersonalfinancialmanager.SingleClickInterface aikorClickInterface) {
        this.aikorModelList = aikorModelList;
        this.aikorClickInterface = aikorClickInterface;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aikorsingleitem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.aikortextView.setText(aikorModelList.get(position).getAikortype());
    }

    @Override
    public int getItemCount() {
        return aikorModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView aikortextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            aikortextView = itemView.findViewById(R.id.aikordhoronId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aikorClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
