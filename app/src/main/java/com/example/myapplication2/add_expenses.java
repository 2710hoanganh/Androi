package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class add_expenses extends DialogFragment {
    public static final String ARG_ADD_EXPENSES = "expenses";


    Button add, cancel ;
    Spinner spinner ;
    EditText date ,time ,amount,comment ;
    TimePickerDialog timePicker;
    TripDBHelper db ;
    DatePickerDialog datePicker  ;

    //:D
    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    int hrs = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }

    public add_expenses() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expenses, container, false);
        spinner = (Spinner) view.findViewById(R.id.expensesCreateType);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Food");
        arrayList.add("Work");
        arrayList.add("Travel");
        arrayList.add("Other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        date = (EditText) view.findViewById(R.id.expensesCreateDate) ;
        time = (EditText) view.findViewById(R.id.expensesCreateTime);
        comment =(EditText) view.findViewById(R.id.expensesCreateComment) ;
        amount =(EditText) view.findViewById(R.id.expensesCreateAmount) ;
        date.setOnClickListener(v-> pickDate());
        time.setOnClickListener(v ->pickTime());




        add = (Button) view.findViewById(R.id.expensesCreateButtonAdd);
        cancel = (Button) view.findViewById(R.id.expensesCreateButtonCancel) ;
        add.setOnClickListener(v -> addExpenses());
        cancel.setOnClickListener(v -> cancelExpenses());

        return view;
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
    public void pickTime(){
        timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendar.set(0,0,0,i ,i1 );
                time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },hrs,min,true);
        timePicker.show();

    }





    public void addExpenses(){
        if(getArguments() !=null){
            Bundle bundle = getArguments();
            String id = bundle.getString(ARG_ADD_EXPENSES);
            int i = Integer.parseInt(id);
            Trip trip =  db.getTrip(i);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            String type = spinner.getSelectedItem().toString();
            String date_add = date.getText().toString();
            String time_add = time.getText().toString();
            String amount_add = amount.getText().toString();
            String comment_add = comment.getText().toString();

            if(date_add.equals("") || time_add.equals("")||amount_add.equals("")){
                Toast.makeText(getContext(),"Please Enter All The Fields",Toast.LENGTH_SHORT).show();
            }else{

                db.insertExpenses(type,date_add,time_add,amount_add,comment_add, i);//convert id back to int :p
                dismiss();



                Toast.makeText(getActivity(),"Add Success" ,Toast.LENGTH_SHORT ).show();
            }

        }else {
            Toast.makeText(getActivity(),"Add Expenses Unsuccessful ",Toast.LENGTH_SHORT ).show();
            dismiss();
        }


    }
    public void cancelExpenses(){
        dismiss();
    }




    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}