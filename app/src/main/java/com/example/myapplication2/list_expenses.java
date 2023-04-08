package com.example.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;

import java.util.ArrayList;


public class list_expenses extends DialogFragment {

    public static final String ARG_ID_TRIP = "id";
    ListView lvExpenses;
    TripDBHelper db ;
    expensesAdapter adapter;
    ArrayList<Expensess> arrayList;
    ImageButton imageButton;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(context);
    }


    public list_expenses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_expenses, container, false);

        if(getArguments() !=null){
            Bundle bundle = getArguments();
            String id = bundle.getString(ARG_ID_TRIP);
            lvExpenses = (ListView) view.findViewById(R.id.lv_expenses);
            arrayList = new ArrayList<>();
            arrayList.addAll(db.getListExpenses(Integer.parseInt(id)));
            adapter = new expensesAdapter(getContext(),R.layout.item_list_expenses,arrayList);
            lvExpenses.setAdapter(adapter);
        }
        if(arrayList.size()==0){
            Toast.makeText(getActivity(),"List Expenses Empty",Toast.LENGTH_SHORT).show();
        }
        lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Expensess e = (Expensess) arrayList.get(i);
                bundle.putString(detail_expenses.ARG_ID_EXPENSES, String.valueOf(e.getId()));
                DialogFragment dialogFragment = new detail_expenses();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getChildFragmentManager(), null);
            }
        });

        imageButton=(ImageButton) view.findViewById(R.id.imageButton_dismiss);
        imageButton.setOnClickListener(v -> Dismiss());
        return  view ;
    }

    public void Dismiss(){
        dismiss();
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = 1000;
            int height =1500;
            dialog.getWindow().setLayout(width, height);
        }
    }
}