package com.example.marlowe.roarsports;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityGettingHere extends AppCompatActivity {

    Button directionsFromLocation;
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_here);
        textView = findViewById(R.id.textView15);

        //textView.setText("\u2022" + R.string.Latin);

        directionsFromLocation = findViewById(R.id.directionsFromLocation);

        directionsFromLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:51.488762, -3.174134?q=" + " " + "CF10 4HJ");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }
}
