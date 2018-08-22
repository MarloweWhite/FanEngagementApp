package com.example.marlowe.roarsports;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


public class FragmentShare  extends Fragment {

    ImageButton facebookShare, twitterShare, instagramShare;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Context context = getActivity();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FacebookSdk.sdkInitialize(this.getActivity().getApplicationContext());
        facebookShare = (ImageButton) view.findViewById(R.id.facebookShare);
        twitterShare = (ImageButton) view.findViewById(R.id.twitterShare);
        instagramShare = (ImageButton) view.findViewById(R.id.instaShare);

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

                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.roar_logo_red)
                        + '/' + getResources().getResourceTypeName(R.drawable.roar_logo_red) + '/' + getResources().getResourceEntryName(R.drawable.roar_logo_red) );

                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text("Join me and support(enter team) using Roar")
                        .image(imageUri);
                builder.show();

            }
        });


        instagramShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "image/*";

                Intent share = new Intent(Intent.ACTION_SEND);

                // Set the MIME type
                share.setType(type);

                // Create the URI from the media
                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.roar_logo_red)
                        + '/' + getResources().getResourceTypeName(R.drawable.roar_logo_red) + '/' + getResources().getResourceEntryName(R.drawable.roar_logo_red) );


                // Add the URI to the Intent.
                share.putExtra(Intent.EXTRA_STREAM, imageUri);

                //Copy clipboard text
                String label = "Paste this text";
                String text = "Join me and support(enter team) using Roar";
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(label, text);
                clipboard.setPrimaryClip(clip);

                // Broadcast the Intent.
                startActivity(Intent.createChooser(share, "Share to"));

            }
        });

    }

}
