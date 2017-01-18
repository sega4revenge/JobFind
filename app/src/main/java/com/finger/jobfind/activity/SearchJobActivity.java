package com.finger.jobfind.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import static com.finger.jobfind.R.id.spdiadiem;

public class SearchJobActivity extends AppCompatActivity {
    String[] arr = {"Tất cả ngành nghề", "Dược sỹ", "Trình dược viên", "Bảo trì/Sửa chửa", "Bán hàng", "Bảo hiểm", "Bất động sản", "Biên dịch viên", "Chứng khoáng", "Công nghệ cao", "IT Phần cứng/Mạng", "Internet/Online Media", "IT -Phần mềm", "Cơ khí,chế tạo", "Dệt may/Da giày", "Dịch vụ khách hàng", "Hàng không/Du lịch", "Điện/Điện tử", "Giáo dục/Đào tạo", "Kế toán", "Kiểm toán", "Y tế/Chăm sóc sức khỏe", "Kiến trúc/Thiết kế nội thất", "Ngân hàng", "Mỹ thuật/Nghệ thuật/Thiết kế", "Nhân sự", "Nông nghiệp/Lâm nghiệp", "Pháp lý", "Phi chính phủ/Phi lợi nhuận", "Cấp quản lý điều hành", "Quản cáo/Khuyến mại/Đối thoại", "Sản Xuất", "Thời vụ/Hợp đồng ngắn hạn", "Tài chính/Đầu tư", "Thời trang", "Thực phẩm đồ uống", "Truyền hình/Truyền thông/Báo chí", "Marketing", "Tư vấn", "Vận chuyển/Giao nhận", "Thu mua/Vật tư/Cung vận", "Viễn thông", "Xây dựng", "Xuất nhập khẩu", "Tự động hóa/Ôtô", "Overseass Jop", "Khác"};
    String[] arr2 = {"Tất cả địa điểm", "Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "An Giang", "Bà Rịa-Vũng Tàu", "Bắc Cạn", "Bắc Giang", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Biên Hòa", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cần Thơ", "Cao Bằng", "Đắc Lắc", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tây", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hòa Bình", "Huế", "Hưng Yên", "Khánh hòa", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa-Thiên-Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Kiên Giang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Quốc Tế", "Hậu Giang", "Khác"};
    private List<CongViec> celebrities = new ArrayList<>();
    List<CongViec> tempArrayList;
    JSONArray mang;
    int countdata;
    int begin = 0;
    int status = 0;
    GPSTracker gps;
    int beginloadmore = 0;
    int have = 0;
    private RecyclerAdapter adapter = null;
    String diadiem1 = "", ml = "", nn = "";
    private EditText tencv;
    private Spinner nganhnghe, mucluong, diadiem;
    private ArrayAdapter adapternn, adapterdd, adapterluong;
    private ProgressBar progressBar;
    AsyncDataClass asyncRequestObject;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    boolean loading = true;
    private int previousTotal = 0;
    private boolean loading2= true;
    private int visibleThreshold = 5;
    int st=0;
    int firstVisibleItem, visibleItemCount, totalItemCount,pastVisiblesItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        addView();

