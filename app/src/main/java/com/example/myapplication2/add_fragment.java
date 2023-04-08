package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication2.DbHelper.TripDBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class add_fragment extends Fragment {
    // định nghĩa trước các dittext , switch , button và datepicker để lấy id
    EditText name , destination , date  , description , accommodation , vehicle ;
    Switch rra ;
    Button add_trip;
    TripDBHelper db ;
    DatePickerDialog datePicker  ;

    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    public add_fragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new TripDBHelper(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_fragment, container, false);


        // lấy tất cả id của edittext , switch , button và datepicker
        name = view.findViewById(R.id.trip_name_editText);
        destination = view.findViewById(R.id.trip_destination_editText);
        date = view.findViewById(R.id.editTextDate);
        description = view .findViewById(R.id.trip_description_editText);
        accommodation = view.findViewById(R.id.trip_accommodation_editText);
        vehicle = view.findViewById(R.id.trip_vehicle_editText);
        rra = view.findViewById(R.id.add_risk_assessment);

        add_trip = view.findViewById(R.id.buttonAddTrip);

        date.setOnClickListener(v-> pickDate());

        // khi nút addtrip được bấm thì sẽ gọi chức năng BtnAdd bên dưới
        add_trip.setOnClickListener(view1 -> BtnAdd());


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
    protected  void  BtnAdd(){
        //gọi class Trip
        Trip trip;

        //lấy tất cả các text được điền vào trong form addtrip
        String trip_name = name.getText().toString();
        String trip_destination = destination.getText().toString();
        String trip_date = date.getText().toString();
        String trip_desc = description.getText().toString();
        String trip_accommodation = accommodation.getText().toString();
        String trip_vehicle = vehicle.getText().toString();
        Boolean trip_rra = rra.isChecked();

        // kiểm tra switch rra có được tích hay không nếu tích thì i =1 còn không thì i=0
        int i = 0;
        if (rra.isChecked()){
            i = 1;
        }

        //Kiểm tra text trong form có rỗng hay không
        if(trip_name.equals("") || trip_destination.equals("") || trip_date.equals("") || trip_accommodation.equals("")||trip_vehicle.equals("")){
            //nếu rỗng trả ra thông báo
            Toast.makeText(getActivity(),"Please Enter All The Field",Toast.LENGTH_SHORT).show();
        }else {
            //tiến hành thêm các text đã được điền vào form
            trip = new Trip(-1,trip_name,trip_destination,trip_date,trip_desc,trip_accommodation,trip_vehicle,i);
            //khi điền đủ dùng bundle để chứa các dữ liệu đã điền sang confirm_add
            Bundle bundle=new Bundle();
            //ARG_CONFIRM được tạo bên confirm_add
            //bundle.putSerializable(key,value);
            bundle.putSerializable(confirm_add.ARG_CONFIRM,trip);

            //khi di chuyển sang confirm_add set các text đã được điền về rỗng
            name.setText("");
            destination.setText("");
            date.setText("");
            description.setText("");
            accommodation.setText("");
            vehicle.setText("");

            // dùng để di chuyển từ fragment này sang fragment khác
            //di chuyển sang fragment này sang confirm_add
            Navigation.findNavController(getView()).navigate(R.id.confirm_add,bundle);
        }


    }
}