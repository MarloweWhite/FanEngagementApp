package com.example.marlowe.roarsports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.marlowe.roarsports.ActivityMain.NAME;
import static com.example.marlowe.roarsports.ActivityMain.PASSWORD;

public class ActivityPasswordChange extends AppCompatActivity {

    Button pChange;
    FirebaseAuth auth;
    TextView pConfirm, pwrdOld, pwordNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pword_change);
        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();

        String user = auth.getCurrentUser().getUid();

        pChange = findViewById(R.id.finalChangePassword);
        pConfirm = findViewById(R.id.newPasswordConfirm);
        pwrdOld = findViewById(R.id.oldPassword);
        pwordNew = findViewById(R.id.newPassword);

        pChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
                String names = sharedPreferences.getString(NAME, "");
                final String passwords = sharedPreferences.getString(PASSWORD, "");

                AuthCredential credential = EmailAuthProvider
                        .getCredential(names, passwords);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (pwrdOld.getText().toString().equals(passwords) && pwordNew.getText().toString().equals(pConfirm.getText().toString())) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(pConfirm.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ActivityPasswordChange.this, "Password updated", Toast.LENGTH_LONG).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    startActivity(new Intent(ActivityPasswordChange.this, ActivityMain.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(ActivityPasswordChange.this, "Error, password not updated", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(ActivityPasswordChange.this, "Error, authaurization failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(ActivityPasswordChange.this, "Error, Passwords do not match", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ActivityPasswordChange.this, ActivityEditProfile.class);
        startActivity(intent);
    }
}
