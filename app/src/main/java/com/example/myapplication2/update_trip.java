package com.example.myapplication2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;


public class update_trip extends Fragment {
    public static final String ARG_UPDATE_TRIP = "update";
    TripDBHelper db ;
    EditText name , destination , date, accommodation,vehicle,description;
    Switch rra ;
    Trip trip;


    public update_trip() {
        // Required empty public constructor
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_trip, container, false);
        if(getArguments() !=null){
            Trip trip = (Trip) getArguments().getSerializable(ARG_UPDATE_TRIP);
            long id = trip.getId();
            name=(EditText) view.findViewById(R.id.trip_update_name);
            destination=(EditText) view.findViewById(R.id.trip_update_destination);
            date=(EditText) view.findViewById(R.id.trip_update_date);
            description =(EditText) view.findViewById(R.id.trip_update_description);
            accommodation=(EditText) view.findViewById(R.id.trip_update_accommodation);
            vehicle = (EditText) view.findViewById(R.id.trip_update_vehicle);
            rra =(Switch) view.findViewById(R.id.update_risk_assessment);
            name.setText(trip.getName());
            description.setText(trip.getDesc());
            destination.setText(trip.getDestination());
            date.setText(trip.getDate());
            accommodation.setText(trip.getAccommodation());
            vehicle.setText(trip.getVehicle());
            if(trip.getRra() ==1){
                rra.setChecked(true);
            }

        }

        Button update = (Button) view.findViewById(R.id.btn_update_trip2);
        update.setOnClickListener(v ->update());



        return  view;
    }
    public void update(){
        if(getArguments()!=null){
            Trip trip = (Trip) getArguments().getSerializable(ARG_UPDATE_TRIP);
            int i  = 0;
            long id  = trip.getId();
            String trip_name = name.getText().toString();
            String trip_destination = destination.getText().toString();
            String trip_date = date.getText().toString();
            String trip_desc = description.getText().toString();
            String trip_accommodation = accommodation.getText().toString();
            String trip_vehicle = vehicle.getText().toString();
            if(rra.isChecked()){
                i = 1;
            }
            db.updateTrip(id,trip_name,trip_destination,trip_date,trip_desc,trip_accommodation,trip_vehicle,i);
            Navigation.findNavController(getView()).navigate(R.id.list_fragment);

        }

    }
}