package com.example.marlowe.roarsports;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityFaceBookAndTwitterDetails extends AppCompatActivity {

    Button socialSubmit;
    EditText socialLastName, socialFirstName;
    CircleImageView imageView;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference mountainsRef, mountainImagesRef;
    FirebaseUser user;
    StorageReference storageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_and_twitter);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

            storage = FirebaseStorage.getInstance();
            storageRef = FirebaseStorage.getInstance().getReference();

            socialSubmit = findViewById(R.id.socialSubmit);
            socialFirstName = findViewById(R.id.socialFirstName);
            socialLastName = findViewById(R.id.socialLastName);
            imageView = findViewById(R.id.socialDp);

            socialSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("RoarSports").child("users").child(user_id);
                    current_user_db.setValue(true);

                    //adds details to firebase db
                    Map new_post = new HashMap();
                    new_post.put("Name", socialFirstName.getText().toString() + socialLastName.getText().toString());
                    new_post.put("email", mAuth.getCurrentUser().getEmail());

                    current_user_db.setValue(new_post);

                    if (imageView.getDrawable() == null){
                        Picasso.with(ActivityFaceBookAndTwitterDetails.this).load(R.drawable.com_facebook_profile_picture_blank_portrait).into(imageView);
                    } else {
                        dataUpload();
                    }

                    Intent intent = new Intent(ActivityFaceBookAndTwitterDetails.this, ActivityHome.class);
                    startActivity(intent);
                    finish();

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(ActivityFaceBookAndTwitterDetails.this);
                }
            });

        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(ActivityFaceBookAndTwitterDetails.this).load(resultUri).fit().into(imageView);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
        private void dataUpload () {

            user = FirebaseAuth.getInstance().getCurrentUser();


            storageRef = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics");

            mountainsRef = storageRef.child(user.getUid());

            mountainImagesRef = storageRef.child("Photos").child(String.valueOf(mountainsRef));

            mountainsRef.getName().equals(mountainImagesRef.getName());
            mountainsRef.getPath().equals(mountainImagesRef.getPath());

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            final Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            final byte[] data = baos.toByteArray();


            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Toast.makeText(ActivityFaceBookAndTwitterDetails.this, "Profile picture uploaded", Toast.LENGTH_LONG).show();


                }
            });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ActivityFaceBookAndTwitterDetails.this, "Account deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActivityFaceBookAndTwitterDetails.this, ActivityMain.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                finish();
            }
        });

    }
}


