package com.example.apersonalfinancialmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private AppCompatButton updatebutton;
    private TextInputEditText nameEdittxt, emailEdittxt, phoneEdittxt, addressEdittxt;
    private ProgressBar progressBar;

    DatabaseReference reference, mref;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imageView = findViewById(R.id.EditprofileImageId);
        nameEdittxt = findViewById(R.id.userNameEdittextId);
        emailEdittxt = findViewById(R.id.userEmailEditetxtId);
        phoneEdittxt = findViewById(R.id.userphoneEdittextId);
        addressEdittxt = findViewById(R.id.useraddressEdittextId);
        progressBar = findViewById(R.id.homeProgressId);

        progressBar.setVisibility(View.GONE);
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnEditProfile);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ////////  get firbase data ////////
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("users").child("UserInfo");
        // StorageReference storageReference= FirebaseStorage.getInstance().getReference();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = database.getReference().child("users").child(currentUser.getUid()).child("UserInfo");





        updatebutton = findViewById(R.id.UserEditupdateButton);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfile();

                Intent intent = new Intent(com.example.apersonalfinancialmanager.EditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(UserProfileActivity.this, "Select image", Toast.LENGTH_SHORT).show();

                uploadImage();




            }
        });

        setInfromation();

    }

    private void uploadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(com.example.apersonalfinancialmanager.EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                imagePick();
            }
        } else {
            imagePick();
        }

    }

    private void imagePick() {

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1).start(this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressBar.setVisibility(View.VISIBLE);
                Uri resultUri = result.getUri();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();


                StorageReference image_path = storageReference.child("Profile_Image")
                        .child(currentUser.getUid()).child("profile.jpg");
                image_path.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("image").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                             progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Upload Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                             progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                         progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void editProfile() {

        String editname = nameEdittxt.getText().toString().trim();
        String editmail = emailEdittxt.getText().toString().trim();
        String editphone = phoneEdittxt.getText().toString().trim();
        String editaddress = addressEdittxt.getText().toString().trim();




        editname = editname.replace("", "");
        editmail = editmail.replace("", "");
        editphone = editphone.replace("", "");
        editaddress = editaddress.replace("", "");


        String newName = editname.toLowerCase().trim();
        String newMail = editmail.toLowerCase().trim();
        String newPhone = editphone.toLowerCase().trim();
        String newAddress = editaddress.toLowerCase().trim();
        /**/


        //////////// Update User name//////////////
        if (!TextUtils.isEmpty(newName)) {


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("name").setValue(newName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(com.example.apersonalfinancialmanager.EditProfileActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }


            });


        }
        //////////// Update User mail//////////////
        if (!TextUtils.isEmpty(newMail)) {


            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("email").setValue(newMail);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(com.example.apersonalfinancialmanager.EditProfileActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }


            });


        }



            //////////// Update User phone//////////////
        if (!TextUtils.isEmpty(newPhone)) {


            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("phone").setValue(newPhone);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(com.example.apersonalfinancialmanager.EditProfileActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }


            });


        }
            //////////// Update User phone//////////////
        if (!TextUtils.isEmpty(newAddress)) {


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("address").setValue(newAddress);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(com.example.apersonalfinancialmanager.EditProfileActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }


            });


        }


    }


        private void setInfromation () {


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String image = snapshot.child("image").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();

                        nameEdittxt.setText(name);
                        emailEdittxt.setText(email);
                        Glide.with(com.example.apersonalfinancialmanager.EditProfileActivity.this).load(image).centerCrop()
                                .placeholder(R.drawable.customer_logo).into(imageView);

                        phoneEdittxt.setText(phone);
                        addressEdittxt.setText(address);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(com.example.apersonalfinancialmanager.EditProfileActivity.this,  error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),UserProfileActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }

}
