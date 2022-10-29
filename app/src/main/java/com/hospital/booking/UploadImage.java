package com.hospital.booking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadImage extends AppCompatActivity {


    private CircleImageView profileImageView;
    private Button closeButton,saveButton,profileChangeBtn;


    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String userID;
    private Uri imageUri;
    private String myUri="";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);



        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        storageProfilePicRef=FirebaseStorage.getInstance().getReference().child("Profile pic");

        profileImageView=findViewById(R.id.profile_image);

        profileChangeBtn=findViewById(R.id.change_profile_btn);

        closeButton=findViewById(R.id.btnClose);
        saveButton=findViewById(R.id.btnSave);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UploadImage.this,MyProfile.class));
                finish();

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
                 startActivity(new Intent(UploadImage.this,MyProfile.class));
                 finish();
            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CropImage.activity().setAspectRatio(1,1).start(UploadImage.this);


            }
        });

        getUserinfo();


    }

    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists() && snapshot.getChildrenCount()>0){

                    if(snapshot.hasChild("image")){

                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
//
//            CropImage.ActivityResult result=CropImage.getActivityResult(data);
//            imageUri=result.getUri();
//
//            profileImageView.setImageURI(imageUri);
//
//
//        }
//        else{
//            Toast.makeText(this, "Error Try again", Toast.LENGTH_SHORT).show();
//        }

    }

    private void uploadProfileImage() {

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Set your Profile");
        progressDialog.setMessage("Please Wait , While We are Setting Your data");
        progressDialog.show();

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        if(imageUri !=null){
            final StorageReference fileRef=storageProfilePicRef.child(userID+".jpg");


            uploadTask=fileRef.putFile(imageUri);


            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){

                        throw task.getException();
                    }


                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){

                        Uri downloadUrl =task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String,Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);


                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        progressDialog.dismiss();



                    }

                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "Image Not Selected", Toast.LENGTH_SHORT).show();
        }

    }
}