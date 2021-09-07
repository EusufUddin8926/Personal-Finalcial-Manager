package com.example.apersonalfinancialmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MemoRoshid extends AppCompatActivity implements ClickInterface {

    private FloatingActionButton addrosidButton;
    private RecyclerView mRecyclerView;
    private MemoAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<MemoModel> mUploads;
    FirebaseUser currentUser;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_roshid);
        //backBtnViewReport onclick listner
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnMR);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.MemoRoshid.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addrosidButton = findViewById(R.id.addmemoRoshidId);
        mRecyclerView = findViewById(R.id.detailsrecyclerId);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUploads = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("Memoroshid");
        firebaseStorage = FirebaseStorage.getInstance();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    MemoModel model = postSnapshot.getValue(MemoModel.class);
                    model.setKey(postSnapshot.getKey());
                    mUploads.add(model);
                }

                mAdapter = new MemoAdapter(com.example.apersonalfinancialmanager.MemoRoshid.this, mUploads, com.example.apersonalfinancialmanager.MemoRoshid.this);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addrosidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(com.example.apersonalfinancialmanager.MemoRoshid.this, InsertMemoRoshid.class));
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(),MemoRoshidDetails.class);
        intent.putExtra("image_url", mUploads.get(position).getMemoimage());
        intent.putExtra("title", mUploads.get(position).getTittle());
        intent.putExtra("date", mUploads.get(position).getDate());
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] info ={"Delete","Update"};
        builder.setTitle("Show Action").setItems(info, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){

                    MemoModel selectedItem = mUploads.get(position);
                    String key = selectedItem.getKey();
                    StorageReference reference = firebaseStorage.getReferenceFromUrl(selectedItem.getMemoimage());

                    reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabaseRef.child(key).removeValue();
                            Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                            mytoast.setText(R.string.alertBuilderYesDeleteConfirmation);
                            mytoast.show();


                        }
                    });

                }

            }
        }).setCancelable(true).create().show();

    }
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}