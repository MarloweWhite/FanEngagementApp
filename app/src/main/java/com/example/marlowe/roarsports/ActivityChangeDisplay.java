package com.example.marlowe.roarsports;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class ActivityChangeDisplay extends AppCompatActivity {

    private EditText firstNameEdit, lastNameEdit;
    private Button change;
    User user;
    FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_display);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();




        firstNameEdit = findViewById(R.id.editFirstName);
        lastNameEdit = findViewById(R.id.editLastName);
        change = findViewById(R.id.changeDisplayName);

        //changing the username
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = mAuth.getCurrentUser().getUid();
                String user = mAuth.getCurrentUser().getEmail();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RoarSports").child("users").child(user_id);
                Map new_post = new HashMap();
                new_post.put("first name", firstNameEdit.getText().toString());
                new_post.put("last name", lastNameEdit.getText().toString());
                new_post.put("email", user);
                databaseReference.setValue(new_post)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ActivityChangeDisplay.this, "Display name updated", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ActivityChangeDisplay.this, ActivityEditProfile.class);
        startActivity(intent);
    }

}


