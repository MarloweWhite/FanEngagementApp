package com.example.marlowe.roarsports;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class ActivityRegistration extends AppCompatActivity {
    private EditText mFirstName, mLastName, mEmail, mPassword, mPasswordConfirm;
    private Button mRegister, remove;
    private ImageView imageView;
    private StorageReference storageReference;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog progressDialog;
    Uri uri;
    private SharedPreferences sharedPreferences, sharedPreferences2;
    public static final String MY_SHARED3 = "MySharedPreference3";
    public static final String PHOTO = "photo";
    private SharedPreferences.Editor editor;
    public static final String MY_SHARED2 = "MySharedPreference2";
    public static final String NAME2 = "name2";
    public static final String PASSWORD2 = "password2";
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    final int PIC_CROP = 1;
    FirebaseStorage storage;
    private Firebase mRef;
    StorageReference mountainsRef, mountainImagesRef;
    String photo;
    FirebaseUser user;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://roar-29883.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();



        storage = FirebaseStorage.getInstance();;
        storageReference = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.profilePhoto);
        progressDialog = new ProgressDialog(this);


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Intent intent = new Intent(ActivityRegistration.this, ActivityStatus.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ActivityRegistration.this);
            }
        });

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.emailReg);
        mPassword = findViewById(R.id.passwordReg);
        mPasswordConfirm = findViewById(R.id.passwordRegConfirm);
        mRegister = findViewById(R.id.register);
        remove = findViewById(R.id.removal);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String email = mEmail.getText().toString();
                final String password = mPasswordConfirm.getText().toString();
                if (!mPassword.getText().toString().equals(mPasswordConfirm.getText().toString())){
                    Toast.makeText(ActivityRegistration.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mFirstName.getText().toString()) || TextUtils.isEmpty(mLastName.getText().toString())
                        || TextUtils.isEmpty(mEmail.getText().toString()) || TextUtils.isEmpty(mPassword.getText().toString())
                        || TextUtils.isEmpty(mPasswordConfirm.getText().toString())) {
                    Toast.makeText(ActivityRegistration.this, "Please fill in empty fields", Toast.LENGTH_LONG).show();
                } else if (mPassword.getText().toString().length() < 5 && mPasswordConfirm.getText().toString().length() < 5) {
                    Toast.makeText(ActivityRegistration.this, "Make password longer than 5 letters", Toast.LENGTH_LONG).show();
                }else {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ActivityRegistration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityRegistration.this, "Error with account creation", Toast.LENGTH_LONG).show();
                        } else {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("RoarSports").child("users").child(user_id);
                            current_user_db.setValue(true);


                            Map new_post = new HashMap();
                            new_post.put("first name", mFirstName.getText().toString());
                            new_post.put("last name", mLastName.getText().toString());
                            new_post.put("email", mEmail.getText().toString());
                            new_post.put("dp", "gs://roar-29883.appspot.com/Display Pics" + "/" + user_id );

                            current_user_db.setValue(new_post);

                            String email2 = mEmail.getText().toString();
                            String password2 = mPassword.getText().toString();

                            sharedPreferences = getSharedPreferences(MY_SHARED2, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();

                            editor.putString(NAME2, email2);
                            editor.putString(PASSWORD2, password2);


                            editor.apply();


                            if (imageView.getDrawable() == null){
                                Picasso.with(ActivityRegistration.this).load(R.drawable.com_facebook_profile_picture_blank_portrait).into(imageView);
                            } else {
                                dataUpload();
                            }

                        }
                    }

                    });
                }
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void dataUpload() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        // Create a storage reference from our app
         storageRef = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics" );

// Create a reference to "mountains.jpg"
         mountainsRef = storageRef.child(user.getUid());


// Create a reference to 'images/mountains.jpg'
         mountainImagesRef = storageRef.child("Photos").child(String.valueOf(mountainsRef));

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        final Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();


        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(ActivityRegistration.this, "Display picture uploaded", Toast.LENGTH_LONG).show();
                Firebase mRefDisplay = mRef.child("Display pic");
                mRefDisplay.setValue(bitmap);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}

