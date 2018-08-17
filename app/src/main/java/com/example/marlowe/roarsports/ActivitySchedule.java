package com.example.marlowe.roarsports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ActivitySchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("https://twitter.com/Marloni_um");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        } else if (id == R.id.myAccount){
            Intent intent = new Intent(ActivitySchedule.this, ActivityEditProfile.class);
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
}
