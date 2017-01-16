package com.finger.jobfind.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finger.jobfind.R;
import com.finger.jobfind.config.AppConfig;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by SONTHO on 15/08/2016.
 */
public class EditProfileActivity extends AppCompatActivity {
    private RadioGroup rdgroup;
    private RadioButton rdnam, rdnu;
    private Button ngaysinh, update, del;
    private EditText spvitri, edname, edmail, edphone, eddiachi, edquequan, edtentruong, edchuyennganh, edthanhtuu, ednamkinhnghiem, edtencongty, edchucdanh, edmotacongviec, edtencv, edluong;
    private Spinner spxeploai, mucluong, nganhNghe, eddd;
    private LinearLayout lin, ngoaingu1, ngoaingu2, ngoaingu3, lin2, kynang1, kynang2, kynang3;
    private TextView nn1, nn2, nn3, kn1, kn2, kn3, txtNN, txtKN;
    private ImageView upimage;
    private ImageButton n1, n2, n3, k1, k2, k3, themnn, themkn;
    private int key = 0, key2 = 0, key3 = 0, key4 = 0, key5 = 0, key6 = 0;
    private String mahs = "", uid, ngoaiNgu = "", kyNang = "", gioitinh = "", uniqueid = "",logo1="", nN, vt, ml, dd, t, qq, phone, gt, em, dc, ns, tt, cn, xl, thanhtuu, nkn, tct, cd, mt, Nn, Kn, Tcv; ;
    private Calendar calendar;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private int year, month, day;
    ProgressDialog pDialog;
    String[] arrnganh,arrhv,arrsalary,arrsex;

    int status=0;
    private ArrayAdapter adapter, adaptennr, adapterdiadiem, adapterluong;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        arrhv= getResources().getStringArray(R.array.spHocVan);
        arrsalary= getResources().getStringArray(R.array.mucluong);
        arrsex= getResources().getStringArray(R.array.sex);
        arrnganh= getResources().getStringArray(R.array.nganhNghe);

        getData();
        events();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        }

    private void events() {
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });


        themkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.dialog_createskill, null);
