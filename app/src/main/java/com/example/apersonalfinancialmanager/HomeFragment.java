package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private LinearLayout denaPawna, jomaKhoroc, sonchoy, memoRoshid, ayKor, others;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenthome, container, false);

        denaPawna = view.findViewById(R.id.denaPawnaId);
        jomaKhoroc = view.findViewById(R.id.jomaKhorocId);
        sonchoy = view.findViewById(R.id.sonchoyId);
        memoRoshid = view.findViewById(R.id.memoRoshidId);
        ayKor = view.findViewById(R.id.aiKorId);
        others = view.findViewById(R.id.othersId);



        denaPawna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), DenaPawna.class));
            }
        });

        jomaKhoroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), com.example.apersonalfinancialmanager.RevenueCost.class));
            }
        });
        sonchoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), com.example.apersonalfinancialmanager.Savings.class));
            }
        });
        memoRoshid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.example.apersonalfinancialmanager.MemoRoshid.class));
            }
        });
        ayKor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.example.apersonalfinancialmanager.AykorActivity.class));
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OthersActivity.class));
            }
        });



        return view;
    }



}
