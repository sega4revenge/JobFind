package com.finger.jobfind.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.firebase.MyFirebaseInstanceIDService;
import com.finger.jobfind.fragment.JobManager;
import com.finger.jobfind.fragment.ProfileFragment;
import com.finger.jobfind.fragment.SearchFragment;
import com.finger.jobfind.pref.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.finger.jobfind.pref.SessionManager.KEY_LOGO;

public class MainActivity extends AppCompatActivity   {

    private String emailpref="", namepref="",birthdate="",sexef,phone="",andress="",homeless="", logo = "";
    static String logopref="";
    public static String uid, email1;
    int status=0;
    SessionManager session;
    private static final String TAG_SEARCH = "search";
    AsyncDataClass asyncRequestObject;
    private String[] activityTitles;
    public static double latitude;
    public static double longitude;
    private static final String TAG = "MyFirebaseIIDService";
    SharedPreferences.Editor edit;
    GPSTracker gps;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar mToolbar;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton fabMenu;
    public static String countryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("JobFindPref", MODE_PRIVATE);
        edit = pref.edit();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        emailpref = user.get(SessionManager.KEY_EMAIL);
        namepref = user.get(SessionManager.KEY_NAME);
        logopref = user.get(KEY_LOGO);
        uid = user.get(SessionManager.KEY_ID);

        fabMenu = (FloatingActionButton) findViewById(R.id.fabMenu);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        MyFirebaseInstanceIDService s = new MyFirebaseInstanceIDService();
        s.sendRegistrationToServer(refreshedToken, uid);

        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        Locale.setDefault(new Locale("vi_VN"));
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {

                countryName = addresses.get(0).getAdminArea();
            }else{
                countryName="";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        initToolbar();
        initViewPagerAndTabs();
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_TakeLogo, uid);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tabCall = tabLayout.getTabAt(position);
                switch (position) {
                    case 0:
                        setToolbarTitle(0);
                        showFab();
                        tabCall.setIcon(R.drawable.icon_menus);
                        break;
                    case 1:
                        backtop();
                        setToolbarTitle(1);
                        hideFab();
                        tabCall.setIcon(R.drawable.icons_menu2);
                        TabLayout.Tab tabCall2 = tabLayout.getTabAt(0);
                        tabCall2.setIcon(R.drawable.icon_menus);
                        break;
                    case 2:
                        setToolbarTitle(2);
                        showFab();
                        tabCall.setIcon(R.drawable.icon_menu3);
                        TabLayout.Tab tabCall3 = tabLayout.getTabAt(0);
                        tabCall3.setIcon(R.drawable.icon_menus);
                        break;
                    case 3:
                        setToolbarTitle(3);
                        showFab();
                        tabCall.setIcon(R.drawable.icon_menu4);
                        TabLayout.Tab tabCall4 = tabLayout.getTabAt(0);
                        tabCall4.setIcon(R.drawable.icon_menus);
                        break;
                    default:
                        tabCall.setIcon(R.drawable.ic_home_white_24dp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mToolbar.getLayoutParams();
//                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//                if (behavior != null) {
//                    behavior.onNestedFling(coordinatorLayout, mToolbar, null, 0, 10000, true);
//                }
            }
        });
        setupTabIcons();
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,CreateProfileActivity.class);
                i.putExtra("name",namepref);
                i.putExtra("birthdate",birthdate);
                i.putExtra("sex",sexef);
                i.putExtra("email",emailpref);
                i.putExtra("phone",phone);
                i.putExtra("andress",andress);
                i.putExtra("homeless",homeless);
                i.putExtra("logo",logo);
                i.putExtra("uniqueid",uid);
                i.putExtra("status",status);
                startActivity(i);
            }
        });
    }

    private void hideFab() {
        if (fabMenu.getVisibility() == View.VISIBLE) {
           fabMenu.setVisibility(View.GONE);
        }
    }
    private void showFab() {
        if (fabMenu.getVisibility() == View.GONE) {
            fabMenu.setVisibility(View.VISIBLE);
        }
    }


    private void setToolbarTitle(int pos) {
        getSupportActionBar().setTitle(activityTitles[pos]);
    }
    private void backtop() {
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appBarLayout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appbar, null, 0, -1000, true);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_library_books_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_assignment_ind_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account_circle_black_24dp);
    }
    private void initToolbar() {
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        setSupportActionBar(mToolbar);
        setToolbarTitle(0);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new SearchFragment(),"");
        pagerAdapter.addFragment(new JobManager(), "");
        pagerAdapter.addFragment(new ProfileFragment(), "");
        pagerAdapter.addFragment(new SettingActivity(), "");
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main, menu);
      return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                gps = new GPSTracker(this);
                if(gps.canGetLocation()){
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Intent intent = new Intent(MainActivity.this,NearMapsActivity.class);
                    intent.putExtra("lat",latitude);
                    intent.putExtra("lng",longitude);
                    startActivity(intent);
                }else{
                    gps.showSettingsAlert();
                    //gps.getLocation();
                }
                return true;
            case R.id.search:
                Intent i = new Intent(this,SearchJobActivity.class);
                i.putExtra("status",0);
                startActivity(i);
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_TakeLogo, uid);
        super.onResume();
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            String jsonResult = "";
            try {

                    nameValuePairs.add(new BasicNameValuePair("uid", params[1]));

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
                Toast.makeText( MainActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject obs = new JSONObject(result);
                int stt = obs.getInt("success");
                if(stt==1)
                {
                    logo=obs.getString("avata");
                    emailpref=obs.getString("email");
                    namepref=obs.getString("name");
                    birthdate=obs.getString("birthdate");
                    phone=obs.getString("phone");
                    andress=obs.getString("andress");
                    sexef=obs.getString("sex");
                    homeless=obs.getString("homeless");
                    logopref=logo;
                    edit.putString("logo", logo);
                    edit.putString("email", emailpref);
                    edit.commit();
                    status=1;

                }else{
                    logo="";
                    status=0;
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( asyncRequestObject!= null) {
            if (!asyncRequestObject.isCancelled()) {
                asyncRequestObject.cancel(true);
            }
        }
    }

}
