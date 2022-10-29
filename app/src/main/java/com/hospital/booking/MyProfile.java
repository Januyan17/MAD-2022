package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    private TextView finaldelete,changepassword;
    LinearLayout Reminder ,llcomplaints;

    private CircleImageView profileImageView;
    private DatabaseReference databaseReference;

    FirebaseAuth mAuth;

    private Button logout ,EditProfile;
    private ProgressBar progressbar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

//4
        profileImageView=findViewById(R.id.dp);

//3

        llcomplaints=findViewById(R.id.llcomplaints);
        llcomplaints.setOnClickListener(this);

        Reminder=findViewById(R.id.Reminder);
        Reminder.setOnClickListener(this);

        finaldelete = (TextView) findViewById(R.id.Permanentdelete);
        finaldelete.setOnClickListener(this);

        EditProfile=(Button)findViewById(R.id.EditProfile);
        EditProfile.setOnClickListener(this);

        changepassword=(TextView)findViewById(R.id.textchangepassword);
        changepassword.setOnClickListener(this);

        progressbar = (ProgressBar) findViewById(R.id.progressBar2);
        logout = (Button) findViewById(R.id.SignOut);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                progressbar.setVisibility(View.VISIBLE);
                Intent intent=new Intent(MyProfile.this, com.hospital.booking.CustomerLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView greetingTextview = (TextView) findViewById(R.id.greeting);
        final TextView fullnameTextview = (TextView) findViewById(R.id.fullname);
        final TextView emailTextview = (TextView) findViewById(R.id.emailaddress);
        final TextView ageTextview = (TextView) findViewById(R.id.age);
        final TextView numberTextview=(TextView) findViewById(R.id.number);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                com.hospital.booking.User userprofile = snapshot.getValue(com.hospital.booking.User.class);

                if (userprofile != null) {

                    String fullname = userprofile.username;
                    String email = userprofile.email;
                    String age = userprofile.age;
                    String number=userprofile.phonenumber;

                    greetingTextview.setText("Welcome " + fullname + "!!!");
                    fullnameTextview.setText(fullname);
                    emailTextview.setText(email);
                    ageTextview.setText(age);
                    numberTextview.setText(number);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfile.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();


            }
        });
//1
        getUserInfo();


    }
    //2
    private void getUserInfo() {

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists() && snapshot.getChildrenCount()>0){


                    // String name =snapshot.child("name").getValue().toString();


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
    public void onClick(View v) {

        switch (v.getId()) {
//
            case R.id.Permanentdelete:
                startActivity(new Intent(MyProfile.this, com.hospital.booking.DeleteCustomer.class));

                break;
//
//            case R.id.textchangepassword:
//                startActivity(new Intent(MyProfile.this,MyprofileChangePassword.class));
//                break;
//
//
            case R.id.EditProfile:
                startActivity(new Intent(MyProfile.this,EditMyProfile.class));
                break;

//            case R.id.Reminder:
//                startActivity(new Intent(MyProfile.this,MedicineReminder.class));
//                break;

            case R.id.llcomplaints:
                startActivity(new Intent(MyProfile.this,MainActivity.class));
                break;
//
        }

    }




}