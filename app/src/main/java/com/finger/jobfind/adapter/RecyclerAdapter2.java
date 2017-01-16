package com.finger.jobfind.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.finger.jobfind.R;
import com.finger.jobfind.activity.JobDetailActivity;
import com.finger.jobfind.activity.MainActivity;
import com.finger.jobfind.model.CongViec;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by VinhNguyen on 1/13/2017.
 */

public class RecyclerAdapter2  extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {
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
    private List<CongViec> myArray;
    private List<CongViec> myArray2;
    String uid;
    int type;
    int mtype;
    int count=0,item=0;
    String[] arr;
    JSONArray arritem = new JSONArray();
    LinearLayout lin,linjr;
    TSnackbar snackbar;
    int[] array;
    int[] array2;
    int stt=0;
    int[] num;
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

    public RecyclerAdapter2(Activity context, List<CongViec> objects, int type, String uid, int mtype, LinearLayout lin,LinearLayout linjr){
        this.mContext=context;
        this.myArray=objects;
        this.uid=uid;
        this.type=type;
        this.mtype=mtype;
        this.arr=mContext.getResources().getStringArray(R.array.mucluong);
        this.lin=lin;
        this.array=new int[myArray.size()];
        this.array2=new int[myArray.size()];
        this.linjr=linjr;
        this.count=0;
        this.item=0;
        this.arritem=new JSONArray();
        for(int i=0;i<myArray.size();i++){
            array[i]=0;
            array2[i]=0;
        }
        this.myArray2=new ArrayList<CongViec>();

    }

    @Override
    public RecyclerAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customitem, parent, false);
        return new RecyclerAdapter2.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter2.MyViewHolder holder, final int position) {
        final CongViec job = myArray.get(position);
        holder.tencongty.setText(job.getTecongty());
        holder.tencongviec.setText(job.getTencongviec());
        holder.diadiem.setText(job.getDiadiem());
        holder.trangthai.setTextSize(10);
        holder.trangthai.setText(R.string.st_hintHanNop);
        holder.ngayup.setText(job.getDateup());
        holder.mucluong.setText(arr[Integer.parseInt(job.getLuong())]);
            holder.linit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(count<1)
                    {
                        count=1;
                        item=1;
                        array[position]=1;
                        array2[position]=position;
                        try {
                            arritem.put(position,myArray.get(position).getMacv());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        holder.linit.setBackgroundResource(R.color.selected);
                        lin.setPadding(0,150,0,0);
                        snackbar = TSnackbar.make(lin, mContext.getResources().getString(R.string.selecte)+" "+item+" "+mContext.getResources().getString(R.string.of)+" "+myArray.size()+" "+mContext.getResources().getString(R.string.tab_ProfileManger)+" ", TSnackbar.LENGTH_INDEFINITE);
                        snackbar.setActionTextColor(Color.WHITE);

                        snackbar.setAction("Xoa", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.linit.setBackgroundResource(R.color.white);
                                for(int i=0;i<myArray.size();i++)
                                {
                                      if(array[i]!=1){
                                            myArray2.add(myArray.get(i));
                                    }
                                }
                                myArray.clear();
                                myArray.addAll(myArray2);
                                myArray2.clear();
                                notifyDataSetChanged();
                                if(myArray.size()==0)
                                {
                                    linjr.setVisibility(View.VISIBLE);
                                }

                                count=0;
                                item=0;

                                lin.setPadding(0,0,0,0);
                                String id = MainActivity.uid+"";
                                array=new int[myArray.size()];
                                array2=new int[myArray.size()];
                                for(int i=0;i<myArray.size();i++){
                                    array[i]=0;
                                    array2[i]=0;
                                }
                                String serverUrl = "http://quickjob.ga/xoacongviec.php";
                                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                                asyncRequestObject.execute(serverUrl,arritem.toString(),id);
                            }
                        });
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundResource(R.color.colorPrimary);
                        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();
                    }
                    return false;
                }
            });
        holder.linit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==0)
                {
                    Intent s = new Intent(mContext, JobDetailActivity.class);
                    s.putExtra("tencongty", job.getTecongty());
                    s.putExtra("tencongviec", job.tencongviec);
                    s.putExtra("diadiem", job.getDiadiem());
                    s.putExtra("mucluong", job.getLuong());
                    s.putExtra("ngayup", job.getDateup());
                    s.putExtra("yeucaubangcap", job.getBangcap());
                    s.putExtra("dotuoi", job.getDotuoi());
                    s.putExtra("ngoaingu", job.getNgoaingu());
                    s.putExtra("gioitinh", job.getGioitinh());
                    s.putExtra("khac", job.getKhac());
                    s.putExtra("motacv", job.getMotact());
                    s.putExtra("kn", job.getKn());
                    s.putExtra("macv", job.getMacv());
                    s.putExtra("img", job.getUrl());
                    s.putExtra("sdt", job.getSdt());
                    s.putExtra("motact", job.getMotact());
                    s.putExtra("type", 4);
                    mContext.startActivity(s);
                }else{
                        if(array[position]==0)
                        {

                            array[position] = 1;
                            array2[position]=position;
                            item=item+1;
                            snackbar.setText(mContext.getResources().getString(R.string.selecte)+" "+item+" "+mContext.getResources().getString(R.string.of)+" "+myArray.size()+" "+mContext.getResources().getString(R.string.job));
                            try {
                                arritem.put(position,myArray.get(position).getMacv());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            holder.linit.setBackgroundResource(R.color.selected);

                        }else{
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                arritem.remove(position);
                                item=item-1;
                                array[position]=0;
                                array2[position]=0;
                                snackbar.setText(mContext.getResources().getString(R.string.selecte)+" "+item+" "+mContext.getResources().getString(R.string.of)+" "+myArray.size()+" "+mContext.getResources().getString(R.string.job));
                           }else{
                               item=item-1;
                               array[position]=0;
                               array2[position]=0;
                               snackbar.setText(mContext.getResources().getString(R.string.selecte)+" "+item+" "+mContext.getResources().getString(R.string.of)+" "+myArray.size()+" "+mContext.getResources().getString(R.string.job));
                               try {
                                    arritem.put(position,"");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            array[position] = 0;
                            holder.linit.setBackgroundResource(R.color.white);
                        }
                }
            }
        });
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

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("listitem", params[1]));
                nameValuePairs.add(new BasicNameValuePair("id", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                return;
            }

            try {
                JSONObject resultObject = new JSONObject(result);
                if (resultObject.getString("success") == "1") {
                    Toast.makeText(mContext, R.string.toast_delete, Toast.LENGTH_SHORT).show();
                 //   adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(mContext, resultObject.getString("error_msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            stt=0;
        }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = br.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return answer;
    }
}
}
