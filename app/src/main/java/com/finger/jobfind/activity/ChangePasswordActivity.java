package com.finger.jobfind.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONTHO on 15/08/2016.
 */
public class ChangePasswordActivity extends AppCompatActivity {

    private String uid;
    private EditText old, newpass, renewpass;
    private Button btlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        init();
        uid = MainActivity.uid;
        events();
      //  Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
    }

    private void events() {
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = old.getText().toString();
                String np= newpass.getText().toString();
                String rnp = renewpass.getText().toString();


                if(op == "" || np == "" || rnp =="")
                {
                    Toast.makeText(ChangePasswordActivity.this,R.string.st_err_taocv, Toast.LENGTH_SHORT).show();
                }else if(np.equals(rnp)){
                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                    asyncRequestObject.execute(AppConfig.URL_CHANGEPASSWORD2,uid, op, np);

                }else {
                    Toast.makeText(ChangePasswordActivity.this, R.string.st_changeError, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void init() {
        old = (EditText) findViewById(R.id.oldpass);
        newpass =(EditText) findViewById(R.id.newpass);
        renewpass =(EditText) findViewById(R.id.renewpass);
        btlog = (Button) findViewById(R.id.btlog);
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
                nameValuePairs.add(new BasicNameValuePair("old", params[2]));
                nameValuePairs.add(new BasicNameValuePair("new", params[3]));
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
            if (result.equals("1")){
                Toast.makeText(ChangePasswordActivity.this, R.string.st_changeSuccess, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                
            } else if (!result.equals("1")){
                Toast.makeText(ChangePasswordActivity.this, R.string.st_changeOldError, Toast.LENGTH_SHORT).show();
            }
            if (result.equals("") || result == null) {
                Toast.makeText(ChangePasswordActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
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
    public void onBackPressed() {
//       Intent s= getIntent();
//        String logo = s.getStringExtra("logo");
//        Intent a= new Intent(getApplicationContext(), SettingActivity.class);
//        a.putExtra("logo",logo);
//        startActivity(a);
        finish();
    }
}
