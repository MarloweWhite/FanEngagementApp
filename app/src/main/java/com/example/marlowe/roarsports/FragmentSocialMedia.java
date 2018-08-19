package com.example.marlowe.roarsports;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

public class FragmentSocialMedia extends Fragment {

    ImageButton clubFacebook, clubYoutube, clubTwitter, clubInstagram, roarFacebook, roarYoutube, roarTwitter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clubFacebook = view.findViewById(R.id.clubFacebook);
        clubInstagram = view.findViewById(R.id.clubInsta);
        clubTwitter = view.findViewById(R.id.clubTwitter);
        clubYoutube = view.findViewById(R.id.clubYoutube);
        roarFacebook = view.findViewById(R.id.roarFacebook);
        roarTwitter = view.findViewById(R.id.roarTwitter);
        roarYoutube = view.findViewById(R.id.roarYoutube);

        clubFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/roarsportsfans/"));
                startActivity(intent);
            }
        });

        clubInstagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/roar.sports/"));
                startActivity(intent);
            }
        });

        clubTwitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/roarsportsfans"));
                startActivity(intent);
            }
        });

        clubYoutube.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://roarsports.net/"));
                startActivity(intent);
            }
        });

        roarFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/roarsportsfans/"));
                startActivity(intent);
            }
        });

        roarTwitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/roarsportsfans"));
                startActivity(intent);
            }
        });

        roarYoutube.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/roar.sports/"));
                startActivity(intent);
            }
        });
    }
}
