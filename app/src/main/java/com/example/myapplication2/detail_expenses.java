package com.example.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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


public class detail_expenses extends DialogFragment {
    public static final String ARG_ID_EXPENSES = "id";
    ArrayList<Expensess> arrayList ;
    Expensess expensess;
    TripDBHelper db;
    expensesAdapter adapter;




    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }

    public detail_expenses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_expenses, container, false);
        TextView type , date ,time , amount , comment  ;
        Button delete, cancel;
        if(getArguments() !=null){
            Bundle bundle = getArguments();
            String id = bundle.getString(ARG_ID_EXPENSES);
            int i = Integer.parseInt(id);
            expensess = db.getExpenses(i);
        }
        if(expensess != null){
            type = (TextView) view.findViewById(R.id.expenses_detail_type);
            date=(TextView) view.findViewById(R.id.expenses_detail_date);
            time =(TextView) view.findViewById(R.id.expenses_detail_time);
            amount =(TextView) view.findViewById(R.id.expenses_detail_amount);
            comment =(TextView) view.findViewById(R.id.expensesDetailComment);
            type.setText(expensess.getType());
            date.setText(expensess.getDate());
            time.setText(expensess.getTime());
            amount.setText(expensess.getAmount());
            comment.setText(expensess.getComment());
        }
        delete = (Button) view.findViewById(R.id.btn_delete_expenses);
        delete.setOnClickListener(v -> deleteExpenses());
        cancel = (Button) view.findViewById(R.id.btn_update_expenses);
        cancel.setOnClickListener(v -> Dismiss());

        return view;
    }

    private void Dismiss() {
        dismiss();
    }


    public  void deleteExpenses(){
        long id =  expensess.getId();
        db.deleteExpenses(id);
        Toast.makeText(getActivity(),"Delete Success " ,Toast.LENGTH_SHORT);
        dismiss();

    }


    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = 1100;
            int height =1500;
            dialog.getWindow().setLayout(width, height);
        }
    }
}