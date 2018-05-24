package com.barateknologi.bbplk_cevest.MyLogin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.barateknologi.barate.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.barateknologi.bbplk_cevest.MainActivityBaru;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    public static final String USER_NAME = "USERNAME";
    String username;
    String password;

    private String file = "mydata";
    private String file1 = "mypass";

    String temp = "";
    String idpengguna;

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/cari.php";
    JSONArray string_json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);

        if(isSessionLogin()) {
            startActivity(new Intent(MainActivity.this, MainActivityBaru.class));
            finish();
        }

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        try {
            FileInputStream fin = openFileInput(file);
            int c;

            FileInputStream fin1 = openFileInput(file1);
            int c1;

            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }

        } catch (Exception e) {
        }


    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Proses Pencarian Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idk", temp));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET", params);

            try {
                string_json = json.getJSONArray("jsoncaridata"); //ini nama json
                JSONObject c = string_json.getJSONObject(0);
                idpengguna = c.getString("id");
                HashMap<String, String> map = new HashMap<String, String>();
                DaftarKelas.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {


                    //    Toast.makeText(MainActivity.this,idpengguna,Toast.LENGTH_LONG).show();


                }
            });

        }
    }


    public void onSignUpClick(View view) {
        Intent i = new Intent(MainActivity.this, RegisterBaru.class);
        startActivity(i);

    }


    public void invokeLogin(View view) {
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        login(username, password);
        //simpan ke internal storage

        //simoan ke stream
        try {

            //menyimpan username
            FileOutputStream fOut = openFileOutput(file, MODE_WORLD_READABLE);
            fOut.write(username.getBytes());
            fOut.close();

            //menyimpan password
            FileOutputStream fOut1 = openFileOutput(file1, MODE_WORLD_READABLE);
            fOut1.write(password.getBytes());
            fOut1.close();

            Toast.makeText(getBaseContext(), "keahlian untuk masa depan", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private void login(final String username, String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String pass = params[1];
                String uname = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;
                HttpClient httpClient = new DefaultHttpClient();

                try {
                    HttpPost httpPost = new HttpPost(
                            "http://blkbekasi.kemnaker.go.id/androidfinger/cekuser.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            //input stream


            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if (s.equalsIgnoreCase("success")) {
                    SessionManager.save(USER_NAME, username);
                    Intent intent = new Intent(MainActivity.this, com.barateknologi.bbplk_cevest.MainActivityBaru.class);
                    intent.putExtra(USER_NAME, username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "login anda tidak diterima....!", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    boolean isSessionLogin() {
        return SessionManager.getString(this, USER_NAME) != null;
    }


}