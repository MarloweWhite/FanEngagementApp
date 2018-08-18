package com.example.marlowe.roarsports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityFeedback extends AppCompatActivity {

    EditText feedback;
    Button sendFeedback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback = findViewById(R.id.feedback);
        sendFeedback = findViewById(R.id.sendFeedback);

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback.getText().toString().length() > 100) {
                    Toast.makeText(ActivityFeedback.this, "Please make feedback less than 100 words", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(feedback.getText().toString())){
                    Toast.makeText(ActivityFeedback.this, "Please fill in feedback", Toast.LENGTH_LONG).show();
                } else {
                    sendMail();
                }
            }
        });

    }

    private void sendMail() {
        String[] recipient = {"WhiteM6@cardiff.ac.uk"};
        String subject = "Feedback";
        String message = feedback.getText().toString();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}