package com.finger.jobfind.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.finger.jobfind.R;

import java.util.ArrayList;

/**
 * Created by SONTHO on 2016-10-19.
 */

public class NewCVAdapter extends BaseAdapter {

    ArrayList<String> data;
    Context context;
    private LayoutInflater inflater=null;
    public NewCVAdapter(ArrayList<String> data, Context context) {
        // TODO Auto-generated constructor stub
        this.data = data;
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder=null;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_new_job,null);

            viewHolder = new ViewHolder();
            viewHolder.txt = (TextView) rowView.findViewById(R.id.namecv);

            rowView.setTag(viewHolder);
        } else{viewHolder = (ViewHolder) convertView.getTag();}

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rowView;
    }
    static class ViewHolder{
        TextView txt;

    }
}

