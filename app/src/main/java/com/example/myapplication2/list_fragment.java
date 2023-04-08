package com.example.myapplication2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;

import java.util.ArrayList;



public class list_fragment extends Fragment implements trip_search.FragmentListener {
    //định nghĩa
    ListView lvTrip;
    ArrayList<Trip> arrayList ;
    TripAdapter adapter ;
    TripDBHelper db;
    EditText searchName ;
    ImageButton advance,resetSearch;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(context);
    }


    public list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_fragment, container, false);
        lvTrip = (ListView) view.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayList.addAll(db.getListTrip());

//
        adapter = new TripAdapter(getContext(),R.layout.item_list,arrayList);
        lvTrip.setAdapter(adapter);

        resetSearch =(ImageButton) view.findViewById(R.id.imageButtonClear);
        advance = (ImageButton) view.findViewById(R.id.imageButton);
        advance.setOnClickListener(v -> advanceSearch());
        resetSearch.setOnClickListener(v -> resetSearch());



        lvTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Trip t = (Trip) arrayList.get(i);
                bundle.putSerializable(trip_detail.ARG_TRIP,t);
                Navigation.findNavController(view).navigate(R.id.action_list_to_trip_detail,bundle);
            }
        });
        searchName = (EditText) view.findViewById(R.id.search_name_EditText) ;

        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
    public void advanceSearch(){
        DialogFragment dialogFragment = new trip_search();
        dialogFragment.show(getChildFragmentManager(), null);
    }
//    protected void reloadList(Trip trip) {
////        arrayList = db.superSearch("","","");
////        adapter.updateList(arrayList);
//
//    }
    protected void resetSearch() {
        searchName.setText("");
        arrayList = db.getListTrip();
        adapter.updateList(arrayList);
    }

    @Override
    public void sendFromSearchFragment(Trip trip) {
        if (!trip.isEmpty()) {
//            reloadList(trip);
            arrayList = db.superSearch(trip.getName()==null?"":trip.getName(),
                    trip.getDestination()==null?"":trip.getDestination(),
                    trip.getDate()==null?"":trip.getDate());
            adapter.updateList(arrayList);
            return;
        }
    }


}