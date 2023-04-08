package com.example.myapplication2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;

import java.util.ArrayList;


public class trip_detail extends Fragment {
    public static final String ARG_TRIP = "trip";
    ArrayList<Trip> arrayList ;
    Trip trip;
    TripDBHelper db;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }

    public trip_detail() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_detail, container, false);
        TextView name , destination ,date , desc , accommodation , vehicle ;
        Switch rra ;
        Button delete,update,add_expenses,list_expenses;
        if(getArguments() !=null){
            trip = (Trip) getArguments().getSerializable(ARG_TRIP);
            trip = db.getTrip((int) trip.getId());
        }



        if(trip != null){
            name = (TextView) view.findViewById(R.id.trip_detail_name);
            destination=(TextView) view.findViewById(R.id.trip_detail_destination);
            date =(TextView) view.findViewById(R.id.trip_detail_date);
            desc =(TextView) view.findViewById(R.id.trip_detail_description);
            accommodation =(TextView) view.findViewById(R.id.trip_detail_accommodation);
            vehicle = (TextView) view.findViewById(R.id.trip_detail_vehicle);
            rra =(Switch) view.findViewById(R.id.detail_risk_assessment);
            name.setText(trip.getName());
            destination.setText(trip.getDestination());
            date.setText(trip.getDate());
            desc.setText(trip.getDesc());
            accommodation.setText(trip.getAccommodation());
            vehicle.setText(trip.getVehicle());
            if(trip.getRra() ==1){
                rra.setClickable(false);
                rra.setChecked(true);
            }else {
                rra.setClickable(false);
            }

        }


        delete = (Button) view.findViewById(R.id.btn_delete_trip);
        delete.setOnClickListener(v -> deleteTrip());
        update = (Button) view.findViewById(R.id.btn_update_trip);
        update.setOnClickListener(v -> updateTrip());
        add_expenses =(Button) view.findViewById(R.id.btn_add_expenses) ;
        list_expenses=(Button) view.findViewById(R.id.btn_list_expenses);
        add_expenses.setOnClickListener(v -> addExpenses());
        list_expenses.setOnClickListener(v-> lisExpenses());
        return  view;
    }
    public void deleteTrip(){
           long id = trip.getId();
           db.delete(id);
        Navigation.findNavController(getView()).navigate(R.id.list_fragment);
    }
    public void updateTrip(){
        if(trip !=null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(update_trip.ARG_UPDATE_TRIP,trip);
            Navigation.findNavController(getView()).navigate(R.id.update_trip,bundle);
        }
    }
    public  void addExpenses(){
        long id = trip.getId();//
        Bundle bundle = new Bundle();
        bundle.putString(add_expenses.ARG_ADD_EXPENSES, String.valueOf(id));//convert id from int to string :D
        DialogFragment dialogFragment = new add_expenses();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), null);

    }
    public void lisExpenses(){
        long id = trip.getId();//
        Bundle bundle = new Bundle();
        bundle.putString(list_expenses.ARG_ID_TRIP, String.valueOf(id));//convert id from int to string :D


        DialogFragment dialogFragment = new list_expenses();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), null);
    }



}