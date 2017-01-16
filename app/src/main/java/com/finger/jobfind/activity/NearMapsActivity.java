package com.finger.jobfind.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.model.CongViec;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.Locale;

public class NearMapsActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    AsyncDataClass asyncRequestObject;
    Double[] latitude;
    Double[] longtitude;
    Marker mPerth;
    private Marker mSydney;
    private List<CongViec> celebrities = new ArrayList<>();
    private GoogleMap mMap;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        Intent i =getIntent();
        lat = i.getDoubleExtra("lat",20);
        lng = i.getDoubleExtra("lng",20);
        String  namecity="";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {

            namecity = addresses.get(0).getAdminArea();
        }else{
            namecity="";
        }

        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_MAPSNEAR,namecity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));
        LatLng me = new LatLng(lat, lng);
        mSydney = mMap.addMarker(new MarkerOptions()
                .position(me)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.human_icon))
                .title("Me"));
        mSydney.setTag(19931995);
        for(int i=0; i<celebrities.size();i++)
        {
            LatLng PERTH = new LatLng(Double.parseDouble(celebrities.get(i).getLat()),Double.parseDouble(celebrities.get(i).getLng()));
            mPerth = mMap.addMarker(new MarkerOptions()
                    .position(PERTH)
                    .snippet(celebrities.get(i).getLuong())
                    .snippet("Company:"+celebrities.get(i).getTecongty())
                    .title(celebrities.get(i).getTencongviec()));
            mPerth.setTag(i);
        }

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        if(clickCount!=19931995) {
            Intent s = new Intent(this, JobDetailActivity.class);
            s.putExtra("tencongty", celebrities.get(clickCount).tecongty);
            s.putExtra("tencongviec", celebrities.get(clickCount).tencongviec);
            s.putExtra("diadiem", celebrities.get(clickCount).diadiem);
            s.putExtra("mucluong", celebrities.get(clickCount).luong);
            s.putExtra("ngayup", celebrities.get(clickCount).dateup);
            s.putExtra("yeucaubangcap", celebrities.get(clickCount).bangcap);
            s.putExtra("dotuoi", celebrities.get(clickCount).dotuoi);
            s.putExtra("ngoaingu", celebrities.get(clickCount).ngoaingu);
            s.putExtra("gioitinh", celebrities.get(clickCount).gioitinh);
            s.putExtra("khac", celebrities.get(clickCount).khac);
            s.putExtra("motacv", celebrities.get(clickCount).motacv);
            s.putExtra("kn", celebrities.get(clickCount).kn);
            s.putExtra("macv", celebrities.get(clickCount).macv);
            s.putExtra("img", celebrities.get(clickCount).url);
            s.putExtra("sdt", celebrities.get(clickCount).sdt);
            s.putExtra("motact", celebrities.get(clickCount).motact);
            s.putExtra("matd", celebrities.get(clickCount).matd);
            s.putExtra("type", 3);
            startActivity(s);
        }
        return false;
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
                nameValuePairs.add(new BasicNameValuePair("location", params[1]));
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
                Toast.makeText(NearMapsActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                return;
            }
            try {
                JSONArray mang = new JSONArray(result);
                int countdata = mang.length();
                if (countdata > 0) {
                    for (int i = 0; i < countdata; i++) {
                        JSONObject ob = mang.getJSONObject(i);
                        CongViec cv = new CongViec();
                        cv.setMacv(ob.getString("macv"));
                        cv.setTencongviec(ob.getString("tencv"));
                        cv.setTecongty(ob.getString("tenct"));
                        cv.setDiachict(ob.getString("diadiem"));
                        cv.setQuymo(ob.getString("quymo"));
                        cv.setNganhNghe(ob.getString("nganhnghe"));
                        cv.setMotact(ob.getString("motact"));
                        cv.setLuong(ob.getString("mucluong"));
                        cv.setDiadiem(ob.getString("diadiem"));
                        cv.setDateup(ob.getString("hannophoso"));
                        cv.setMotacv(ob.getString("motacv"));
                        cv.setBangcap(ob.getString("bangcap"));
                        cv.setNgoaingu(ob.getString("ngoaingu"));
                        cv.setDotuoi(ob.getString("dotuoi"));
                        cv.setGioitinh(ob.getString("gioitinh"));
                        cv.setKhac(ob.getString("khac"));
                        cv.setKn(ob.getString("kynang"));
                        cv.setUrl(ob.getString("img"));
                        cv.setSdt(ob.getString("sdt"));
                        cv.setMatd(ob.getString("matd"));
                        cv.setViews(ob.getString("views"));
                        cv.setLat(ob.getString("lat"));
                        cv.setLng(ob.getString("long"));
                        cv.setTrangthai("3");
                        celebrities.add(cv);
                    }
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(NearMapsActivity.this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
}
