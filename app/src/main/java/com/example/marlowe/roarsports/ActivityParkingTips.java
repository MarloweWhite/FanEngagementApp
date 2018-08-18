package com.example.marlowe.roarsports;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ActivityParkingTips extends ListActivity {


    String[] NAMES = {"Event Parking", "General Direction/Parking Tips", "ADA Parking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_tips);

        ListView listView = (ListView) findViewById(android.R.id.list);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);


    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return NAMES.length;
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
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customlayout2, null);


            if (i % 2 == 1){
                view.setBackgroundColor(getColor(R.color.black));
            } else {
                view.setBackgroundColor(getColor(R.color.darkBlack));

            }

            TextView textView = (TextView) view.findViewById(R.id.parkTips);
            textView.setText(NAMES[i]);

            return view;
        }
    }
}
