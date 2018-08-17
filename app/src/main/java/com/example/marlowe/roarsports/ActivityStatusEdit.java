package com.example.marlowe.roarsports;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityStatusEdit extends AppCompatActivity {

    private TextView email, status, uid;
    private Button send;
    private Button refresh;
    private Button contin;
    private Button remove;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setContentView(R.layout.activity_status_edit);
        show_alert();


            email = findViewById(R.id.txt_email2);
            status = findViewById(R.id.txt_status2);
            uid = findViewById(R.id.txt_uid2);
            send = findViewById(R.id.btn_send2);
            refresh = findViewById(R.id.btn_refresh2);
            contin = findViewById(R.id.btn_cont2);
            //remove = findViewById(R.id.removal);


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send.setEnabled(false);

                    FirebaseAuth.getInstance().getCurrentUser()
                            .sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    send.setEnabled(true);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ActivityStatusEdit.this, "Email sent", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ActivityStatusEdit.this, "Email failed to send", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
            });

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().getCurrentUser()
                            .reload()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    setInfo();
                                }
                            });
                }
            });

            contin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(ActivityStatusEdit.this, ActivityHome.class);
                        startActivity(intent);
                        String user_id = mAuth.getCurrentUser().getUid();
                        String user_email = mAuth.getCurrentUser().getEmail();
                        Firebase firebase = new Firebase("https://roar-29883.firebaseio.com/");
                        Firebase firebaseRef = firebase.child("RoarSports").child("users");
                        firebaseRef.child(user_id).child("email").setValue(user_email);
                    } else {
                        Toast.makeText(ActivityStatusEdit.this, "Please accept the email first", Toast.LENGTH_LONG).show();
                    }
                }
            });


    }

    private void setInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        email.setText(new StringBuilder("EMAIL : ").append(user.getEmail()));
        uid.setText(new StringBuilder("UID : ").append(user.getUid()));
        status.setText(new StringBuilder("STATUS : ").append(String.valueOf(user.isEmailVerified())));

    }

    AlertDialog mMyDialog; // declare AlertDialog
    public void show_alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please request an email then once accepted press the refresh button ");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //  Intent intent = new Intent(ActivityStatus.this, null);
                        //    startActivity(intent);
                        mMyDialog.dismiss(); // dismiss AlertDialog
                    }
                });

        mMyDialog = builder.show(); // assign AlertDialog
        return;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ActivityStatusEdit.this, "Not happening ", Toast.LENGTH_LONG).show();
    }
}
