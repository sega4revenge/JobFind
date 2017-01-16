package com.finger.jobfind.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends Activity {

    private EditText edtk, edmk;
    private String tk = "", mk = "", unid = "", email = "", url="";
    private String loggedUser = "", name = "", key = "";
    private int mtype = 2,  status,st=0;
    private TextView txtDangKy;
    private Button log;
    ProgressDialog progress;
    private TextView tv;
    // Session Manager Class
    SessionManager session;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        AppEventsLogger.activateApp(this);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        loggedUser = user.get(SessionManager.KEY_EMAIL);
        if(loggedUser==null)
        {

        }   else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
            finish();
        }
        Controles();
        Events();
    }



    private void Events() {

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkErrorInput();
            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                try {
                                    Log.i("Response", response.toString());

                                    //   String email = response.getJSONObject().getString("email");
                                    String firstName = response.getJSONObject().getString("first_name");
                                    String lastName = response.getJSONObject().getString("last_name");
                                    String gender = response.getJSONObject().getString("gender");
                                    String name = response.getJSONObject().getString("name");
                                    tk = response.getJSONObject().getString("id");
                                    url="https://graph.facebook.com/" + tk + "/picture?type=large";
                                    st=1;
                                    AsyncDataClass asyncRequestObject = new AsyncDataClass();
                                    asyncRequestObject.execute(AppConfig.URL_LOGINFACE, tk, name,url);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name,gender, birthday, name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void checkErrorInput() {

        edtk.setError(null);
        edmk.setError(null);
        tk = edtk.getText().toString();
        mk = edmk.getText().toString();
        // Kiểm tra email
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(tk)) {
            edtk.setError(getString(R.string.error_field_required));
            focusView = edtk;
            cancel = true;
        } else if (!isValidEmail(tk)) {
            edtk.setError(getString(R.string.error_invalid_email));
            focusView = edtk;
            cancel = true;
        }
        if (TextUtils.isEmpty(mk)) {
            edmk.setError(getString(R.string.error_field_required));
            focusView = edtk;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        if(cancel == false){
            AsyncDataClass asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(AppConfig.URL_LOGIN, tk, mk);

        }

    }

    private void Controles() {
        edtk = (EditText) findViewById(R.id.edtk);
        edmk = (EditText) findViewById(R.id.edmk);
        log = (Button) findViewById(R.id.btlog);
        txtDangKy = (TextView) findViewById(R.id.link_login);
        tv = (TextView) findViewById(R.id.link_fw);
        loginButton = (LoginButton) findViewById(R.id.login_button);

    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
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
                if(st==0) {
                    nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                }else{
                    nameValuePairs.add(new BasicNameValuePair("id", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("name", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("url", params[3]));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

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
                Toast.makeText(LoginActivity.this, R.string.st_errServer, Toast.LENGTH_SHORT).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (jsonResult == 0) {
                Toast.makeText(LoginActivity.this, R.string.st_saiTKMK, Toast.LENGTH_SHORT).show();
                return;
            }
            if (jsonResult == 1 ) {
                session.createLoginSession(key, tk, url, mk, unid);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }}


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
            key = resultObject.getString("company");
            if (key.equals("")) {
                key = resultObject.getString("objectname");
            }
            mtype = resultObject.getInt("mtype");
            status = resultObject.getInt("status");
            if (status == 1) {
                url = resultObject.getString("logo");
            } else {}
            unid = resultObject.getString("uid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

    @Override
    public void onBackPressed() {

        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
