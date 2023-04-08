package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class expensesAdapter extends BaseAdapter {
    Context context ;
    int layout ;
    ArrayList<Expensess> expenses;

    public expensesAdapter(Context context, int layout, ArrayList<Expensess> expenses) {
        this.context = context;
        this.layout = layout;
        this.expenses = expenses;
    }
    public void updateResults(ArrayList<Expensess> expenses) {
        this.expenses = expenses;
        //Triggers the list update
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
       return expenses.size();
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

        TextView txtType = (TextView) view.findViewById(R.id.listItemExpensesType);
        TextView txtDate = (TextView) view.findViewById(R.id.listItemExpensesDate);
        TextView txtCMT = (TextView) view.findViewById(R.id.listItemExpensesMoney);


        Expensess e = expenses.get(i);
        txtType.setText(e.getType());
        txtDate.setText(e.getDate());
        txtCMT.setText(e.getAmount()+"$");


        return view;
    }

}
