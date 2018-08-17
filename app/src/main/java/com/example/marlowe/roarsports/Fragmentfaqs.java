package com.example.marlowe.roarsports;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Fragmentfaqs extends ListFragment {

    String[] FAQS = {"Question 1", "Question 2","Question 3","Question 4","Question 5","Question 6","Question 7","Question 8","Question 9","Question 10", };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faqs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(android.R.id.list);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return FAQS.length;
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
            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            if (i % 2 == 1){
                view.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.darkBlack));

            }
            TextView textView = view.findViewById(R.id.parkingTipsOptions);
            textView.setText(FAQS[i]);
            return view;


        }
    }
}
