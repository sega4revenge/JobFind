package com.finger.jobfind.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.adapter.RecyclerAdapter;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.model.CongViec;
import com.finger.jobfind.pref.RecyclerItemClickListener;

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



public class MoreNew_Activity extends AppCompatActivity {
    JSONArray mang;
    private RecyclerAdapter adapter = null;
    AsyncDataClass asyncRequestObject;
    int countdata,beginloadmore=0;
    boolean loading;
    private ProgressBar progressBar;
    private List<CongViec> celebrities = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    int firstVisibleItem, visibleItemCount, totalItemCount,pastVisiblesItems;
    private boolean loading2= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_new);
        setContentView(R.layout.search_morehot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        addView();

        loading = true;
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_MORENEW,beginloadmore+"");

    }

    private void addView() {
        progressBar = (ProgressBar) findViewById(R.id.pbLoader);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(MoreNew_Activity.this, celebrities, 99, "", 1);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(MoreNew_Activity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading2)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading2 = false;
                            Log.v("...", "Last Item Wow !");
                            asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(AppConfig.URL_MORENEW,beginloadmore+"");

                        }
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MoreNew_Activity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Intent s = new Intent(MoreNew_Activity.this, JobDetailActivity.class);
                        s.putExtra("tencongty", celebrities.get(i).tecongty);
                        s.putExtra("tencongviec", celebrities.get(i).tencongviec);
                        s.putExtra("diadiem", celebrities.get(i).diadiem);
                        s.putExtra("mucluong", celebrities.get(i).luong);
                        s.putExtra("ngayup", celebrities.get(i).dateup);
                        s.putExtra("yeucaubangcap", celebrities.get(i).bangcap);
                        s.putExtra("dotuoi", celebrities.get(i).dotuoi);
                        s.putExtra("ngoaingu", celebrities.get(i).ngoaingu);
                        s.putExtra("gioitinh", celebrities.get(i).gioitinh);
                        s.putExtra("khac", celebrities.get(i).khac);
                        s.putExtra("motacv", celebrities.get(i).motacv);
                        s.putExtra("kn", celebrities.get(i).kn);
                        s.putExtra("macv", celebrities.get(i).macv);
                        s.putExtra("img", celebrities.get(i).url);
                        s.putExtra("sdt", celebrities.get(i).sdt);
                        s.putExtra("motact", celebrities.get(i).motact);
                        s.putExtra("matd", celebrities.get(i).matd);
                        s.putExtra("type", 3);
                        startActivity(s);

                    }

                    @Override
                    public boolean onLongItemClick(View view, int position) {
                        // do whatever
                        return false;
                    }
                })
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

        });
    }
    private void refreshContent() {
        beginloadmore=0;
        if(celebrities!= null)
        {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_MORENEW,beginloadmore+"");
        mSwipeRefreshLayout.setRefreshing(false);
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
                nameValuePairs.add(new BasicNameValuePair("page", params[1]));
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
                Toast.makeText(MoreNew_Activity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                return;
            }
            try {
                mang = new JSONArray(result);
                countdata = mang.length();
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
                        cv.setDatecreate(ob.getString("ngaydang"));
                        cv.setTrangthai("3");
                        celebrities.add(cv);
                    }
                    adapter.notifyDataSetChanged();
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loading = false;
            beginloadmore = beginloadmore + 1;
            progressBar.setVisibility(View.GONE);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MoreNew_Activity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int textlength = newText.length();
                List<CongViec> tempArrayList = new ArrayList<CongViec>();
                for(CongViec c: celebrities){
                    if (textlength <= c.tencongviec.length()) {
                        if (c.tencongviec.toLowerCase().contains(newText.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }else{
                            // celebrities.remove(c);
                        }
                        // adapter.notifyDataSetChanged();
                        adapter = new RecyclerAdapter(MoreNew_Activity.this, tempArrayList, 0, "", 1);
                        recyclerView.setAdapter(adapter);
                    }
                }

                return false;
            }
        });
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
