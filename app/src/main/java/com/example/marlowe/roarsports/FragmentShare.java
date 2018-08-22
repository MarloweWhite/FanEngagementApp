package com.example.marlowe.roarsports;

import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.URL;

public class FragmentShare  extends Fragment {

    Button facebookShare, twitterShare, instagramShare;
    CallbackManager callbackManager;
    ShareDialog shareDialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FacebookSdk.sdkInitialize(this.getActivity().getApplicationContext());
        facebookShare = (Button) view.findViewById(R.id.facebookShare);
        twitterShare = (Button) view.findViewById(R.id.twitterShare);
        instagramShare = (Button) view.findViewById(R.id.instaShare);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());

        facebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setQuote("Download ROAR and support (enter team)")
                        .setContentUrl(Uri.parse("http://roarsports.net/"))
                        .build();
                ShareDialog.show(getActivity(),shareLinkContent);
            }
        });

        twitterShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        instagramShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
