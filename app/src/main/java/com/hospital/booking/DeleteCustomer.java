package com.hospital.booking;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteCustomer extends AppCompatActivity implements View.OnClickListener {


    private EditText EditTextEmail;
    private Button EditDeleteButton;
    private ProgressBar ProgressBar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer);


        EditDeleteButton = (Button) findViewById(R.id.DeleteButton);
        EditDeleteButton.setOnClickListener(this);

        ProgressBar = (ProgressBar) findViewById(R.id.progressBar3);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        EditTextEmail = (EditText) findViewById(R.id.DeleteEmail);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {

                    String email = userprofile.email;

                    EditTextEmail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteCustomer.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.DeleteButton:
                PermanentDelete();
                break;
        }
    }

    private void PermanentDelete() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ProgressBar.setVisibility(View.VISIBLE);
        AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteCustomer.this);
        dialog.setTitle("Are You Sure ?");
        dialog.setMessage("Deleting this Account Will result in Completely Removing From System ?");

        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //   progressbar.setVisibility(View.VISIBLE);
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            // progressbar.setVisibility(View.GONE);

                            String email = EditTextEmail.getText().toString().trim();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            Query UserQuery = reference.child("Users").orderByChild("email").equalTo(email);

                            UserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot usersnapshop : snapshot.getChildren()) {
                                        usersnapshop.getRef().removeValue();
                                        EditTextEmail.requestFocus();
                                        EditTextEmail.getText().clear();
                                        EditTextEmail.setVisibility(View.GONE);
                                    }
                                }

                                @Override

                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e(TAG, "onCancelled", error.toException());

                                }
                            });

                            Toast.makeText(DeleteCustomer.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(DeleteCustomer.this, SigninSignup.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


                        } else {
                            //  progressbar.setVisibility(View.GONE);
                            Toast.makeText(DeleteCustomer.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // progressbar.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }
}