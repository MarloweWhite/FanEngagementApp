package com.example.marlowe.roarsports;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSponsor extends ListFragment {


    ListView listView;
    String[] values = {"Nestle", "Nike", "Intel"};
    int[] IMAGES = {R.drawable.ic_nestle, R.drawable.ic_nike, R.drawable.ic_intel};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sponsor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(android.R.id.list);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 ){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.nestle.co.uk/"));
                    startActivity(intent);
                } else if (position == 1){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.nike.com/gb/en_gb/"));
                    startActivity(intent);
                } else if (position == 2){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.intel.co.uk/content/www/uk/en/homepage.html"));
                    startActivity(intent);
                }
            }
        });



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
        public View getView(int i, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.customlistview, null);


            if (i % 2 == 1){
                view.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.darkBlack));

            }

            TextView textView = (TextView) view.findViewById(R.id.listViewText);
            ImageView imageView = (ImageView) view.findViewById(R.id.listViewImage);
            textView.setText(values[i]);
            imageView.setImageResource(IMAGES[i]);

            return view;
        }
    }
}
