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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;


public class confirm_add extends Fragment {
    //key ARG_CONFIRM
    public static final String ARG_CONFIRM = "confirm";
    //gọi database
    TripDBHelper db;
    //định nghĩa các textview ,switch , button
    TextView name , destination ,date , desc , accommodation , vehicle ;
    Switch rra ;
    Button add ,cancel;
    //gọi Trip
    Trip trip;



    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }

    public confirm_add() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comfirm_add, container, false);

        //getArguments dùng để lấy dữ liệu chuyển từ bundle sang
        if(getArguments() !=null){
            //getArguments().getSerializable(key);
            trip = (Trip) getArguments().getSerializable(ARG_CONFIRM);
        }


        // Nếu trip có dữ liệu truyền tất cả dữ liệu vào trong text view ,switch vào trong form của confirm_add
        if(trip != null) {
            name = (TextView) view.findViewById(R.id.trip_confirm_name);
            destination = (TextView) view.findViewById(R.id.trip_confirm_destination);
            date = (TextView) view.findViewById(R.id.trip_confirm_date);
            desc = (TextView) view.findViewById(R.id.trip_confirm_description);
            accommodation = (TextView) view.findViewById(R.id.trip_confirm_accommodation);
            vehicle = (TextView) view.findViewById(R.id.trip_confirm_vehicle);
            rra = (Switch) view.findViewById(R.id.confirm_risk_assessment);
            name.setText(trip.getName());
            destination.setText(trip.getDestination());
            date.setText(trip.getDate());
            desc.setText(trip.getDesc());
            accommodation.setText(trip.getAccommodation());
            vehicle.setText(trip.getVehicle());
            if (trip.getRra() == 1) {
                rra.setClickable(false);
                rra.setChecked(true);
            } else {
                rra.setClickable(false);
            }
        }

        //lấy id của button
        add =(Button) view.findViewById(R.id.buttonConfirm_add);
        //gọi chức năng addtrip
        add.setOnClickListener(v -> addTrip());

        //lấy id của button
        cancel =(Button) view.findViewById(R.id.buttonConfirm_cancel);
        //gọi chức năng Cancel
        cancel.setOnClickListener(v -> Cancel());



        return view;
    }

    public void addTrip(){
        // lấy dữ liệu trên form
        String trip_name = name.getText().toString();
        String trip_destination = destination.getText().toString();
        String trip_date = date.getText().toString();
        String trip_desc = desc.getText().toString();
        String trip_accommodation = accommodation.getText().toString();
        String trip_vehicle = vehicle.getText().toString();
        int i = 0;
        if (rra.isChecked()){
            i = 1;
        }
        //db được định nghĩa ở trên
        //db.insert(các dữ liệu được lấy trong chức năng addTrip) = insert dữ liệu vào database
        db.insert(trip_name,trip_destination,trip_date,trip_desc,trip_accommodation,trip_vehicle,i);
        //thông báp
        Toast.makeText(getActivity(),"Add Success", Toast.LENGTH_SHORT).show();
        //di chuyển về trong add_fragment
        Navigation.findNavController(getView()).navigate(R.id.add_fragment);

    }
    //di chuyển về trong add_fragment
    public void Cancel(){
        Navigation.findNavController(getView()).navigate(R.id.add_fragment);
    }
}