//                builder.setTitle(R.string.st_kinang);
                builder.setView(view1);
                builder.setPositiveButton(R.string.st_thoat, null);
                builder.setNegativeButton(R.string.st_chon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText e = (EditText) view1.findViewById(R.id.edkynang);

                        String k = e.getText().toString();
                        if (k == "") {
                        } else {
                            if (key4 == 0) {
                                kynang1.setVisibility(View.VISIBLE);
                                kn1.setText(k + "");
                                key4 = 1;
                            } else if (key5 == 0) {
                                kynang2.setVisibility(View.VISIBLE);
                                kn2.setText(k + "");
                                key5 = 1;
                            } else {
                                kynang3.setVisibility(View.VISIBLE);
                                kn3.setText(k + "");
                                key6 = 1;
                            }
                        }
                    }
                });

                builder.create().show();
            }
        });
        themnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtNN.setVisibility(View.GONE);
                final String[] ngoaingu = getResources().getStringArray(R.array.spNgoaiNgu);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle(R.string.st_ngoaingu)
                        .setItems(R.array.spNgoaiNgu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                if (key == 0) {
                                    ngoaingu1.setVisibility(View.VISIBLE);
                                    nn1.setText(ngoaingu[position] + "");
                                    key = 1;
                                } else if (key2 == 0) {
                                    ngoaingu2.setVisibility(View.VISIBLE);
                                    nn2.setText(ngoaingu[position] + "");
                                    key2 = 1;
                                } else {
                                    ngoaingu3.setVisibility(View.VISIBLE);
                                    nn3.setText(ngoaingu[position] + "");
                                    key3 = 1;
                                }
                            }
                        });
                builder.create().show();
            }
        });
        rdgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idnam = rdnam.getId();
                int idnu = rdnu.getId();
                if (checkedId == idnam) {
                    gioitinh = "1";
                } else if (checkedId == idnu) {
                    gioitinh = "2";
                }else{
                    gioitinh = "0";
                }
            }
        });
        upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(AppConfig.URL_DELETEPROFILE,"1",uniqueid,mahs);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = edmail.getText().toString();
                phone = edphone.getText().toString();
                dc = eddiachi.getText().toString();
                qq = edquequan.getText().toString();
                tt = edtentruong.getText().toString();
                cn = edchuyennganh.getText().toString();
                 thanhtuu = edthanhtuu.getText().toString();
                nkn = ednamkinhnghiem.getText().toString();
                tct = edtencongty.getText().toString();
                cd = edchucdanh.getText().toString();
                mt = edmotacongviec.getText().toString();
                Tcv = edtencv.getText().toString();
                int nN = nganhNghe.getSelectedItemPosition();
                // ml = String.valueOf(mucluong.getSelectedItem());
                int ml = mucluong.getSelectedItemPosition();
                dd = String.valueOf(eddd.getSelectedItem());
                 vt = spvitri.getText().toString();
                t = edname.getText().toString();
                ns = ngaysinh.getText().toString();
               // xl = String.valueOf(spxeploai.getSelectedItem());
                 xl = spxeploai.getSelectedItemPosition()+"";
                if (t == "" || em == "" || phone == "" || dc == "" || qq == "" || tt == "" || cn == "" || xl == "Xếp loại" || nkn == "" || tct == "" || cd == "" || ngaysinh.getText() == "Chọn Ngày" || ngaysinh.getText() == "//"|| dd == ""  || vt == "" || mahs == "") {
                    Toast.makeText(EditProfileActivity.this, R.string.st_err_taocv, Toast.LENGTH_SHORT).show();

                } else {
                    if (key != 0) {
                        ngoaiNgu = nn1.getText().toString();
                    }
                    if (key2 != 0) {
                        ngoaiNgu = ngoaiNgu + " " + nn2.getText().toString();
                    }
                    if (key3 != 0) {
                        ngoaiNgu = ngoaiNgu + " " + nn3.getText().toString();
                    }

                    if (key4 != 0) {
                        kyNang = kn1.getText().toString();
                    }
                    if (key5 != 0) {
                        kyNang = kyNang + " / " + kn2.getText().toString();
                    }
                    if (key6 != 0) {
                        kyNang = kyNang + " / " + kn3.getText().toString();
                    }
                    if(status==1)
                    {
                        uploadImage();
                    }else{
                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        asyncRequestObject.execute(AppConfig.URL_EDIT_PROFILE, uniqueid, gioitinh, ns, em, phone, dc, qq, tt, cn, xl, thanhtuu, nkn, tct, cd, mt, ngoaiNgu, kyNang, t, Tcv, dd, ml+"", nN+"", vt, mahs, uid);

                    }
                    }
            }
        });

    }

    private void getData() {
        Intent s = getIntent();
        addView();
        uniqueid=MainActivity.uid;
        mahs = s.getStringExtra("mahs");
        uid = s.getStringExtra("uid");
        nN = s.getStringExtra("nn");
        vt = s.getStringExtra("vitri");
        ml = s.getStringExtra("mucluong");
        dd = s.getStringExtra("diadiem");
        t = s.getStringExtra("ten");
        qq = s.getStringExtra("quequan");
        phone = s.getStringExtra("sdt");
        gt = s.getStringExtra("gioitinh");
        em = s.getStringExtra("email");
        dc = s.getStringExtra("diachi");
        ns = s.getStringExtra("ngaysinh");
        tt = s.getStringExtra("tentruong");
        cn = s.getStringExtra("chuyennganh");
        xl = s.getStringExtra("xeploai");
        thanhtuu = s.getStringExtra("thanhtuu");
        nkn = s.getStringExtra("namkn");
        tct = s.getStringExtra("tencongty");
        cd = s.getStringExtra("chucdanh");
        mt = s.getStringExtra("mota");
        Nn = s.getStringExtra("ngoaingu");
        Kn = s.getStringExtra("kynang");
        Tcv = s.getStringExtra("tencv");
        logo1=s.getStringExtra("logo");
        if(logo1=="")
        {}else{
            new LoadImage().execute(logo1);
        }
        edtencv.setText(Tcv + "");
        edchucdanh.setText(cd + "");
        edchuyennganh.setText(cn + "");
        edname.setText(t + "");
        if (gt.equals("1")) {
            rdnam.setChecked(true);
            gioitinh = "1";
        } else if (gt.equals("2")){
            rdnu.setChecked(true);
            gioitinh = "2";
        }
        ngaysinh.setText(ns + "");
        edmail.setText(em + "");
        edphone.setText(phone + "");
        eddiachi.setText(dc + "");
        edquequan.setText(qq + "");
        edtentruong.setText(tt + "");
       // int pos = adapter.getPosition(xl);
        spxeploai.setSelection(Integer.parseInt(xl));
        edthanhtuu.setText(thanhtuu + "");
        ednamkinhnghiem.setText(nkn + "");
        edtencongty.setText(tct);
        edmotacongviec.setText(mt + "");
       // int posnn = adaptennr.getPosition(nN);
        nganhNghe.setSelection(Integer.parseInt(nN));
     //   int posluong = adapterluong.getPosition(ml);
        mucluong.setSelection(Integer.parseInt(ml));
        int posdd = adapterdiadiem.getPosition(dd);
        eddd.setSelection(posdd);
        spvitri.setText(vt + "");
        String[] arr = Nn.split(" ");
        int a = 1;
        for (int i = 0; i < arr.length; i++) {
            if (a == 1) {
                ngoaingu1.setVisibility(View.VISIBLE);
                nn1.setText(arr[i] + "");
                key = 1;
                a++;
                txtNN.setVisibility(View.GONE);
            } else if (a == 2) {
                ngoaingu2.setVisibility(View.VISIBLE);
                nn2.setText(arr[i] + "");
                key2 = 1;
                a++;
            } else if (a == 3) {
                ngoaingu3.setVisibility(View.VISIBLE);
                nn3.setText(arr[i] + "");
                key3 = 1;
            }
            String[] arrkn = Kn.split(" ");
            int b = 1;
            for (int e = 0; e < arrkn.length; e++) {
                if (b == 1) {
                    kynang1.setVisibility(View.VISIBLE);
                    kn1.setText(arrkn[e] + "");
                    key4 = 1;
                    b++;
                    txtKN.setVisibility(View.GONE);
                } else if (b == 2) {
                    kynang2.setVisibility(View.VISIBLE);
                    kn2.setText(arrkn[e] + "");
                    key5 = 1;
                    b++;
                } else if (b == 3) {
                    kynang3.setVisibility(View.VISIBLE);
                    kn3.setText(arrkn[e] + "");
                    key6 = 1;
                }
            }



            n1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key = 0;
                    ngoaingu1.setVisibility(View.GONE);

                }
            });
            n2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key2 = 0;
                    ngoaingu2.setVisibility(View.GONE);
                }
            });
            n3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key3 = 0;
                    ngoaingu3.setVisibility(View.GONE);
                }
            });
            k1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key4 = 0;
                    kynang1.setVisibility(View.GONE);
                }
            });
            k2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key5 = 0;
                    kynang2.setVisibility(View.GONE);
                }
            });
            k3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key6 = 0;
                    kynang3.setVisibility(View.GONE);
                }
            });
    }

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                upimage.setImageBitmap(bitmap);
                status=1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, getString(R.string.st_uploading), getString(R.string.st_plsWait), false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        Toast.makeText(EditProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                        System.out.println("Returned 123" +s);
                        loading.dismiss();
                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        asyncRequestObject.execute(AppConfig.URL_EDIT_PROFILE, uniqueid, gioitinh, ns, em, phone, dc, qq, tt, cn, xl, thanhtuu, nkn, tct, cd, mt, ngoaiNgu, kyNang, t, Tcv, dd, ml, nN, vt, mahs, uid);
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(EditProfileActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = uniqueid;

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
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
                if( params[1]!="1")
                {
                    nameValuePairs.add(new BasicNameValuePair("uniqueid", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("gioitinh", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("birtdate", params[3]));
                    nameValuePairs.add(new BasicNameValuePair("mail", params[4]));
                    nameValuePairs.add(new BasicNameValuePair("sdt", params[5]));
                    nameValuePairs.add(new BasicNameValuePair("diachi", params[6]));
                    nameValuePairs.add(new BasicNameValuePair("quequan", params[7]));
                    nameValuePairs.add(new BasicNameValuePair("tentruong", params[8]));
                    nameValuePairs.add(new BasicNameValuePair("chuyennganh", params[9]));
                    nameValuePairs.add(new BasicNameValuePair("xeploai", params[10]));
                    nameValuePairs.add(new BasicNameValuePair("thanhtuu", params[11]));
                    nameValuePairs.add(new BasicNameValuePair("namkn", params[12]));
                    nameValuePairs.add(new BasicNameValuePair("tencongty", params[13]));
                    nameValuePairs.add(new BasicNameValuePair("chucdanh", params[14]));
                    nameValuePairs.add(new BasicNameValuePair("motacv", params[15]));
                    nameValuePairs.add(new BasicNameValuePair("ngoaiNgu", params[16]));
                    nameValuePairs.add(new BasicNameValuePair("kyNang", params[17]));
                    nameValuePairs.add(new BasicNameValuePair("hoten", params[18]));
                    nameValuePairs.add(new BasicNameValuePair("tencv", params[19]));
                    nameValuePairs.add(new BasicNameValuePair("diadiem", params[20]));
                    nameValuePairs.add(new BasicNameValuePair("mucluong", params[21]));
                    nameValuePairs.add(new BasicNameValuePair("nganhnghe", params[22]));
                    nameValuePairs.add(new BasicNameValuePair("vitri", params[23]));
                    nameValuePairs.add(new BasicNameValuePair("mahs", params[24]));
                    nameValuePairs.add(new BasicNameValuePair("uid", params[25]));
                }else{
                    nameValuePairs.add(new BasicNameValuePair("id", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("mahs", params[3]));
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
               finish();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (jsonResult == 0) {
                return;
            }
            if (jsonResult == 1) {

                try {
                    JSONObject resultObject = new JSONObject(result);
                     int returnedResult = resultObject.getInt("success");
                    if(returnedResult==1){
                     String msg = resultObject.getString("msg");
                     Toast.makeText(EditProfileActivity.this,msg, Toast.LENGTH_SHORT).show();
                     finish();
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

    private int returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
            //   kqq = resultObject.getString("kynang");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            ngaysinh.setText(arg1 + "/" + arg2 + "/" + arg3);
        }
    };
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfileActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                upimage.setImageBitmap(image);
                pDialog.dismiss();

            }else{
                pDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addView() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        upimage = (ImageView) findViewById(R.id.upimage);
        upimage.setImageResource(R.drawable.profile);
        eddd = (Spinner) findViewById(R.id.eddiadiem);
        edtencv = (EditText) findViewById(R.id.edtencv);
        mucluong = (Spinner) findViewById(R.id.spluong);
        nganhNghe = (Spinner) findViewById(R.id.spnganhnghe);
        spvitri = (EditText) findViewById(R.id.edvitri);
        del = (Button) findViewById(R.id.btdel);
        rdgroup = (RadioGroup) findViewById(R.id.radioGroup);
        rdnam = (RadioButton) findViewById(R.id.radioNam);
        rdnu = (RadioButton) findViewById(R.id.radioNu);
        ngaysinh = (Button) findViewById(R.id.btngaysinh);
        update = (Button) findViewById(R.id.updatehs);
        edmail = (EditText) findViewById(R.id.edmail);
        edphone = (EditText) findViewById(R.id.edphone);
        eddiachi = (EditText) findViewById(R.id.eddiachi);
        edquequan = (EditText) findViewById(R.id.edquequan);
        edtentruong = (EditText) findViewById(R.id.edtentruong);
        edchuyennganh = (EditText) findViewById(R.id.edchuyennganh);
        edthanhtuu = (EditText) findViewById(R.id.edthanhtuu);
        edname = (EditText) findViewById(R.id.edname);
        ednamkinhnghiem = (EditText) findViewById(R.id.ednamkinhnghiem);
        edtencongty = (EditText) findViewById(R.id.edtencongty);
        edmotacongviec = (EditText) findViewById(R.id.edmotacv);
        edchucdanh = (EditText) findViewById(R.id.edchucdanh);
        themnn = (ImageButton) findViewById(R.id.themnn);
        themkn = (ImageButton) findViewById(R.id.themkn1);
        n1 = (ImageButton) findViewById(R.id.n1);
        n2 = (ImageButton) findViewById(R.id.n2);
        n3 = (ImageButton) findViewById(R.id.n3);
        k1 = (ImageButton) findViewById(R.id.k1);
        k2 = (ImageButton) findViewById(R.id.k2);
        k3 = (ImageButton) findViewById(R.id.k3);
        nn1 = (TextView) findViewById(R.id.nn1);
        nn2 = (TextView) findViewById(R.id.nn2);
        nn3 = (TextView) findViewById(R.id.nn3);
        kn1 = (TextView) findViewById(R.id.kn1);
        kn2 = (TextView) findViewById(R.id.kn2);
        kn3 = (TextView) findViewById(R.id.kn3);
        ngoaingu1 = (LinearLayout) findViewById(R.id.ngoaingu1);
        ngoaingu2 = (LinearLayout) findViewById(R.id.ngoaingu2);
        ngoaingu3 = (LinearLayout) findViewById(R.id.ngoaingu3);
        kynang1 = (LinearLayout) findViewById(R.id.kynang1);
        kynang2 = (LinearLayout) findViewById(R.id.kynang2);
        kynang3 = (LinearLayout) findViewById(R.id.kynang3);
        spxeploai = (Spinner) findViewById(R.id.spxeploai);
        txtNN = (TextView) findViewById(R.id.txtnn);
        txtKN = (TextView) findViewById(R.id.txtkn);
        adaptennr = ArrayAdapter.createFromResource(EditProfileActivity.this, R.array.nganhNghe, android.R.layout.simple_spinner_item);
        adaptennr.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        nganhNghe.setAdapter(adaptennr);
        adapterluong = ArrayAdapter.createFromResource(EditProfileActivity.this, R.array.mucluong, android.R.layout.simple_spinner_item);
        adapterluong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mucluong.setAdapter(adapterluong);
        adapterdiadiem = ArrayAdapter.createFromResource(EditProfileActivity.this, R.array.diadiem, android.R.layout.simple_spinner_item);
        adapterdiadiem.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        eddd.setAdapter(adapterdiadiem);
        adapter = ArrayAdapter.createFromResource(EditProfileActivity.this, R.array.spXepLoai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spxeploai.setAdapter(adapter);

    }
}
