package com.example.marlowe.roarsports;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseUser;


public class ActivityAmenities extends ListActivity  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FirebaseUser user;


    String[] AMENITIES = {"Toilet Info", "Concessions Info", "Support desks", "Healthcare", "Gates(entry, exit)", "Shops", "Bars", "Hospitality", "Disabled access", "Sponsor activation areas", "Fan zones"};
    int[] IMAGES = {R.mipmap.baseline_wc_white_48, R.mipmap.baseline_local_cafe_white_48, R.mipmap.baseline_record_voice_over_white_48, R.mipmap.baseline_local_hospital_white_48, R.mipmap.baseline_meeting_room_white_48,
            R.mipmap.baseline_shopping_cart_white_48, R.mipmap.baseline_local_bar_white_48, R.mipmap.baseline_restaurant_white_48,
            R.mipmap.baseline_accessible_white_48, R.drawable.ic_radiobox_marked_white_24dp, R.drawable.ic_flag_checkered_white_36dp};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);
        Firebase.setAndroidContext(this);


        //Setting the listview
        ListView listView = findViewById(android.R.id.list);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlistview, null);
            if (i % 2 == 1){
                view.setBackgroundColor(getColor(R.color.black));
            } else {
                view.setBackgroundColor(getColor(R.color.darkBlack));

            }

            ImageView imageView = view.findViewById(R.id.listViewImage);
            TextView textView = view.findViewById(R.id.listViewText);

            imageView.setImageResource(IMAGES[i]);
            textView.setText(AMENITIES[i]);
            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

}
