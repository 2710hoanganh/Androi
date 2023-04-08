package com.example.myapplication2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.net.URL;
import java.util.ArrayList;

public class home_fragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TripDBHelper dbHelper ;
    Button backup , reset;
    ImageView imageView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new TripDBHelper(getContext());
    }
    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);




        backup =(Button) view.findViewById(R.id.btn_backup);
        reset =(Button) view.findViewById(R.id.btn_reset);
        backup.setOnClickListener(v->Backup());
        reset.setOnClickListener(v->Reset());

        return view;
    }
    public void Backup(){
        ArrayList<Trip> trip = dbHelper.getListTrip();
        ArrayList<Expensess> expenses = dbHelper.listExpenses();



        if (null != trip && 0 < trip.size() && null != expenses && 0 < expenses.size()) {
            ExpensesBackup expensesBackup = new ExpensesBackup(expenses);
            TripBackup backup = new TripBackup(trip);

                    db.collection("trip")
                    .add(backup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(getActivity(), "ngon", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "ngu", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
                    db.collection("expenses")
                    .add(expensesBackup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(getActivity(), "ngon", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "ngu", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        } else {
            Toast.makeText(getActivity(), "khong co list", Toast.LENGTH_SHORT).show();
        }

    }
    public void  Reset(){

        dbHelper.reset();
        Toast.makeText(getActivity(),"reset db successful",Toast.LENGTH_SHORT);
    }
}
class TripBackup{
    private ArrayList<Trip> trips;


    public TripBackup(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }
}
class ExpensesBackup{
    private ArrayList<Expensess> expensesses;

    public ExpensesBackup(ArrayList<Expensess> expensesses) {
        this.expensesses = expensesses;
    }

    public ArrayList<Expensess> getExpensesses() {
        return expensesses;
    }

    public void setExpensesses(ArrayList<Expensess> expensesses) {
        this.expensesses = expensesses;
    }
}