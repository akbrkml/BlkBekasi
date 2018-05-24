package com.barateknologi.bbplk_cevest.perusahaan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.MainActivityBaru;
import com.barateknologi.bbplk_cevest.MyLogin.RegisterBaru;
import com.barateknologi.bbplk_cevest.R;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//import com.barateknologi.barate.R;

public class PerusahaanActivity extends AppCompatActivity {

   private EditText editTextUserName;
   private EditText editTextPassword;
   public static final String USER_NAME = "USERNAME";
    String uname;String psd;

    private String fup = "un";
    // private String fpass="pass";

    String tempunper= "";
    //String temp1="";
@Override
protected void onCreate(Bundle savedInstanceState)
{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_perusahaan);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);




        }

    public void onSignUpClick(View view) {
        Intent i = new Intent(PerusahaanActivity.this, RegisterPerusahaan.class);
        startActivity(i);
        finish();

    }



      public void invokeLogin(View view) {
          uname = editTextUserName.getText().toString();
          psd = editTextPassword.getText().toString();
          login(uname, psd);
          //simpan ke internal storage

      }

    private void login(final String username, String password) {
       class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(PerusahaanActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
            String psd = params[1];
            String uname = params[0];

            InputStream is = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("uname",uname));
            nameValuePairs.add(new BasicNameValuePair("psd", psd));
            String result = null;
                    HttpClient httpClient = new DefaultHttpClient();

                try{
                    HttpPost httpPost = new HttpPost(
                    "http://barateknologi.org/androidfinger/login_perusahaan.php");
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
           //Intent intent = new Intent(PerusahaanActivity.this, com.barateknologi.bbplk_cevest.MainActivity.class);
           //intent.putExtra(USER_NAME, username);
                String name = editTextUserName.getText().toString();

                try {

                    //menyimpan username
                    FileOutputStream fOut = openFileOutput(fup,MODE_WORLD_READABLE);
                    fOut.write(name.getBytes());
                    fOut.close();

                    Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                }


                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



          //  Toast.makeText(getApplicationContext(),  uname, Toast.LENGTH_LONG).show();


                finish();
                Intent intent = new Intent(PerusahaanActivity.this, MenuActivity.class);
                intent.putExtra(USER_NAME, uname);
                startActivity(intent);



               // Intent intent = new Intent(SplashScreen.this, MainActivityBaru.class);


            }else {
            Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
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




}