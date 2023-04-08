package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class trip_search extends DialogFragment {
    EditText name, destination ,date ;
    Button search , cancel ;

    DatePickerDialog datePicker  ;

    //:D
    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    public trip_search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trip_search, container, false);

        name = (EditText) view.findViewById(R.id.search_name);
        destination = (EditText) view.findViewById(R.id.search_destination);
        date =(EditText) view.findViewById(R.id.search_date);
        date.setOnClickListener(v-> pickDate());

        search = (Button) view.findViewById(R.id.btn_search);
        cancel = (Button) view.findViewById(R.id.btn_cancel);

        search.setOnClickListener(v->Search());
        cancel.setOnClickListener(v -> Cancel());

        return view;
    }

    public void Search() {
        Trip trip = new Trip();


        String txt_name = name.getText().toString();
        String txt_destination = destination.getText().toString();
        String txt_date = date.getText().toString();


        if (txt_name != null && !txt_name.trim().isEmpty())
            trip.setName(txt_name);
        if (txt_destination != null && !txt_destination.trim().isEmpty())
            trip.setDestination(txt_destination);
        if (txt_date != null && !txt_date.trim().isEmpty())
            trip.setDate(txt_date);


        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromSearchFragment(trip);

        dismiss();
    }


    public void Cancel(){
        dismiss();
    }

    public void pickDate(){
        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i ,i1 ,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, year,month,day);
        datePicker.show();
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = 1500;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public interface FragmentListener {
        void sendFromSearchFragment(Trip trip);
    }
}