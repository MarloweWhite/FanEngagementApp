package com.example.marlowe.roarsports;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentHome extends Fragment {

    Button pTickets, mTickets, seatDiagram, ticketOffice, activeSchedule, gameSchedule;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pTickets = view.findViewById(R.id.purchaseTickets);
        mTickets = view.findViewById(R.id.myTickets);
        seatDiagram = view.findViewById(R.id.seatingDiagram);
        ticketOffice = view.findViewById(R.id.ticketOffice);
        activeSchedule = view.findViewById(R.id.activitySchedule);
        gameSchedule = view.findViewById(R.id.gameSchedule);

        activeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Activity schedule", Toast.LENGTH_LONG).show();
            }
        });

        ticketOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:123456789"));
                startActivity(callIntent);
            }
        });

        pTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://roarsports.net/"));
                startActivity(intent);
            }
        });

        //This can easily be made into an activity with an image
        seatDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivitySeatingPlan.class);
                //intent.setAction(Intent.ACTION_VIEW);
                //intent.addCategory(Intent.CATEGORY_BROWSABLE);
                //intent.setData(Uri.parse("https://cardiffdevils.cloudvenue.co.uk/"));
                startActivity(intent);
            }
        });

        activeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivitySchedule.class);
                startActivity(intent);
            }
        });


    }
}
