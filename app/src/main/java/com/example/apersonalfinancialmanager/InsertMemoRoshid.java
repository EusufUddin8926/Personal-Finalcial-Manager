package com.example.apersonalfinancialmanager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertMemoRoshid extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;


    private AppCompatImageView memopicimage, insertImage;
    private TextInputEditText memotitleEdittext;
    private TextView setdate;
    private ImageView mDisplayDate;
    private AppCompatButton addButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Uri mImageUri;
    private ProgressBar mprogressBar;


    StorageTask uploadtask;
    DatabaseReference reference, mref;
    StorageReference storageReference;
    FirebaseUser currentUser;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_memo_roshid);

        memopicimage = findViewById(R.id.memopicID);
        insertImage = findViewById(R.id.insertimageID);
        memotitleEdittext = findViewById(R.id.memoroshidTittleId);
        mDisplayDate = findViewById(R.id.memodatepickerId);
        setdate = findViewById(R.id.memosetDate);
        addButton = findViewById(R.id.memoroshidInsertId);
        mprogressBar = findViewById(R.id.progress_bar);
        //backBtnViewReport onclick listner
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnInsMemmo);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.InsertMemoRoshid.this, com.example.apersonalfinancialmanager.MemoRoshid.class);
                startActivity(intent);
                finish();
            }
        });

        ////////  get firbase data ////////
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = database.getReference().child("users").child(currentUser.getUid()).child("Memoroshid");
        storageReference = FirebaseStorage.getInstance().getReference("MemoRoshid");

        insertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uploadtask!=null && uploadtask.isInProgress()){
                    Toast.makeText(com.example.apersonalfinancialmanager.InsertMemoRoshid.this, "Uploading Is in progress", Toast.LENGTH_SHORT).show();

                }else {
                    Insertdata();


                }





            }
        });





        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        com.example.apersonalfinancialmanager.InsertMemoRoshid.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makedateString(day, month, year);
                setdate.setText(date);
            }
        };


        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setdate.setText(date.format(new Date()));






    }
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MemoRoshid.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }










    private void uploadImage() {

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(com.example.apersonalfinancialmanager.InsertMemoRoshid.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                imagePick();
            }
        } else {
            imagePick();
        }





    }

    private void imagePick() {

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1920,1080)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
               .start(this);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
          Glide.with(InsertMemoRoshid.this)
                  .load(mImageUri)
                  .centerCrop()
                  .placeholder(R.drawable.amarhisab)
                  .into(memopicimage);



        }*/


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                mImageUri = resultUri;

               /*Glide.with(InsertMemoRoshid.this)
                        .load(mImageUri)
                        .placeholder(R.drawable.amarhisab)
                       .centerInside()
                        .into(memopicimage);*/



                Picasso.get().load(mImageUri)
                        .fit()


                        .placeholder(R.drawable.amarhisab)
                        .into(memopicimage);
            }


        }

    }




    private void Insertdata() {


    String tittle = memotitleEdittext.getText().toString().trim();
    String date = setdate.getText().toString().trim();



        if (mImageUri==null){
            Toast.makeText(com.example.apersonalfinancialmanager.InsertMemoRoshid.this, "Please choice an image", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tittle.equals("")){
            memotitleEdittext.setError(getResources().getString(R.string.iw_memoRosid_catagory));
            memotitleEdittext.requestFocus();
            return;
        }
        



            StorageReference image_path = storageReference.
                    child(currentUser.getUid()).child(String.valueOf(System.currentTimeMillis()+".jpg"));

        uploadtask = image_path.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressBar.setProgress(0);
                                }
                            }, 500);

                           Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                            mytoast.setText(R.string.memoSucces);
                            mytoast.show();


                            com.example.apersonalfinancialmanager.MemoModel memoModel = new com.example.apersonalfinancialmanager.MemoModel(tittle,uri.toString() ,date);
                            String Uploadid = reference.push().getKey();
                            reference.child(Uploadid).setValue(memoModel);



                            startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MemoRoshid.class));
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(com.example.apersonalfinancialmanager.InsertMemoRoshid.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                mprogressBar.setProgress((int) progress);
            }
        });


    }





    private String makedateString(int day, int month, int year){
        return  getDayformat(day) +" "+ getMonthformat(month) +" "+year;
    }

    private String getMonthformat(int s) {
        if(s== 1){
            return "Jan";
        }
        if(s== 2){
            return "Feb";
        }
        if(s== 3){
            return "Mar";
        }
        if(s== 4){
            return "Apr";
        }
        if(s== 5){
            return "May";
        }
        if(s== 6){
            return "Jun";
        }
        if(s== 7){
            return "Jul";
        }
        if(s== 8){
            return "Aug";
        }
        if(s== 9){
            return "Sep";
        }
        if(s== 10){
            return "Oct";
        }
        if(s== 11){
            return "Nov";
        }
        if(s== 12){
            return "Dec";
        }
        return null;
    }

    private String getDayformat(int s) {
        if(s== 1) return "01";
        if(s== 2) return "02";
        if(s== 3) return "03";
        if(s== 4) return "04";
        if(s== 5) return "05";
        if(s== 6) return "06";
        if(s== 7) return "07";
        if(s== 8) return "08";
        if(s== 9) return "09";

        return String.valueOf(s);
    }



}