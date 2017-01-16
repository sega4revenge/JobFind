package com.finger.jobfind.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finger.jobfind.R;
import com.finger.jobfind.activity.CreateProfileActivity;
import com.finger.jobfind.activity.EditProfileActivity;
import com.finger.jobfind.activity.MainActivity;
import com.finger.jobfind.adapter.ListViewAdapter;
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

import static com.finger.jobfind.R.id.lin;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private LinearLayout lin2, pLoad;
    int hoso;
    private ListView lv;
    private ListViewAdapter adapter;
    private ArrayList<Profile> celebrities = new ArrayList<Profile>();
    private String id;
    private TextView ss;
    int status=0;
    private AlertDialog progressDialog;
    private String ten="",gt="",ns="",em="",p="",dc="",qq="",logo1="";
    private ProgressBar progressBar;
    JSONArray mang;
    AsyncDataClass asyncRequestObject;


    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        ss = (TextView) rootView.findViewById(lin);
        lin2 = (LinearLayout) rootView.findViewById(R.id.lin2);
        pLoad = (LinearLayout) rootView.findViewById(R.id.load);
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar3);

        id= MainActivity.uid;
        if(adapter!= null)
        {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
//        asyncRequestObject = new AsyncDataClass();
//        asyncRequestObject.execute(AppConfig.URL_XUATHS, id);
        lv =(ListView) rootView.findViewById(R.id.listhoso);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject ob = mang.getJSONObject(i);
                    Intent s = new Intent(getActivity(), EditProfileActivity.class);
                    s.putExtra("key", 1);
                    s.putExtra("mahs", ob.getString("mahs"));
                    s.putExtra("nn",  ob.getString("nganhnghe"));
                    s.putExtra("vitri", ob.getString("vitri"));
                    s.putExtra("mucluong",ob.getString("mucluong"));
                    s.putExtra("diadiem",  ob.getString("diadiem"));
                    s.putExtra("ngaydang", ob.getString("createdate"));
                    s.putExtra("ten", ob.getString("hoten"));
                    s.putExtra("quequan", ob.getString("quequan"));
                    s.putExtra("sdt", ob.getString("sdt"));
                    s.putExtra("gioitinh", ob.getString("gioitinh2"));
                    s.putExtra("email", ob.getString("email"));
                    s.putExtra("diachi", ob.getString("diachi"));
                    s.putExtra("ngaysinh", ob.getString("ngaysinh"));
                    s.putExtra("tentruong", ob.getString("tentruong"));
                    s.putExtra("chuyennganh",  ob.getString("chuyennganh"));
                    s.putExtra("xeploai", ob.getString("xeploai"));
                    s.putExtra("thanhtuu", ob.getString("thanhtuu"));
                    s.putExtra("namkn",ob.getString("namkn"));
                    s.putExtra("tencongty",ob.getString("tencongty"));
                    s.putExtra("chucdanh", ob.getString("chucdanh"));
                    s.putExtra("mota", ob.getString("mota"));
                    s.putExtra("ngoaingu", ob.getString("ngoaingu"));
                    s.putExtra("kynang",  ob.getString("kynang"));
                    s.putExtra("tencv", ob.getString("tencv"));
                    s.putExtra("uid", id);
                    s.putExtra("logo",ob.getString("img"));
                    startActivity(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
//        progressDialog.show();
        lin2.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        if(adapter!= null)
        {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATHS, id);
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

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", params[1]));
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
                Toast.makeText(getActivity(), R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                progressBar.setVisibility(View.GONE);
                pLoad.setVisibility(View.GONE);


                return;
            }
            try {
                mang = new JSONArray(result);
                if(mang.length()>0) {
                    status=1;
                    JSONObject obs = mang.getJSONObject(0);
                    ten = obs.getString("hoten");
                    gt = obs.getString("gioitinh2");
                    ns = obs.getString("ngaysinh");
                    em = obs.getString("email");
                    qq = obs.getString("quequan");
                    dc = obs.getString("diachi");
                    p = obs.getString("sdt");
                    logo1 = obs.getString("img");
                    int stt =obs.getInt("status");
                    if(stt==0){
                        for (int i = 0; i < mang.length(); i++) {
                            JSONObject ob = mang.getJSONObject(i);
                            celebrities.add(new Profile(
                                    ob.getString("nganhnghe"),
                                    ob.getString("vitri"),
                                    ob.getString("mucluong"),
                                    ob.getString("diadiem"),
                                    ob.getString("createdate"),
                                    ob.getString("mahs"),
                                    ob.getString("hoten"),
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
                                    ob.getString("tencv"),
                                    id,
                                    ob.getString("img")));

                        }
                        lin2.setVisibility(View.VISIBLE);
                        ss.setVisibility(View.GONE);
                    }else{
                        ss.setVisibility(View.VISIBLE);
                    }
                }else {
                    ss.setVisibility(View.VISIBLE);
                }
                try {
                    adapter = new ListViewAdapter(getActivity(), android.R.layout.simple_list_item_1, celebrities, 0);
                    lv.setAdapter(adapter);
                }catch  (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            progressDialog.dismiss();
//            progressBar.setVisibility(View.GONE);
            pLoad.setVisibility(View.GONE);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                // do stuff
                createCV();
                return true;

        }

        return false;
    }
    private void createCV() {
        Intent i = new Intent(getActivity(), CreateProfileActivity.class);
        i.putExtra("uniqueid", id);
        i.putExtra("hoten", ten);
        i.putExtra("ngaysinh", ns);
        i.putExtra("gioitinh", gt);
        i.putExtra("email", em);
        i.putExtra("sdt", p);
        i.putExtra("diachi", dc);
        i.putExtra("quequan", qq);
        i.putExtra("logo", logo1);
        i.putExtra("status", status);
        i.putExtra("key", 0);
        startActivity(i);
    }
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