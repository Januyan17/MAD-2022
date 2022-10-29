package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditMyProfile extends AppCompatActivity {


    private Button EditProfile,Editprofilepicture;
//

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

//
        EditProfile = (Button) findViewById(R.id.changeProfile);
//        EditProfile.setOnClickListener(this);

        Editprofilepicture = (Button) findViewById(R.id.editmypicture);
        Editprofilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditMyProfile.this, com.hospital.booking.UploadImage.class));
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final EditText FullnameEditText = (EditText) findViewById(R.id.editTextUsername);
        final EditText PhonenumberEditText = (EditText) findViewById(R.id.editTextTextPhonenumber);
        final EditText AgeEditText = (EditText) findViewById(R.id.editTextTextAge);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                com.hospital.booking.User userprofile = snapshot.getValue(com.hospital.booking.User.class);

                if (userprofile != null) {

                    final String[] fullname = {userprofile.username};
                    final String[] age = {userprofile.age};
                    final String[] number = {userprofile.phonenumber};


                    FullnameEditText.setText(fullname[0]);
                    PhonenumberEditText.setText(number[0]);
                    AgeEditText.setText(age[0]);


                    EditProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isNameChanged() || isPhonenumberchanged() || isAgechanged()) {

//                                Toast.makeText(EditMyProfile.this, "Data Has Been Updated Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditMyProfile.this, "Data is Same Cannot Be Updated", Toast.LENGTH_SHORT).show();
                            }

                        }

                        private boolean isNameChanged() {

                            if (!fullname[0].equals(FullnameEditText.getText().toString())) {

                                reference.child(userID).child("username").setValue(FullnameEditText.getText().toString());
                                fullname[0] = FullnameEditText.getText().toString();

                                Toast.makeText(EditMyProfile.this, "Name Has Been Updated Successfully", Toast.LENGTH_SHORT).show();

                                return true;

                            } else {
                                return false;
                            }

                        }


                        private boolean isPhonenumberchanged() {

                            if (!number[0].equals(PhonenumberEditText.getText().toString())) {

                                reference.child(userID).child("phonenumber").setValue(PhonenumberEditText.getText().toString());
                                number[0] = PhonenumberEditText.getText().toString();
                                Toast.makeText(EditMyProfile.this, "Number Has Been Updated Successfully", Toast.LENGTH_SHORT).show();
                                return true;

                            } else {
                                return false;
                            }
                        }

                        private boolean isAgechanged() {


                            if (!age[0].equals(AgeEditText.getText().toString())) {

                                reference.child(userID).child("age").setValue(AgeEditText.getText().toString());
                                age[0] = AgeEditText.getText().toString();
                                Toast.makeText(EditMyProfile.this, "Age Has Been Updated Successfully", Toast.LENGTH_SHORT).show();
                                return true;

                            } else {
                                return false;
                            }

                        }


                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditMyProfile.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();


            }
        });


    }


}