package com.example.marlowe.roarsports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.marlowe.roarsports.ActivityMain.NAME;
import static com.example.marlowe.roarsports.ActivityMain.PASSWORD;

public class ActivityChangeEmail extends AppCompatActivity{

    private EditText emailEdit;
    FirebaseAuth auth;
    private Button changeEmail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);
        auth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        String user = auth.getCurrentUser().getUid();



        changeEmail = findViewById(R.id.changeEmail);
        emailEdit = findViewById(R.id.editEmail);


        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //To remove the error on changing the email after registartion would have to be done by changing
                //the shared preference used however changing the email after on login would be unable to be done

                //retrieves details from db
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
                String names = sharedPreferences.getString(NAME, "");
                String passwords = sharedPreferences.getString(PASSWORD, "");

                //authenticates user again
                AuthCredential credential = EmailAuthProvider
                        .getCredential(names, passwords);
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Log.d("TAG", "User re-authenticated.");
                                //Now change your email address \\
                                //----------------Code for Changing Email Address----------\\
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateEmail(emailEdit.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "User email address updated.");
                                                    Toast.makeText(ActivityChangeEmail.this, "User email address updated.", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(ActivityChangeEmail.this, ActivityStatusEdit.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ActivityChangeEmail.this, ActivityEditProfile.class);
        startActivity(intent);
    }

}

