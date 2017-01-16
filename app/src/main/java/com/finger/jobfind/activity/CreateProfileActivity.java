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
import android.widget.NumberPicker;
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by VinhNguyen on 7/6/2016.
 */
public class CreateProfileActivity extends AppCompatActivity {
    String[] arr={"Tất cả ngành nghề","Dược sỹ","Trình dược viên","Bảo trì/Sửa chửa","Bán hàng","Bảo hiểm","Bất động sản","Biên dịch viên","Chứng khoáng","Công nghệ cao","IT Phần cứng/Mạng","Internet/Online Media","IT -Phần mềm","Cơ khí,chế tạo","Dệt may/Da giày","Dịch vụ khách hàng","Hàng không/Du lịch","Điện/Điện tử","Giáo dục/Đào tạo","Kế toán","Kiểm toán","Y tế/Chăm sóc sức khỏe","Kiến trúc/Thiết kế nội thất","Ngân hàng","Mỹ thuật/Nghệ thuật/Thiết kế","Nhân sự","Nông nghiệp/Lâm nghiệp","Pháp lý","Phi chính phủ/Phi lợi nhuận","Cấp quản lý điều hành","Quản cáo/Khuyến mại/Đối thoại","Sản Xuất","Thời vụ/Hợp đồng ngắn hạn","Tài chính/Đầu tư","Thời trang","Thực phẩm đồ uống","Truyền hình/Truyền thông/Báo chí","Marketing","Tư vấn","Vận chuyển/Giao nhận","Thu mua/Vật tư/Cung vận","Viễn thông","Xây dựng","Xuất nhập khẩu","Tự động hóa/Ôtô","Overseass Jop","Khác"};
    private RadioGroup rdgroup;
    private RadioButton rdnam, rdnu;
    private Button ngaysinh, create, rep;
    private EditText spvitri, edname, edmail, edphone, eddiachi, edquequan, edtentruong, edchuyennganh, edthanhtuu, ednamkinhnghiem, edtencongty, edchucdanh, edmotacongviec, edtencv, edluong;
    private Spinner spxeploai, mucluong, nganhNghe, eddd;
    private LinearLayout lin, ngoaingu1, ngoaingu2, ngoaingu3, lin2, kynang1, kynang2, kynang3;
    private TextView nn1, nn2, nn3, kn1, kn2, kn3, txtNN, txtKN;
    private ImageView logo;
    private ImageButton n1, n2, n3, k1, k2, k3, themnn, themkn;
    private int key = 0, key2 = 0, key3 = 0, key4 = 0, key5 = 0, key6 = 0;
    private String KEY_IMAGE = "image", KEY_NAME = "name" , ngoaiNgu = "", kyNang = "", gioitinh = "", uniqueid = "", mail = "", sdt = "", diachi = "", quequan = "", tentruong = "", chuyennganh = "", thanhtuu = "", tencongty = "", chucdanh = "", motacv = "", tencv = "", dd = "", vt = "", ten1 = "", birtdate = "", ten = "", gt = "", ns = "", em = "", p = "", dc = "", qq = "", logo1 = "",yearskill="0";
    private Calendar calendar;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private int year, month, day;
    private ArrayAdapter adapter, adaptennr, adapterdiadiem, adapterluong;
    private int statuss = 0, stt = 0;
    private int ss = 0,bd=0,nn,ml,xeploai1;
    static String image="";
    private ProgressDialog pDialog;
    // Session Manager Class
    private SessionManager session;
    private String emailpref, namepref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_createprofile);

        session = new SessionManager(getApplicationContext());


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        em = user.get(SessionManager.KEY_EMAIL);
        ten= user.get(SessionManager.KEY_NAME);


        getData();
        addView();
        setData();


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
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        ednamkinhnghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.dialog_number_skill, null);
                builder.setTitle(R.string.st_skill);
                builder.setView(view1);
                final NumberPicker np = (NumberPicker) view1.findViewById(R.id.numberpicker);
                np.setMaxValue(30);
                np.setMinValue(0);
                builder.setPositiveButton(R.string.st_xacNhanOK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yearskill = np.getValue() + "";
                        ednamkinhnghiem.setText(yearskill);
                    }
                });
                builder.create().show();
            }
        });

        themkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtKN.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view1 = inflater.inflate(R.layout.dialog_createskill, null);
                builder.setTitle(R.string.st_kinang);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfileActivity.this);
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
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ss == 0) {
                    mail = edmail.getText().toString();
                    sdt = edphone.getText().toString();
                    diachi = eddiachi.getText().toString();
                    quequan = edquequan.getText().toString();
                    tentruong = edtentruong.getText().toString();
                    chuyennganh = edchuyennganh.getText().toString();
                    thanhtuu = edthanhtuu.getText().toString();
            //        namkn = ednamkinhnghiem.getText().toString() + " Năm";
                    tencongty = edtencongty.getText().toString();
                    chucdanh = edchucdanh.getText().toString();
                    motacv = edmotacongviec.getText().toString();
                    tencv = edtencv.getText().toString();
                  //  nn = String.valueOf(nganhNghe.getSelectedItem());
                     nn = nganhNghe.getSelectedItemPosition();
                   // ml = String.valueOf(mucluong.getSelectedItem());
                     ml = mucluong.getSelectedItemPosition();
                    dd = String.valueOf(eddd.getSelectedItem());
                    vt = spvitri.getText().toString();
                    ten1 = edname.getText().toString();
                    birtdate = ngaysinh.getText().toString();
                  //  spxeploai.setSelection(2);
                   // xeploai1 = String.valueOf(spxeploai.getSelectedItem());
                     xeploai1 = spxeploai.getSelectedItemPosition();
                    if (ten1 == "" || mail == "" || sdt == "" || diachi == "" || quequan == "" || tentruong.equals("") || chuyennganh.equals("")  || bd==0 || vt == "" || dd == "" || tencv.equals("")) {
                        Toast.makeText(CreateProfileActivity.this, R.string.st_err_taocv, Toast.LENGTH_SHORT).show();
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

                        if (stt == 2) {
                            if (statuss == 1) {
                                uploadImage();
                            } else {
                                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                                asyncRequestObject.execute(AppConfig.URL_TAOHOSO, uniqueid, gioitinh, birtdate, mail, sdt, diachi, quequan, tentruong, chuyennganh, xeploai1+"", thanhtuu, yearskill, tencongty, chucdanh, motacv, ngoaiNgu, kyNang, ten1, tencv, dd, ml+"", nn+"", vt);
                            }
                            ss = 1;
                        } else {
                            if (statuss == 0) {
                                Toast.makeText(CreateProfileActivity.this, R.string.st_err_anh, Toast.LENGTH_SHORT).show();
                            } else {
                                uploadImage();
                                ss = 1;
                            }
                        }

                    }
                } else {
                    Toast.makeText(CreateProfileActivity.this, R.string.st_errHoso, Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void getData() {
        Intent s = getIntent();
        uniqueid = s.getStringExtra("uniqueid");
        final int status = s.getIntExtra("status", 1);
        if (status == 1) {
            ten = s.getStringExtra("name");
            ns = s.getStringExtra("birthdate");
            gt = s.getStringExtra("sex");
            em = s.getStringExtra("email");
            p = s.getStringExtra("phone");
            dc = s.getStringExtra("andress");
            qq = s.getStringExtra("homeless");
            logo1 = s.getStringExtra("logo");
            if(logo1.equals(""))
            {
            }else{
               new LoadImage().execute(logo1);
                stt = 2;
            }
        }else{

        }
    }

    private void setData() {

        if(statuss!=0)
        {
            edname.setText(ten+"");
            edmail.setText(em+"");

        }else{
            edname.setText(ten+"");
            edphone.setText(p + "");
            edquequan.setText(qq + "");
            edmail.setText(em+"");
            eddiachi.setText(dc + "");
            if(ns.equals("")) {

            }else{
                ngaysinh.setText(ns + "");
                bd=1;
            }
            if (gt=="1") {
                rdnam.setChecked(true);
                gioitinh = "1";
            } else {
                rdnu.setChecked(true);
                gioitinh = "2";
            }
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
                logo.setImageBitmap(bitmap);

//                Glide.with(this).load(bitmap)
//                        .crossFade()
//                        .thumbnail(0.5f)
//                        .bitmapTransform(new CircleTransform(this))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(logo);
                statuss = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
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
                        loading.dismiss();
                        image=s;
                        //Showing toast message of the response
                       // Toast.makeText(CreateProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                        AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        asyncRequestObject.execute(AppConfig.URL_TAOHOSO, uniqueid, gioitinh, birtdate, mail, sdt, diachi, quequan, tentruong, chuyennganh, xeploai1+"", thanhtuu, yearskill, tencongty, chucdanh, motacv, ngoaiNgu, kyNang, ten1, tencv, dd, ml+"", nn+"", vt);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(CreateProfileActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CreateProfileActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (jsonResult == 0) {
                Toast.makeText(CreateProfileActivity.this, R.string.st_errNamePass, Toast.LENGTH_SHORT).show();
                return;
            }
            if (jsonResult == 1) {
                Toast.makeText(CreateProfileActivity.this, R.string.st_create_success, Toast.LENGTH_SHORT).show();
                //finish();
              /*  fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                List_Profile main = new List_Profile();
                fragmentTransaction.replace(R.id.container, main);
                fragmentTransaction.commit(); */
        /*    Intent i = new Intent(CreateProfileActivity.this, SlingdingMenuActivity.class);
                i.putExtra("update", 1);
                i.putExtra("USERNAME", em);
                i.putExtra("name", ten);
                i.putExtra("logo", logo1);
                i.putExtra("uid", uniqueid);
                startActivity(i);*/
                finish();
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
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateProfileActivity.this);
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
                logo.setImageBitmap(image);
                pDialog.dismiss();

            }else{
                pDialog.dismiss();
                Toast.makeText(CreateProfileActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
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
            int thang = arg2+1;
            ngaysinh.setText(arg1 + "/" + thang + "/" + arg3);
            bd=1;
        }
    };

    private void addView() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        logo = (ImageView) findViewById(R.id.upimage);
        logo.setImageResource(R.drawable.profile);
        eddd = (Spinner) findViewById(R.id.eddiadiem);
        edtencv = (EditText) findViewById(R.id.edtencv);
        mucluong = (Spinner) findViewById(R.id.spluong);
        nganhNghe = (Spinner) findViewById(R.id.spnganhnghe);
        spvitri = (EditText) findViewById(R.id.edvitri);
        //  rep = (Button) findViewById(R.id.rep);
        rdgroup = (RadioGroup) findViewById(R.id.radioGroup);
        rdnam = (RadioButton) findViewById(R.id.radioNam);
        rdnu = (RadioButton) findViewById(R.id.radioNu);
        ngaysinh = (Button) findViewById(R.id.btngaysinh);
        create = (Button) findViewById(R.id.createhs);
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
        TextView txtnn = (TextView) findViewById(R.id.txtnn);
        TextView txtkn = (TextView) findViewById(R.id.txtkn);
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
        adaptennr = ArrayAdapter.createFromResource(CreateProfileActivity.this, R.array.nganhNghe, android.R.layout.simple_spinner_item);
        adaptennr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nganhNghe.setAdapter(adaptennr);
        adapterluong = ArrayAdapter.createFromResource(CreateProfileActivity.this, R.array.mucluong, android.R.layout.simple_spinner_item);
        adapterluong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mucluong.setAdapter(adapterluong);
        adapterdiadiem = ArrayAdapter.createFromResource(CreateProfileActivity.this, R.array.diadiem, android.R.layout.simple_spinner_item);
        adapterdiadiem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eddd.setAdapter(adapterdiadiem);
        adapter = ArrayAdapter.createFromResource(CreateProfileActivity.this, R.array.spXepLoai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spxeploai.setAdapter(adapter);

    }

}
