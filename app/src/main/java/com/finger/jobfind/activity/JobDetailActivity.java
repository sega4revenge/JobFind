package com.finger.jobfind.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.finger.jobfind.R;
import com.finger.jobfind.adapter.ListViewAdapter;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.fragment.CompanyDetailFragment;
import com.finger.jobfind.fragment.JobAboutFragment;
import com.finger.jobfind.model.Profile;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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

public class JobDetailActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private NetworkImageView logo;
    private ArrayList<Profile> celebrities = new ArrayList<Profile>();
    private static final int REQUEST_CALL = 0;
    private ListView lv;
    private ListViewAdapter adapter;
    public static final String TAG = "JobDetailActivity";
    public String macv;
    int status = 0,luong=0,hv=0,gt=0;
    private String id, sdt, tencv, tenct, diadiem, mucluong, ngayup, yeucaubangcap, dotuoi, ngoaingu, gioitinh, khac, motacv, kn, img,quymo,jobcompany,location,detail,matd;
    private CollapsingToolbarLayout collapsingToolbar;
    int type;
    private int mPreviousVisibleItem;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabCall, fabSave,fablocation;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_j);
//        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.floatbutton);
        status = 1;
        actionGetIntent();
        init();
        setData();
        events();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tenct);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        fabMenu.setClosedOnTouchOutside(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        collapsingToolbar.setTitleEnabled(false);



        if (type == 0 || type ==3 || type==4){
            fabMenu.setVisibility(View.VISIBLE);
        } else {
            fabMenu.setVisibility(View.GONE);
        }

        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATHS, id,"");

    }




    private void events() {

        fabCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                if (ActivityCompat.checkSelfPermission(JobDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    takePicture();
                }
            }
        });
        fablocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+diadiem);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                if (type == 0) {
                    Toast.makeText(getApplicationContext(), R.string.st_reg, Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_createlangues, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(JobDetailActivity.this);
                    String ms = getResources().getString(R.string.addProfile);
                    builder.setTitle(ms);
                    builder.setView(view);
                    lv = (ListView) view.findViewById(R.id.lvnn);
                    adapter = new ListViewAdapter(JobDetailActivity.this, android.R.layout.simple_list_item_1, celebrities, 1);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //   Toast.makeText(JobDetailActivity.this,id+macv+"",Toast.LENGTH_SHORT).show();
                            AsyncDataClass asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(AppConfig.URL_NOPHS, celebrities.get(i).id, macv);
                        }
                    });
                    builder.setNegativeButton(R.string.st_thoat, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog= builder.create();
                    dialog.show();
                }
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobAboutFragment(), getResources().getString(R.string.st_about_job));
        adapter.addFragment(new CompanyDetailFragment(), getResources().getString(R.string.st_about_company));

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setData() {
        id = MainActivity.uid;
    }

    private void init() {

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabCall = (FloatingActionButton) findViewById(R.id.fabCall);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fablocation = (FloatingActionButton) findViewById(R.id.fabmaps);
//        logo = (NetworkImageView) findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    private void actionGetIntent() {
        Intent i = getIntent();
        type= i.getIntExtra("type",1);
        tencv = i.getStringExtra("tencongviec");
        tenct = i.getStringExtra("tencongty");
        diadiem = i.getStringExtra("diadiem");
       // mucluong = ;
        luong=Integer.parseInt(i.getStringExtra("mucluong"));
        ngayup = i.getStringExtra("ngayup");
       // yeucaubangcap = i.getStringExtra("yeucaubangcap");
        if(i.getStringExtra("yeucaubangcap").equals(""))
        {

        }else{
            hv=Integer.parseInt(i.getStringExtra("yeucaubangcap"));
        }

        dotuoi = i.getStringExtra("dotuoi");
        ngoaingu = i.getStringExtra("ngoaingu");
       // gioitinh = i.getStringExtra("gioitinh");
        if(i.getStringExtra("gioitinh").equals(""))
        {

        }else{
            gt=Integer.parseInt(i.getStringExtra("gioitinh"));
        }

        khac = i.getStringExtra("khac");
        motacv = i.getStringExtra("motacv");
        kn = i.getStringExtra("kn");
        img = i.getStringExtra("img");
        macv = i.getStringExtra("macv");
        sdt = i.getStringExtra("sdt");
        quymo = i.getStringExtra("quymo");
        jobcompany = i.getStringExtra("nganhnghe");
        detail = i.getStringExtra("motact");
        location = i.getStringExtra("diachi");
    }

    private void takePicture() {
        String phone = sdt;
        String number = phone + "";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), R.string.st_loiKXD, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestCameraPermission() {
        // Camera permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
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
                Toast.makeText(JobDetailActivity.this, R.string.st_pemissonCamera, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent s= getIntent();
        matd= s.getStringExtra("matd");
        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_VIEW, matd,"");

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
                nameValuePairs.add(new BasicNameValuePair("macv", params[2]));
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
                Toast.makeText(JobDetailActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            if (result.equals("3")) {
                    Toast.makeText(JobDetailActivity.this,R.string.toast_save, Toast.LENGTH_SHORT).show();
            }
            if (result.equals("2")) {
                Toast.makeText(JobDetailActivity.this,R.string.toast_alreadysaved, Toast.LENGTH_SHORT).show();
            }
            if (result.equals("1")) {
                Toast.makeText(JobDetailActivity.this,R.string.toast_apply, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            if (result.equals("0")) {
                Toast.makeText(JobDetailActivity.this,R.string.toast_alreadyapply, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            if(status==1){
                try {

                    JSONArray mang = new JSONArray(result);
                    for (int i = 0; i < mang.length(); i++) {
                        JSONObject ob = mang.getJSONObject(i);
                        celebrities.add(new Profile(ob.getString("nganhnghe"),
                                ob.getString("vitri"), ob.getString("mucluong"),
                                ob.getString("diadiem"), ob.getString("createdate"),
                                ob.getString("mahs"), ob.getString("hoten"),
                                ob.getString("gioitinh2"),
                                ob.getString("ngaysinh"),
                                ob.getString("email"),
                                ob.getString("sdt"),
                                ob.getString("diachi"),
                                ob.getString("quequan"),
                                ob.getString("tentruong"),
                                ob.getString("chuyennganh"),
                                ob.getString("xeploai"),
                                ob.getString("thanhtuu"),
                                ob.getString("namkn"),
                                ob.getString("tencongty"),
                                ob.getString("chucdanh"),
                                ob.getString("mota"),
                                ob.getString("ngoaingu"),
                                ob.getString("kynang"),
                                ob.getString("tencv"),id,
                                ob.getString("img")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if(result.equals("1")) {

                }else{

                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type==1 || type==3)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detail_job, menu);
        }
        return true;
    }








    private void saveJobAction() {

        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_LUUCV, id, macv);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            super.onBackPressed();
        }

        if ((id == R.id.action_save)){
            saveJobAction();

        }
        if ((id == R.id.action_share)){
            ShareAction();

        }
        return super.onOptionsItemSelected(item);
    }

    private void ShareAction() {
        String message = "Tên công việc :"+tencv+"\n Địa điểm: "+diadiem+"\n Mức lương : "+mucluong+"\n Mô tả việc: "+motacv+" \n Yêu cầu: \n -  Bằng cấp:  "+yeucaubangcap+"\n - Độ tuổi: "+dotuoi+ "\n - Kỹ năng: "+kn+"\n - Ngoại ngữ:  "+ngoaingu+"\n - Khác: "+khac+"\n Hạn nộp hồ sơ :"+ngayup+"\n Tham khảo thêm tại: https://play.google.com/store/apps/details?id=com.finger.jobfind&hl=vi" ;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
    }
}
