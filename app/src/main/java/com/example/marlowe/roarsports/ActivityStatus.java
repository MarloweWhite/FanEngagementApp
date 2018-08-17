package com.example.marlowe.roarsports;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.marlowe.roarsports.ActivityRegistration.NAME2;
import static com.example.marlowe.roarsports.ActivityRegistration.PASSWORD2;


public class ActivityStatus extends AppCompatActivity {

    private TextView email, status, uid;
    private Button send;
    private Button refresh;
    private Button contin;
    private Button remove;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mAuth = FirebaseAuth.getInstance();
        show_alert();

        email = findViewById(R.id.txt_email);
        status = findViewById(R.id.txt_status);
        uid = findViewById(R.id.txt_uid);
        send = findViewById(R.id.btn_send);
        refresh = findViewById(R.id.btn_refresh);
        contin = findViewById(R.id.btn_cont);
        remove = findViewById(R.id.removal);


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
                                    Toast.makeText(ActivityStatus.this, "Email sent", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ActivityStatus.this, "Email failed to send", Toast.LENGTH_LONG).show();

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
                if (user.isEmailVerified()){
                    startSignIn();
                } else {
                    Toast.makeText(ActivityStatus.this, "Please confirm your email first", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void startSignIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreference2", Context.MODE_PRIVATE);

        String names = sharedPreferences.getString(NAME2, "");
        String passwords = sharedPreferences.getString(PASSWORD2, "");


        mAuth.signInWithEmailAndPassword(names, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(ActivityStatus.this, "Failure to sign in", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ActivityStatus.this, "Signed in", Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(ActivityStatus.this, ActivityHome.class);
                    startActivity(intent);

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
        Toast.makeText(ActivityStatus.this, "Please ", Toast.LENGTH_LONG).show();
    }
}
