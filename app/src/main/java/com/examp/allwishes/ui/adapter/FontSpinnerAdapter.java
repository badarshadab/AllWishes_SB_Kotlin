package com.examp.allwishes.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.examp.allwishes.R;


/**
 * Created by pc9 on 12/18/2017.
 */

public class FontSpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    String[] style;


    public FontSpinnerAdapter(Context context, int textViewResourceId,
                              String[] objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.style = objects;

    }

//    @Override
//    public View getDropDownView(int position, View convertView,
//                                ViewGroup parent) {
//        return getCustomView(position, convertView, parent);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.spinner_row, parent, false);
//        LayoutInflater inflater = getLayoutInflater();
//        View row = inflater.inflate(R.layout.spinner_row, parent, false);

            TextView label = (TextView) row.findViewById(R.id.textView1);
            label.setText("Select Font");
            Typeface face = Typeface.createFromAsset(context.getAssets(), style[position]);
            label.setTypeface(face);
        }

        return row;
    }

}
