package com.finger.jobfind.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.finger.jobfind.R;

import java.util.Calendar;

/**
 * Created by SONTHO on 2016-10-26.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);


    }
//    private String getAge(int year, int month, int day){
//        Calendar dob = Calendar.getInstance();
//        Calendar today = Calendar.getInstance();
//
//        dob.set(year, month, day);
//
//        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//
//        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
//            age--;
//        }
//
//        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();
//
//        return ageS;
//    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        ((TextView) getActivity().findViewById(R.id.tvAge)).setText(""+day +"/"+month+"/"+year);


    }
}