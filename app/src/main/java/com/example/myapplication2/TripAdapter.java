package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class TripAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private ArrayList<Trip> tripList;
    private  ArrayList<Trip> filterListTrip;
    private ValueFilter valueFilter;



    public TripAdapter(Context context, int layout, ArrayList<Trip> tripList) {
        this.context = context;
        this.layout = layout;
        this.tripList = tripList;
        this.filterListTrip = tripList;
    }
    public void updateList(ArrayList<Trip> list) {
        tripList = list;
        filterListTrip = list;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tripList==null ? 0 :tripList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView txtName = (TextView) view.findViewById(R.id.listItemTripName);
        TextView txtDate = (TextView) view.findViewById(R.id.listItemTripDate);
        TextView txtDesc = (TextView) view.findViewById(R.id.listItemTripDesc);



        Trip trip = tripList.get(i);
        txtName.setText(trip.getName());
        txtDate.setText(trip.getDate());
        txtDesc.setText(trip.getDesc());


        return view;
    }
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Trip> filterList=new ArrayList<Trip>();
                for(int i=0;i<filterListTrip.size();i++){
                    if((filterListTrip.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Trip trip = new Trip((int) filterListTrip.get(i).getId(),
                                filterListTrip.get(i).getName(),
                                filterListTrip.get(i).getDestination(),
                                filterListTrip.get(i).getDate(),
                                filterListTrip.get(i).getDesc(),
                                filterListTrip.get(i).getAccommodation(),
                                filterListTrip.get(i).getVehicle(),
                                filterListTrip.get(i).getRra());

                        filterList.add(trip);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=filterListTrip.size();
                results.values=filterListTrip;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            tripList = (ArrayList<Trip>) filterResults.values;
            notifyDataSetChanged();
        }

    }
}