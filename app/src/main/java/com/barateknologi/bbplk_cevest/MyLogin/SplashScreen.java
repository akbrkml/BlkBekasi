package com.barateknologi.bbplk_cevest.MyLogin;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.utils.SessionManager;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * Created by PCIT-001 on 30/08/2016.
 */
public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    //untuk search data tersimpan
    TextView tv;
    TextView tv2;
    private String file = "mydata";
    private String file1="mypass";

    String temp= "";
    String temp1="";

    //ngeri untuk cek user--------------------------

    private EditText editTextUserName;
    private EditText editTextPassword;
    public static final String USER_NAME = "USERNAME";
    String username;
    String password;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("KEAHLIAN UNTUK MASA DEPAN");

        initComponents();

        Nammu.init(this);
        initPermission();

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                try {
                    FileInputStream fin = openFileInput(file);
                    int c;

                    FileInputStream fin1 = openFileInput(file1);
                    int c1;

                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);

                    }

                    while ((c1 = fin1.read()) != -1) {
                        temp1 = temp1 + Character.toString((char) c1);

                    }

                    if (temp.equals(""))
                    {
                        Intent i = new Intent(SplashScreen.this, RegisterBaru.class);
                        startActivity(i);
                        finish();
                    }else{
                        username = temp;
                        password = temp1;

                        login(username, password);
                    }

                } catch (Exception e) {
                }
            }


        }, SPLASH_TIME_OUT);
    }

    private void initComponents(){
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startApp();
                finish();
            }
        }).start();
    }

    private void startApp(){
        if(isSessionLogin()) {
            startActivity(new Intent(SplashScreen.this, MainActivityBaru.class));
            finish();
        } else {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }
    }

    private void login(final String username, String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SplashScreen.this, "Silahkan Tunggu Sedang Sinkronisasi Data", "Loading...");
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

                try{
                    HttpPost httpPost = new HttpPost(
                            "http://blkbekasi.kemnaker.go.id/androidfinger/cekuser.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
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

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(SplashScreen.this, MainActivityBaru.class);
                 //   intent.putExtra(USER_NAME, temp);
                 //   Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
                 //   Toast.makeText(getApplicationContext(),"Keahlian untuk Masa Depan", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);

                }else {
                   // Intent i = new Intent(SplashScreen.this, RegisterBaru.class);
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                  //  Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }

    public void registerr(View view) {
        Intent i = new Intent(SplashScreen.this, RegisterBaru.class);
        startActivity(i);
    }

    private void initPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.READ_CONTACTS, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                @Override
                public void permissionRefused() {
                    finish();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    boolean isSessionLogin() {
        return SessionManager.getString(this, USER_NAME) != null;
    }

}
