package com.finger.jobfind.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.activity.GPSTracker;
import com.finger.jobfind.activity.Information_Company;
import com.finger.jobfind.activity.JobDetailActivity;
import com.finger.jobfind.activity.MainActivity;
import com.finger.jobfind.activity.MoreHotAcitivity;
import com.finger.jobfind.activity.MoreNew_Activity;
import com.finger.jobfind.activity.NeedJobActivity;
import com.finger.jobfind.adapter.InformationCompanyAdapter;
import com.finger.jobfind.adapter.RecyclerAdapter;
import com.finger.jobfind.config.AppConfig;
import com.finger.jobfind.model.CongViec;
import com.finger.jobfind.pref.RecyclerItemClickListener;
import com.finger.jobfind.pref.SessionManager;

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

import static com.finger.jobfind.activity.MainActivity.countryName;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String[] arr={"Tất cả ngành nghề","Dược sỹ","Trình dược viên","Bảo trì/Sửa chửa","Bán hàng","Bảo hiểm","Bất động sản","Biên dịch viên","Chứng khoáng","Công nghệ cao","IT Phần cứng/Mạng","Internet/Online Media","IT -Phần mềm","Cơ khí,chế tạo","Dệt may/Da giày","Dịch vụ khách hàng","Hàng không/Du lịch","Điện/Điện tử","Giáo dục/Đào tạo","Kế toán","Kiểm toán","Y tế/Chăm sóc sức khỏe","Kiến trúc/Thiết kế nội thất","Ngân hàng","Mỹ thuật/Nghệ thuật/Thiết kế","Nhân sự","Nông nghiệp/Lâm nghiệp","Pháp lý","Phi chính phủ/Phi lợi nhuận","Cấp quản lý điều hành","Quản cáo/Khuyến mại/Đối thoại","Sản Xuất","Thời vụ/Hợp đồng ngắn hạn","Tài chính/Đầu tư","Thời trang","Thực phẩm đồ uống","Truyền hình/Truyền thông/Báo chí","Marketing","Tư vấn","Vận chuyển/Giao nhận","Thu mua/Vật tư/Cung vận","Viễn thông","Xây dựng","Xuất nhập khẩu","Tự động hóa/Ôtô","Overseass Jop","Khác"};
    String[] arr2={"Tất cả địa điểm","Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "An Giang", "Bà Rịa-Vũng Tàu", "Bắc Cạn", "Bắc Giang", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Biên Hòa", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cần Thơ", "Cao Bằng", "Đắc Lắc", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tây", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hòa Bình", "Huế", "Hưng Yên", "Khánh hòa", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa-Thiên-Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Kiên Giang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Quốc Tế", "Hậu Giang", "Khác"};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<CongViec> celebrities = new ArrayList<>();
    private List<CongViec> celebrities2 = new ArrayList<>();
    private List<CongViec> celebrities3 = new ArrayList<>();
    private List<CongViec> celebrities4= new ArrayList<>();
    private List<CongViec> secondlist = new ArrayList<>();
    Button btmorenew,btmorehot,btmorenear;
    int countdata;
    int begin = 0;
    int status = 0;
    boolean loading;
    int beginloadmore=0;
    int have=0;
    private RecyclerAdapter adapter = null,adapter_hot=null,adapter_near=null;
    private InformationCompanyAdapter adapter_company = null;
    String uid="",namecity;
    private EditText tencv;
    private View view;
    private ProgressBar progressBar;
    LinearLayout linlv, linmore, linhot, linnear;
    AsyncDataClass asyncRequestObject;
    SessionManager session;
     double latitude;
     double longitude;
    TextView txt,txt2,txt3,txt4;
    private RecyclerView recyclerView,recyclerView_company,recyclerView_hot,recyclerView_near;
    private OnFragmentInteractionListener mListener;
    GPSTracker gps;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        begin = 0;
        if (adapter != null) {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        if (adapter_hot != null) {
            celebrities2.clear();
            adapter_hot.notifyDataSetChanged();
        }
        if (adapter_near != null) {
            celebrities3.clear();
            adapter_near.notifyDataSetChanged();
        }
        if (adapter_company != null) {
            celebrities4.clear();
            adapter_company.notifyDataSetChanged();
        }
        latitude=MainActivity.latitude;
        longitude=MainActivity.longitude;

        addView();
        eventmore();
        loading = true;
        namecity= countryName;

        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATCV_NEW_HOT,namecity);
        return view;
    }

    private void eventmore() {
        linmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MoreNew_Activity.class);
                i.putExtra("status",0);
                startActivity(i);
            }
        });
        linhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MoreHotAcitivity.class);
                startActivity(i);
            }
        });
        linnear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),NeedJobActivity.class);
                i.putExtra("lat",latitude);
                i.putExtra("lng",longitude);
                startActivity(i);
            }
        });
    }

    private void addView() {
        txt =(TextView) view.findViewById(R.id.txt);
        txt2 =(TextView) view.findViewById(R.id.txt2);
        txt3 =(TextView) view.findViewById(R.id.txt3);
        txt4 =(TextView) view.findViewById(R.id.txt7);
        linmore = (LinearLayout) view.findViewById(R.id.linmore);
        linhot = (LinearLayout) view.findViewById(R.id.linhot);
        linnear = (LinearLayout) view.findViewById(R.id.linnear);
        progressBar = (ProgressBar) view.findViewById(R.id.pbLoader);
//        btmorenew = (Button) view.findViewById(R.id.more);
//        btmorehot = (Button) view.findViewById(R.id.more2);
//        btmorenear = (Button) view.findViewById(R.id.more3);
        hideText();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView_near = (RecyclerView) view.findViewById(R.id.recycler_view_near);
        recyclerView_near.setScrollContainer(false);
        adapter_near = new RecyclerAdapter(getActivity(), celebrities3, 3, "", 3);
        recyclerView_near.setAdapter(adapter_near);
        LinearLayoutManager linearLayoutManager_near = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_near.setLayoutManager(linearLayoutManager_near);
        recyclerView_near.setItemAnimator(new DefaultItemAnimator());
        recyclerView_near.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        Intent s = new Intent(getActivity(), JobDetailActivity.class);
                        s.putExtra("tencongty", celebrities3.get(i).tecongty);
                        s.putExtra("tencongviec", celebrities3.get(i).tencongviec);
                        s.putExtra("diadiem", celebrities3.get(i).diadiem);
                        s.putExtra("mucluong", celebrities3.get(i).luong);
                        s.putExtra("ngayup", celebrities3.get(i).dateup);
                        s.putExtra("yeucaubangcap", celebrities3.get(i).bangcap);
                        s.putExtra("dotuoi", celebrities3.get(i).dotuoi);
                        s.putExtra("ngoaingu", celebrities3.get(i).ngoaingu);
                        s.putExtra("gioitinh", celebrities3.get(i).gioitinh);
                        s.putExtra("khac", celebrities3.get(i).khac);
                        s.putExtra("motacv", celebrities3.get(i).motacv);
                        s.putExtra("kn", celebrities3.get(i).kn);
                        s.putExtra("macv", celebrities3.get(i).macv);
                        s.putExtra("img", celebrities3.get(i).url);
                        s.putExtra("sdt", celebrities3.get(i).sdt);
                        s.putExtra("motact", celebrities3.get(i).motact);
                        s.putExtra("matd", celebrities3.get(i).matd);
                        s.putExtra("type", 3);
                        startActivity(s);

                    }

                    @Override public boolean onLongItemClick(View view, int position) {
                        // do whatever
                        return false;
                    }
                })
        );

        recyclerView_hot =(RecyclerView) view.findViewById(R.id.recycler_view_hot);
        recyclerView_hot.setScrollContainer(false);
        adapter_hot = new RecyclerAdapter(getActivity(), celebrities2, 0, "", 3);
        recyclerView_hot.setAdapter(adapter_hot);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_hot.setLayoutManager(linearLayoutManager);
        recyclerView_hot.setItemAnimator(new DefaultItemAnimator());
        recyclerView_hot.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        Intent s = new Intent(getActivity(), JobDetailActivity.class);
                        s.putExtra("tencongty", celebrities2.get(i).tecongty);
                        s.putExtra("tencongviec", celebrities2.get(i).tencongviec);
                        s.putExtra("diadiem", celebrities2.get(i).diadiem);
                        s.putExtra("mucluong", celebrities2.get(i).luong);
                        s.putExtra("ngayup", celebrities2.get(i).dateup);
                        s.putExtra("yeucaubangcap", celebrities2.get(i).bangcap);
                        s.putExtra("dotuoi", celebrities2.get(i).dotuoi);
                        s.putExtra("ngoaingu", celebrities2.get(i).ngoaingu);
                        s.putExtra("gioitinh", celebrities2.get(i).gioitinh);
                        s.putExtra("khac", celebrities2.get(i).khac);
                        s.putExtra("motacv", celebrities2.get(i).motacv);
                        s.putExtra("kn", celebrities2.get(i).kn);
                        s.putExtra("macv", celebrities2.get(i).macv);
                        s.putExtra("img", celebrities2.get(i).url);
                        s.putExtra("sdt", celebrities2.get(i).sdt);
                        s.putExtra("motact", celebrities2.get(i).motact);
                        s.putExtra("matd", celebrities2.get(i).matd);
                        s.putExtra("type", 3);
                        startActivity(s);

                    }
                    @Override public boolean onLongItemClick(View view, int position) {

                        return false;
                    }
                })
        );

        recyclerView_company=(RecyclerView) view.findViewById(R.id.newcompany) ;
        recyclerView_company.setScrollContainer(false);
        adapter_company = new InformationCompanyAdapter(celebrities4, getActivity());
        recyclerView_company.setAdapter(adapter_company);
        LinearLayoutManager linearLayoutManager_company = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_company.setLayoutManager(linearLayoutManager_company);
        recyclerView_company.setItemAnimator(new DefaultItemAnimator());
        recyclerView_company.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        Intent s = new Intent(getActivity(), Information_Company.class);
                        s.putExtra("nameconpany", celebrities4.get(i).tecongty);
                        s.putExtra("andress", celebrities4.get(i).diachict);
                        s.putExtra("career", celebrities4.get(i).nganhNghe);
                        s.putExtra("infor", celebrities4.get(i).getMotact());
                        s.putExtra("logo", celebrities4.get(i).getUrl());
                        s.putExtra("macv", celebrities4.get(i).getMacv());
                        startActivity(s);
                    }

                    @Override public boolean onLongItemClick(View view, int position) {
                        // do whatever
                        return false;
                    }
                })
        );


        recyclerView.setScrollContainer(false);
        adapter = new RecyclerAdapter(getActivity(), celebrities, 99, "", 3);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager_hot = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager_hot);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        Intent s = new Intent(getActivity(), JobDetailActivity.class);
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

                    @Override public boolean onLongItemClick(View view, int position) {
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
    public void hideText(){
        txt.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        txt4.setVisibility(View.GONE);
//        btmorehot.setVisibility(View.GONE);
//        btmorenear.setVisibility(View.GONE);
//        btmorenew.setVisibility(View.GONE);
        linmore.setVisibility(View.GONE);
        linhot.setVisibility(View.GONE);
        linnear.setVisibility(View.GONE);
    }
    public void showText(){
        txt.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.VISIBLE);
        txt4.setVisibility(View.VISIBLE);
//        btmorehot.setVisibility(View.VISIBLE);
//        btmorenear.setVisibility(View.VISIBLE);
//        btmorenew.setVisibility(View.VISIBLE);
        linmore.setVisibility(View.VISIBLE);
        linhot.setVisibility(View.VISIBLE);
        linnear.setVisibility(View.VISIBLE);
    }
    private void refreshContent() {
        hideText();
        progressBar.setVisibility(View.VISIBLE);
        if(namecity==""){
            gps = new GPSTracker(getActivity());
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }else{
                gps.showSettingsAlert();
            }

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {

                namecity = addresses.get(0).getAdminArea();
            }else{
                namecity="";
            }
        }
        begin = 0;
        if (adapter != null) {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        if (adapter_hot != null) {
            celebrities2.clear();
            adapter_hot.notifyDataSetChanged();
        }
        if (adapter_near != null) {
            celebrities3.clear();
            adapter_near.notifyDataSetChanged();
        }
        if (adapter_company != null) {
            celebrities4.clear();
            adapter_company.notifyDataSetChanged();
        }
        loading = true;
        mSwipeRefreshLayout.setRefreshing(false);
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATCV_NEW_HOT,namecity);

    }
    public double getDistance(double my_lat,double my_long, double to_lat,double to_long) {

        double pk = (float) (180.f/Math.PI);

        double a1 = my_lat / pk;
        double a2 = my_long / pk;
        double b1 = to_lat / pk;
        double b2 = to_long / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
    private void location() {
        if (celebrities3.size() > 0) {
            for (int i = 0; i < celebrities3.size(); i++) {
             for(int n=i+1;n<celebrities3.size();n++)
                {
                    double lati = Double.parseDouble(celebrities3.get(i).getLat());
                    double lngi =Double.parseDouble(celebrities3.get(i).getLng());
                    double latii =Double.parseDouble(celebrities3.get(n).getLat());
                    double lngii =Double.parseDouble(celebrities3.get(n).getLng());
                    double dis1 = getDistance(latitude,longitude,lati,lngi);
                    double dis2 = getDistance(latitude,longitude,latii,lngii);
                    if(dis1>1000)
                    {
                        dis1=dis1/1000;
                        dis1=Math.round(dis1);
                        celebrities3.get(i).setDistance(dis1+" KM");

                    }else{
                        dis1=Math.round(dis1);
                        celebrities3.get(i).setDistance(dis1+" M");
                    }
                    if(dis2>1000)
                    {
                        dis2=dis2/1000;
                        dis2=Math.round(dis2);
                        celebrities3.get(n).setDistance(dis2+" KM");
                    }else{
                        dis2=Math.round(dis2);
                        celebrities3.get(n).setDistance(dis2+" M");
                    }

                    if(getDistance(latitude,longitude,lati,lngi)>getDistance(latitude,longitude,latii,lngii)){
                        secondlist.add(celebrities3.get(i));
                        celebrities3.set(i,celebrities3.get(n));
                        celebrities3.set(n,secondlist.get(0));
                        secondlist.clear();
                    }
                }
            }

            adapter_near.notifyDataSetChanged();
        }else{

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
                Toast.makeText(getActivity(),R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                return;
            }
            try {
                JSONArray jObj4 = new JSONObject(result).getJSONArray("company");
                getData3(jObj4);
                JSONArray jObj = new JSONObject(result).getJSONArray("new");
                getData(jObj,0);
                JSONArray jObj2 = new JSONObject(result).getJSONArray("hot");
                getData(jObj2,1);
                JSONArray jObj3 = new JSONObject(result).getJSONArray("near");
                getData2(jObj3,2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            showText();
            loading = false;
            beginloadmore=beginloadmore+1;
            progressBar.setVisibility(View.GONE);
        }
        public void getData(JSONArray joc,int a){
            countdata= joc.length();
                for (int i = 0; i < countdata; i++) {
                    try {
                        JSONObject ob = joc.getJSONObject(i);
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

                        if(a==0) {
                            cv.setTrangthai("3");
                            celebrities.add(cv);
                        }if(a==1)
                        {
                            cv.setTrangthai("3");
                            celebrities2.add(cv);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(a==0) {
                    adapter.notifyDataSetChanged();
                }if(a==1)
                {
                    adapter_hot.notifyDataSetChanged();
                }

        }
        public void getData2(JSONArray joc,int a){
            countdata= joc.length();
            for (int i = 0; i < countdata; i++) {
                try {
                    JSONObject ob = joc.getJSONObject(i);
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
                    cv.setTrangthai("4");
                    celebrities3.add(cv);
                          location();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            have=1;
            status=1;

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

    private void getData3(JSONArray jObj4) {
        countdata= jObj4.length();
        for (int i = 0; i < countdata; i++) {
            try {
                JSONObject ob = jObj4.getJSONObject(i);
                CongViec cv = new CongViec();
                cv.setMacv(ob.getString("uid"));
                cv.setTecongty(ob.getString("namecompany"));
                cv.setDiachict(ob.getString("diachi"));
                cv.setQuymo(ob.getString("quymo"));
                cv.setNganhNghe(ob.getString("nganhnghe"));
                cv.setMotact(ob.getString("mota"));
                cv.setUrl(ob.getString("img"));
                celebrities4.add(cv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter_company.notifyDataSetChanged();
    }

//    private void addcommpany() {
//        Toast.makeText(getActivity(), celebrities.size()+"", Toast.LENGTH_SHORT).show();
//        for (int i =0;i<celebrities.size();i++)
//        {
//            for(int n=i+1;n<=celebrities.size();n++)
//            {
//                if(celebrities.get(i).tecongty.equals(celebrities.get(n).tecongty))
//                {
//
//                }else{
//                    celebrities2.add(celebrities.get(i));
//                }
//            }
//        }
//        Toast.makeText(getActivity(), celebrities2.size()+"", Toast.LENGTH_SHORT).show();
//        adapter_company.notifyDataSetChanged();
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
