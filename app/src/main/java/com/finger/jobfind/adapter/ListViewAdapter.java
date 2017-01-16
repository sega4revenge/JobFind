package com.finger.jobfind.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.finger.jobfind.R;
import com.finger.jobfind.config.AppController;
import com.finger.jobfind.model.Profile;
import com.finger.jobfind.other.CircleTransform;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VinhNguyen on 7/8/2016.
 */
public class ListViewAdapter extends ArrayAdapter<Profile>
{
    Context mcontext;
    int status;
   // String macv = JobDetailActivity.macv;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ArrayList<Profile> celebrities = new ArrayList<Profile>();
    ArrayList<Profile> myArray=new ArrayList<Profile>();
    String[] arrnganh,arrhv,arrsalary,arrsex;
    public ListViewAdapter(Context context, int layoutId, List<Profile> objects, int status){
        super(context, layoutId, objects);
        this.mcontext=context;
        this.myArray=new ArrayList<Profile>(objects);
        this.status=status;
        arrhv= mcontext.getResources().getStringArray(R.array.spHocVan);
        arrsalary= mcontext.getResources().getStringArray(R.array.mucluong);
        arrsex= mcontext.getResources().getStringArray(R.array.sex);
        arrnganh= mcontext.getResources().getStringArray(R.array.nganhNghe);


    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder=null;

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.customitems,null);
        viewHolder = new ViewHolder();
        viewHolder.txt1 = (TextView) rowView.findViewById(R.id.txt1);
        viewHolder.txt2 = (TextView) rowView.findViewById(R.id.txt2);
        viewHolder.txt3 = (TextView) rowView.findViewById(R.id.txt3);
        viewHolder.txt4 = (TextView) rowView.findViewById(R.id.txt4);
        viewHolder.txt5 = (TextView) rowView.findViewById(R.id.txt5);
        viewHolder.lin = (LinearLayout) rowView.findViewById(R.id.liner);
        viewHolder.thumbNail = (ImageView) rowView.findViewById(R.id.thumbnail);

        Glide.with(mcontext).load(myArray.get(position).img)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(mcontext))
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .into(viewHolder.thumbNail);

        viewHolder.txt1.setText(arrnganh[Integer.parseInt(myArray.get(position).nganhnghe)]);
        viewHolder.txt2.setText(myArray.get(position).vitri);
        viewHolder.txt3.setText(arrsalary[Integer.parseInt(myArray.get(position).mucluong)]);
        viewHolder.txt4.setText(myArray.get(position).diadiem);
        viewHolder.txt5.setText(myArray.get(position).ngaydang);
//        viewHolder.lin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(status==0) {
//                    Intent i = new Intent(mcontext, EditProfileActivity.class);
//                    i.putExtra("key", 1);
//                    i.putExtra("mahs", myArray.get(position).id);
//                    i.putExtra("nn", myArray.get(position).nganhnghe);
//                    i.putExtra("vitri", myArray.get(position).vitri);
//                    i.putExtra("mucluong", myArray.get(position).mucluong);
//                    i.putExtra("diadiem", myArray.get(position).diadiem);
//                    i.putExtra("ngaydang", myArray.get(position).ngaydang);
//                    i.putExtra("ten", myArray.get(position).ten);
//                    i.putExtra("quequan", myArray.get(position).quequan);
//                    i.putExtra("sdt", myArray.get(position).sdt);
//                    i.putExtra("gioitinh", myArray.get(position).gioitinh);
//                    i.putExtra("email", myArray.get(position).email);
//                    i.putExtra("diachi", myArray.get(position).diachi);
//                    i.putExtra("ngaysinh", myArray.get(position).ngaysinh);
//                    i.putExtra("tentruong", myArray.get(position).tentruong);
//                    i.putExtra("chuyennganh", myArray.get(position).chuyennganh);
//                    i.putExtra("xeploai", myArray.get(position).xeploai);
//                    i.putExtra("thanhtuu", myArray.get(position).thanhtuu);
//                    i.putExtra("namkn", myArray.get(position).namkn);
//                    i.putExtra("tencongty", myArray.get(position).tencongty);
//                    i.putExtra("chucdanh", myArray.get(position).chucdanh);
//                    i.putExtra("mota", myArray.get(position).motacv);
//                    i.putExtra("ngoaingu", myArray.get(position).ngoaingu);
//                    i.putExtra("kynang", myArray.get(position).kynang);
//                    i.putExtra("tencv", myArray.get(position).tencv);
//                    i.putExtra("uid", myArray.get(position).uid);
//                    i.putExtra("logo", myArray.get(position).img);
//                    mcontext.startActivity(i);
//
//                }else{
//                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
//                    asyncRequestObject.execute(AppConfig.URL_NOPHS, myArray.get(position).id, macv);
//                }
//
//            }
//        });
        return rowView;
    }


    static class ViewHolder{
        TextView txt1,txt2,txt3,txt4,txt5;
        LinearLayout lin; ImageView thumbNail;
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
                nameValuePairs.add(new BasicNameValuePair("mahs", params[1]));
                nameValuePairs.add(new BasicNameValuePair("macv", params[2]));
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
                Toast.makeText(mcontext, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mcontext,result+"", Toast.LENGTH_SHORT).show();

        }
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