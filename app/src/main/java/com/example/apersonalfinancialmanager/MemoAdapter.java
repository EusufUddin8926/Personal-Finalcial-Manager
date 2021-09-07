package com.example.apersonalfinancialmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<com.example.apersonalfinancialmanager.MemoAdapter.MemoViewHolder> {

    private Context context;
    private List<com.example.apersonalfinancialmanager.MemoModel> memoModels;
    com.example.apersonalfinancialmanager.MemoModel memoModel;
    private com.example.apersonalfinancialmanager.ClickInterface clickInterface;



    public MemoAdapter(Context context, List<com.example.apersonalfinancialmanager.MemoModel> memoModels, com.example.apersonalfinancialmanager.ClickInterface clickInterface) {
        this.context = context;
        this.memoModels = memoModels;
        this.clickInterface = clickInterface;
    }




    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memo_single_item,parent,false);

        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
       memoModel = memoModels.get(position);
        holder.title.setText(memoModel.getTittle());
        holder.date.setText(memoModel.getDate());



        Picasso.get().load(memoModel.getMemoimage())
                .fit()
                .placeholder(R.drawable.ic_image)
                .into(holder.memoimage);




    }

    @Override
    public int getItemCount() {
        return memoModels.size();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder {

        public TextView title, date;
        public AppCompatImageView memoimage;
        //CardView view;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.memotittleId);
            date = itemView.findViewById(R.id.dateID);
            memoimage = itemView.findViewById(R.id.memorosidimageId);

            // view = itemView.findViewById(R.id.parentlayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickInterface.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });


        }


    }




}
