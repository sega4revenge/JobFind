package com.finger.jobfind.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.activity.MainActivity;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.model.Profile;

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
import java.util.ArrayList;
import java.util.List;


public class JobAboutFragment extends Fragment {

    private ArrayList<Profile> celebrities = new ArrayList<Profile>();
    private static final int REQUEST_CALL = 0;
    public static final String TAG = "JobDetailActivity";
    public static String macv;
    int status = 0,luong=0,gt=0,hv=0;
    private TextView txttencv, txtdiachi, txtluong,txtbangcap, txtmotacv, txtkn, txtdotuoi, txtgt, txtnn, txtkhac, txtdate;
    private String id, sdt, tencv, tenct, diadiem, mucluong, ngayup, yeucaubangcap, dotuoi, ngoaingu, gioitinh, khac, motacv, kn,quymo,jobcompany,location,detail;
    int type;
    private View v;
    String[] arrnganh,arrhv,arrsalary,arrsex;

    public JobAboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v =  inflater.inflate(R.layout.fragment_about_job, container, false);
        arrhv= getResources().getStringArray(R.array.spHocVan);
        arrsalary= getResources().getStringArray(R.array.mucluong);
        arrsex= getResources().getStringArray(R.array.sex);
        arrnganh= getResources().getStringArray(R.array.nganhNghe);
        init();
        actionGetIntent();

        txtdiachi.setText(diadiem);
        txtluong.setText(arrsalary[luong] + " VND");
        txtdate.setText(ngayup + "");
        txtmotacv.setText(motacv + "");
        txttencv.setText(tencv + "");
        txtbangcap.setText(arrhv[hv] + "");
        txtkn.setText(kn + "");
        txtdotuoi.setText(dotuoi + "");
        txtgt.setText(arrsex[gt] + "");
        txtnn.setText(ngoaingu + "");
        txtkhac.setText(khac + "");
        id = MainActivity.uid;


        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATHS, id);





        return v;


    }








    private void takePicture() {
        String phone = sdt;
        String number = phone + "";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), R.string.st_loiKXD, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestCameraPermission() {


        // Camera permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_CALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Camera permission has been granted, preview can be displayed

                takePicture();

            } else {
                //Permission not granted
                Toast.makeText(getActivity(), R.string.st_pemissonCamera, Toast.LENGTH_SHORT).show();
            }

        }
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

                nameValuePairs.add(new BasicNameValuePair("id", params[1]));
                if (status == 1) {
                    nameValuePairs.add(new BasicNameValuePair("macv", params[2]));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

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
            if (result.equals("") || result == null) {


                Toast.makeText(getActivity(), R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            if (status == 0) {
                try {

                    JSONArray mang = new JSONArray(result);

                    for (int i = 0; i < mang.length(); i++) {
                        JSONObject ob = mang.getJSONObject(i);
                        celebrities.add(new Profile(ob.getString("nganhnghe"), ob.getString("vitri"), ob.getString("mucluong"), ob.getString("diadiem"), ob.getString("createdate"), ob.getString("mahs"), ob.getString("hoten"), ob.getString("gioitinh2"), ob.getString("ngaysinh"), ob.getString("email"), ob.getString("sdt"), ob.getString("diachi"), ob.getString("quequan"), ob.getString("tentruong"), ob.getString("chuyennganh"), ob.getString("xeploai"), ob.getString("thanhtuu"), ob.getString("namkn"), ob.getString("tencongty"), ob.getString("chucdanh"), ob.getString("mota"), ob.getString("ngoaingu"), ob.getString("kynang"), ob.getString("tencv"), id, ob.getString("img")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();
            }

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


    //    private void loadBackdrop() {
    //      logo.setImageUrl(hinhanh, imageLoader);
    //   }




//        @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//            switch (item.getItemId()){
//                case android.R.id.home:
//
//                    return true;
//
//                case R.id.action_favorite:
//                    saveJobAction();
//                    return true;
//
//                default:
//                    return super.onOptionsItemSelected(item);
//            }
////        if (item.getItemId() == android.R.id.home) {
////            onBackPressed();
////            return true;
////        }

//    }



    private void saveJobAction() {
        status = 1;
        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_LUUCV, id, macv);
    }

    private void init() {



        txttencv = (TextView)  v.findViewById((R.id.txttencv));
        txtdiachi = (TextView)  v.findViewById(R.id.txtdiadiem);
        txtluong = (TextView) v.findViewById((R.id.txtluong));
        txtbangcap = (TextView) v.findViewById(R.id.txtbc);
        txtmotacv = (TextView) v.findViewById((R.id.txtmotacv));
        txtkn = (TextView) v.findViewById((R.id.txtnamkn));
        txtdotuoi = (TextView) v.findViewById((R.id.txtdotuoi));
        txtgt = (TextView) v.findViewById(R.id.txtgioitinh);
        txtnn = (TextView) v.findViewById((R.id.txtnn));
        txtkhac = (TextView) v.findViewById((R.id.txtkhac));
        txtdate = (TextView) v.findViewById(R.id.txthethan);



    }

    private void actionGetIntent() {
        Intent i = getActivity().getIntent();
        type= i.getIntExtra("type",1);
        tencv = i.getStringExtra("tencongviec");
        tenct = i.getStringExtra("tencongty");
        diadiem = i.getStringExtra("diadiem");
        // mucluong = ;
        luong=Integer.parseInt(i.getStringExtra("mucluong"));
        ngayup = i.getStringExtra("ngayup");
        // yeucaubangcap = i.getStringExtra("yeucaubangcap");
        hv=Integer.parseInt(i.getStringExtra("yeucaubangcap"));
        dotuoi = i.getStringExtra("dotuoi");
        ngoaingu = i.getStringExtra("ngoaingu");
        // gioitinh = i.getStringExtra("gioitinh");
        gt=Integer.parseInt(i.getStringExtra("gioitinh"));
        khac = i.getStringExtra("khac");
        motacv = i.getStringExtra("motacv");
        kn = i.getStringExtra("kn");
        macv = i.getStringExtra("macv");
        sdt = i.getStringExtra("sdt");
        quymo = i.getStringExtra("quymo");
        jobcompany = i.getStringExtra("nganhnghe");
        detail = i.getStringExtra("motact");
        location = i.getStringExtra("diachi");


    }





}
