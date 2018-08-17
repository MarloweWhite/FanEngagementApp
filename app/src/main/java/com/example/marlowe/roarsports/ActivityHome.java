package com.example.marlowe.roarsports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener, mAuthListener ;
    FirebaseUser user;
    String userID;
    Firebase mRef, mRefEmail, RefDp;
    GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
            setContentView(R.layout.activity_home);
            Firebase.setAndroidContext(this);

            String user = mAuth.getCurrentUser().getUid();

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            final TextView navEmail = (TextView) headerView.findViewById(R.id.loggedInEmail);
            final TextView navUsername = (TextView) headerView.findViewById(R.id.loggedInName);
            final ImageView imageView = (ImageView) headerView.findViewById(R.id.loggedInDp);


            mRef = new Firebase("https://roar-29883.firebaseio.com/RoarSports/users/" + user + "/" + "first name");
            mRefEmail = new Firebase("https://roar-29883.firebaseio.com/RoarSports/users/" + user + "/" + "email");
            RefDp = new Firebase("gs://roar-29883.appspot.com/Display Pics/" + user);
            String link = "gs://roar-29883.appspot.com/Display Pics/" + user;

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics/" + user);
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(imageView);


            mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    navUsername.setText(value);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            navEmail.setText(email);

            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


            mDrawerLayout = findViewById(R.id.drawer_layout);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentHome()).commit();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        } else if (id == R.id.myAccount){
            Intent intent = new Intent(ActivityHome.this, ActivityEditProfile.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);



        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.concessions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentConcessions()).commit();
                break;
            case R.id.merchandise:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMerchandise()).commit();
                break;
            case R.id.support:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentInfo()).commit();
                break;
            case R.id.parking:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentParking()).commit();
                break;
            case R.id.stadInfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentStadiumInformation()).commit();
                break;
            case R.id.socialM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSocialMedia()).commit();
                break;
            case R.id.partners:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSponsor()).commit();
                break;
            case R.id.ticketing:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentTicketing()).commit();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHome()).commit();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Lol he tried leave", "He tried leave bro ");
    }
}