        loading = true;
        Intent i =getIntent();
        int status = i.getIntExtra("status",1);
        if(status==0){
            asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(AppConfig.URL_XUATTK_NANGCAO,nn,diadiem1,ml,beginloadmore+"");
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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                st=1;
                int textlength = newText.length();
                tempArrayList = new ArrayList<CongViec>();
                for(CongViec c: celebrities){
                    if (textlength <= c.tencongviec.length()) {
                        if (c.tencongviec.toLowerCase().contains(newText.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }else{
                            //celebrities.remove(c);
                        }
                      //  adapter.notifyDataSetChanged();
                        adapter = new RecyclerAdapter(SearchJobActivity.this, tempArrayList, tempArrayList.size(), "", 1);
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
    private void addView() {
        progressBar = (ProgressBar) findViewById(R.id.pbLoader);
        nganhnghe = (Spinner) findViewById(R.id.spnganh);
        diadiem = (Spinner) findViewById(spdiadiem);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(SearchJobActivity.this, celebrities, 1, "", 1);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

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
                            asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(AppConfig.URL_XUATTK_NANGCAO,nn,diadiem1,ml,beginloadmore+"");

                        }
                    }
                }
            }
        });




         String[] arrnn = getResources().getStringArray(R.array.nganhNghe);
        String[] arrdd = getResources().getStringArray(R.array.diadiem);
        adapternn = new ArrayAdapter<String>(SearchJobActivity.this, R.layout.customspniner, arrnn);
        adapternn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nganhnghe.setAdapter(adapternn);


        adapterdd = new ArrayAdapter<String>(SearchJobActivity.this, R.layout.customspniner, arrdd);
        adapterdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diadiem.setAdapter(adapterdd);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchJobActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        if(st==0) {
                        intentdetail(celebrities,i);
                        }else{
                          intentdetail(tempArrayList,i);
                        }


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
        nganhnghe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!loading) {
                    loading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    int pos1 = nganhnghe.getSelectedItemPosition();
                    if (pos1 > 0) {
                        nn = nganhnghe.getSelectedItemPosition()+"";
                    } else {
                        nn="";
                    }
                    search(nn,diadiem1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        diadiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!loading) {
                    loading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    int pos2 = diadiem.getSelectedItemPosition();
                    if (pos2 > 0) {
                        diadiem1 = arr2[pos2];
                    } else {
                        diadiem1="";
                    }
                    search(nn,diadiem1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void intentdetail(List<CongViec> list,int pos){
        Intent s = new Intent(SearchJobActivity.this, JobDetailActivity.class);
        s.putExtra("tencongty", list.get(pos).tecongty);
        s.putExtra("tencongviec", list.get(pos).tencongviec);
        s.putExtra("diadiem", list.get(pos).diadiem);
        s.putExtra("mucluong", list.get(pos).luong);
        s.putExtra("ngayup", list.get(pos).dateup);
        s.putExtra("yeucaubangcap", list.get(pos).bangcap);
        s.putExtra("dotuoi", list.get(pos).dotuoi);
        s.putExtra("ngoaingu", list.get(pos).ngoaingu);
        s.putExtra("gioitinh", list.get(pos).gioitinh);
        s.putExtra("khac", list.get(pos).khac);
        s.putExtra("motacv", list.get(pos).motacv);
        s.putExtra("kn", list.get(pos).kn);
        s.putExtra("macv", list.get(pos).macv);
        s.putExtra("img", list.get(pos).url);
        s.putExtra("sdt", list.get(pos).sdt);
        s.putExtra("motact", list.get(pos).motact);
        s.putExtra("matd", list.get(pos).matd);
        s.putExtra("type", 3);
        startActivity(s);
    }

    private void refreshContent() {
        begin=0;
        if(celebrities!= null)
        {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        have = 0;
        status = 0;
        beginloadmore=0;
        mSwipeRefreshLayout.setRefreshing(false);
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATTK_NANGCAO,nn,diadiem1,ml,beginloadmore+"");

    }
    public void search(String nganhnghe,String diadiem){
        st=0;
        if (adapter != null) {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        adapter = new RecyclerAdapter(SearchJobActivity.this, celebrities, 2, "", 1);
        recyclerView.setAdapter(adapter);
        begin=1;
        loading = true;
        beginloadmore=0;
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATTK_NANGCAO,nn,diadiem1,ml,beginloadmore+"");
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
                 nameValuePairs.add(new BasicNameValuePair("nganhnghe", params[1]));
                 nameValuePairs.add(new BasicNameValuePair("diadiem", params[2]));
                 nameValuePairs.add(new BasicNameValuePair("mucluong", params[3]));
                 nameValuePairs.add(new BasicNameValuePair("page", params[4]));
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
                Toast.makeText(SearchJobActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
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
                        cv.setTrangthai("3");
                        celebrities.add(cv);
                    }
                    adapter.notifyDataSetChanged();
                    have = 1;
                    status = 1;
                } else {
                    if (have == 0) {
                        Toast.makeText(SearchJobActivity.this, R.string.toast_notfind, Toast.LENGTH_SHORT).show();
                    }
                    status = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loading = false;
            loading2 = true;
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



}

