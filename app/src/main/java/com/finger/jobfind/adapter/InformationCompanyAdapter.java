package com.finger.jobfind.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.finger.jobfind.R;
import com.finger.jobfind.model.CongViec;

import java.util.List;

/**
 * Created by VinhNguyen on 1/3/2017.
 */

public class InformationCompanyAdapter  extends RecyclerView.Adapter<InformationCompanyAdapter.MyViewHolder> {

private List<CongViec> joblist;
    Activity mContext;
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView  nameconpany;
    public ImageView logocompany;

    public MyViewHolder(View view) {
        super(view);
        nameconpany = (TextView) view.findViewById(R.id.txtnamecompany);
        logocompany = (ImageView) view.findViewById(R.id.logocompany);
    }
}

    public InformationCompanyAdapter(List<CongViec> joblist,Activity context ){
        this.joblist = joblist;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_inforcompany, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CongViec movie = joblist.get(position);
        holder.nameconpany.setText(movie.getTecongty());
        Glide.with(mContext).load(joblist.get(position).url)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .skipMemoryCache( true )
                .into(holder.logocompany);
    }

    @Override
    public int getItemCount() {
        return joblist.size();
    }
}

