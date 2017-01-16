package com.finger.jobfind.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finger.jobfind.R;
import com.finger.jobfind.activity.MainActivity;
import com.finger.jobfind.adapter.RecyclerAdapter2;
import com.finger.jobfind.config.AppConfig;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobSaveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobSaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobSaveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int mProgressStatus = 0;


    private LinearLayout lnjr;
    private RecyclerView recyclerView;
    private View view;
    private String uid;
    private LinearLayout progressBar;
//    private AlertDialog progressDialog;
    private RecyclerAdapter2 adapter = null;
    private List<CongViec> celebrities = new ArrayList<CongViec>();
    private JSONArray mang;
    boolean stt =false;
    private OnFragmentInteractionListener mListener;
    AsyncDataClass asyncRequestObject;
    int sm=0;
    LinearLayout coordinatorLayout;
    int[]  pos;
    public JobSaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobSaveFragment newInstance(String param1, String param2) {
        JobSaveFragment fragment = new JobSaveFragment();
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
        view = inflater.inflate(R.layout.fragment_job_saves,container,false);
        coordinatorLayout = (LinearLayout) view.findViewById(R.id.coor);


        lnjr = (LinearLayout) view.findViewById(R.id.linjr);
        progressBar = (LinearLayout) view.findViewById(R.id.load);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final Intent i = getActivity().getIntent();
        uid= MainActivity.uid;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

     /*   recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        if(!stt) {
                            try {
                                JSONObject ob = mang.getJSONObject(i);
                                Intent s = new Intent(getActivity(), JobDetailActivity.class);
                                s.putExtra("tencongty", ob.getString("tenct"));
                                s.putExtra("tencongviec", ob.getString("tencv"));
                                s.putExtra("diadiem", ob.getString("diadiem"));
                                s.putExtra("mucluong", ob.getString("mucluong"));
                                s.putExtra("ngayup", ob.getString("hannophoso"));
                                s.putExtra("yeucaubangcap", ob.getString("bangcap"));
                                s.putExtra("dotuoi", ob.getString("dotuoi"));
                                s.putExtra("ngoaingu", ob.getString("ngoaingu"));
                                s.putExtra("gioitinh", ob.getString("gioitinh"));
                                s.putExtra("khac", ob.getString("khac"));
                                s.putExtra("motacv", ob.getString("motacv"));
                                s.putExtra("kn", ob.getString("kynang"));
                                s.putExtra("macv", ob.getString("macv"));
                                s.putExtra("img", ob.getString("img"));
                                s.putExtra("sdt", ob.getString("phone"));
                                s.putExtra("motact", ob.getString("motact"));
                                s.putExtra("type", 4);
                                startActivity(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override public boolean onLongItemClick(View view, final int position) {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Text", Snackbar.LENGTH_LONG);

                        snackbar.show();

//                        stt=true;
//                        if(mang.length()>0)
//                        {
//                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage(R.string.st_xacNhanXoaCV);
//                            builder.setCancelable(false);
//                            builder.setPositiveButton(R.string.st_xacNhanOK, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    try {
//                                        JSONObject ob =mang.getJSONObject(position);
//                                        String macv= ob.getString("macv");
//                                        String serverUrl = "http://quickjob.ga/xoacongviec.php";
//                                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
//                                        asyncRequestObject.execute(serverUrl,macv, uid,"1");
//                                        stt=false;
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                            builder.setNegativeButton(R.string.st_xacNhanCancel, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                    stt=false;
//                                }
//                            });
//                            builder.show();
//                        }
                        return false;
                    }
                })
        ); */


        return view;
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
//                Toast.makeText(getActivity(), R.string.st_errServer, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                return;
            }
                try {
                    mang = new JSONArray(result);
                    if (mang.length() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mang.length(); i++) {
                            JSONObject ob = mang.getJSONObject(i);
                            CongViec cv = new CongViec();
                            cv.setMacv(ob.getString("macv"));
                            cv.setTencongviec(ob.getString("tencv"));
                            cv.setTecongty(ob.getString("tenct"));
                            cv.setLuong(ob.getString("mucluong"));
                            cv.setDiadiem(ob.getString("diadiem"));
                            cv.setDateup(ob.getString("hannophoso"));
                            cv.setBangcap(ob.getString("bangcap"));
                            cv.setMotacv(ob.getString("motacv"));
                            cv.setDotuoi(ob.getString("dotuoi"));
                            cv.setNgoaingu(ob.getString("ngoaingu"));
                            cv.setGioitinh(ob.getString("gioitinh"));
                            cv.setKhac(ob.getString("khac"));
                            cv.setKn(ob.getString("kynang"));
                            cv.setUrl(ob.getString("img"));
                            cv.setSdt(ob.getString("phone"));
                            cv.setMotact(ob.getString("motact"));
                            cv.setTrangthai("3");
                            celebrities.add(cv);

                        }
                        adapter = new RecyclerAdapter2(getActivity(), celebrities, 2, uid, 99,coordinatorLayout,lnjr);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        lnjr.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//            progressDialog.dismiss();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onResume() {
        super.onResume();
        lnjr.setVisibility(View.GONE);
        if(adapter!= null)
        {
            celebrities.clear();
            adapter.notifyDataSetChanged();
        }
        asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(AppConfig.URL_XUATCV_DALUU, uid,"","0");
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
