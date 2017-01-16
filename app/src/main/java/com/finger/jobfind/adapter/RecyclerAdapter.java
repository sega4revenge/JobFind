package com.finger.jobfind.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finger.jobfind.R;
import com.finger.jobfind.model.CongViec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    Activity mContext;
    private  List<CongViec> myArray;
    String uid;
    int type;
    int mtype;
    int count=0;
    String[] arr;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tencongty,tencongviec,diadiem,mucluong,ngayup,trangthai,views;
        LinearLayout lin;RelativeLayout linit;
        public MyViewHolder(View view) {
            super(view);
            tencongty = (TextView) view.findViewById(R.id.tenct);
            trangthai = (TextView) view.findViewById(R.id.trangthai);
            tencongviec = (TextView) view.findViewById(R.id.tencongviec);
            diadiem = (TextView) view.findViewById(R.id.diachi);
            mucluong=(TextView) view.findViewById(R.id.luong);
            ngayup=(TextView) view.findViewById(R.id.dateup1);
            views = (TextView) view.findViewById(R.id.views);
            lin = (LinearLayout) view.findViewById(R.id.viewCount);
            linit= (RelativeLayout) view.findViewById(R.id.linit);
        }
    }

    public RecyclerAdapter(Activity context, List<CongViec> objects, int type, String uid, int mtype){
        this.mContext=context;
        this.myArray=objects;
        this.uid=uid;
        this.type=type;
        this.mtype=mtype;
        this.arr=mContext.getResources().getStringArray(R.array.mucluong);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customitem, parent, false);
        return new RecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CongViec job = myArray.get(position);
        holder.tencongty.setText(job.getTecongty());
        int status = Integer.parseInt(job.getTrangthai());
        if(status==0)
        {
            holder.trangthai.setText(R.string.st_stt_dangcho);
        }else if(status==1){
            holder.trangthai.setText(R.string.st_stt_dachapnhan);
        }else if(status==2){
            holder.trangthai.setText(R.string.st_stt_datuchoi);
        }else if(status==4){
            holder.trangthai.setText(myArray.get(position).getDistance());
        }
        if(type==0){
            if(position<3){
                holder.lin.setVisibility(View.VISIBLE);
            }else{
                holder.lin.setVisibility(View.GONE);
            }
            holder.trangthai.setTextSize(10);
            holder.trangthai.setText(R.string.st_hintHanNop);
            holder.views.setText(" "+ job.getViews()+"");
            holder.ngayup.setText(job.getDateup());
        }else  if(type==99){
            String time= job.getDatecreate();
            Long gettime=getDateInMillis(time);
            String timeago=getTimeAgo(gettime);
            holder.ngayup.setText(timeago);
        }else{
            holder.ngayup.setText(job.getDateup());
        }
        holder.tencongviec.setText(job.getTencongviec());
        holder.diadiem.setText(job.getDiadiem());
        holder.mucluong.setText(arr[Integer.parseInt(job.getLuong())]);

    }
    @Override
    public int getItemCount() {
        return myArray.size();
    }
    public String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        long now =System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return mContext.getResources().getString(R.string.justnow);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return mContext.getResources().getString(R.string.anminute);
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS +" "+ mContext.getResources().getString(R.string.minutesago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            return mContext.getResources().getString(R.string.anhour);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS +" "+ mContext.getResources().getString(R.string.hourago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return mContext.getResources().getString(R.string.yesterday);
        } else {
            return diff / DAY_MILLIS +" "+mContext.getResources().getString(R.string.daysago);
        }
    }
    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}