package com.example.marlowe.roarsports;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class ActivityEditProfile extends AppCompatActivity {
    // to fix comitt

    private Button signOutButton, changePasswordButton, changeEmaildButton, changeDisplayButton, delete;
    private ImageView imageView;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseStorage storage;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    FirebaseUser user;
    String userID;
    StorageReference mountainsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();


        signOutButton = findViewById(R.id.signOut);
        imageView = findViewById(R.id.changeDp);
        changePasswordButton = findViewById(R.id.cpButton);
        changeEmaildButton = findViewById(R.id.ceButton);
        changeDisplayButton = findViewById(R.id.cdButton);
        delete = findViewById(R.id.delete);


        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics/" + userID);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);



        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast toast = Toast.makeText(ActivityEditProfile.this, "LOGGED", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    return;
                }
            }
        };

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditProfile.this, ActivityPasswordChange.class);
                startActivity(intent);
                finish();
            }
        });

        changeEmaildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditProfile.this, ActivityChangeEmail.class);
                startActivity(intent);
                finish();
            }
        });

        changeDisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditProfile.this, ActivityChangeDisplay.class);
                startActivity(intent);
                finish();
            }
        });



        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(ActivityEditProfile.this, ActivityMain.class));
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    LoginManager.getInstance().logOut();
                                                    Toast.makeText(ActivityEditProfile.this, "Account deleted", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(ActivityEditProfile.this, ActivityMain.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(ActivityEditProfile.this, "Account not deleted, Please sign out and then log back in to complete action", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                break;



                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEditProfile.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CropImage.activity()
                     //   .setGuidelines(CropImageView.Guidelines.ON)
                     //   .start(ActivityEditProfile.this);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(ActivityEditProfile.this).load(resultUri).fit().into(imageView);
                //dataUpload();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

  /*  private void dataUpload() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics");

// Create a reference to "mountains.jpg"
        mountainsRef = storageRef.child(user.getUid());

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("Photos").child(String.valueOf(mountainsRef));

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ActivityEditProfile.this, "Profile picture failed to update", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(ActivityEditProfile.this, "Profile picture updated", Toast.LENGTH_LONG).show();
            }
        });
    }*/




    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityEditProfile.this, ActivityHome.class);
        startActivity(intent);
        finish();
    }



}
