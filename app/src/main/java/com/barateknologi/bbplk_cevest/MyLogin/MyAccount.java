package com.barateknologi.bbplk_cevest.MyLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.barateknologi.bbplk_cevest.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.RequestHandler;

import org.json.JSONArray;

import static com.barateknologi.bbplk_cevest.utils.SessionManager.getUsername;

public class MyAccount extends AppCompatActivity implements View.OnClickListener {

    private EditText editnama, editEmail, editHp;

    String nama, mygambar, email, noHp, uname;

    private ImageView imageView;
    private ImageView edfoto;

    private ProgressDialog pDialog;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/cari.php";
    private static String url_update = "http://blkbekasi.kemnaker.go.id/androidfinger/update_account.php";
    JSONArray string_json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        edfoto = findViewById(R.id.editfoto);
        edfoto.setOnClickListener(this);

        new AmbilData().execute();
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyAccount.this);
            pDialog.setMessage("Proses Pencarian Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idk",getUsername(MyAccount.this)));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET", params);

            try {
                 string_json = json.getJSONArray("jsoncaridata"); //ini nama json
                 JSONObject c = string_json.getJSONObject(0);
                 nama = c.getString("nama");
                 email=c.getString("email");
                 mygambar = c.getString("gambar");
                 noHp = c.getString("telp");
                 uname = c.getString("username");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                   TextView mynama = findViewById(R.id.textnama);
                   TextView myemail = findViewById(R.id.textemail);
                   TextView nohp = findViewById(R.id.texthp);
                   TextView username = findViewById(R.id.username);

                   mynama.setText(nama);
                   myemail.setText(email);
                   nohp.setText(noHp);
                   username.setText(uname);
                   Glide.with(MyAccount.this).load(mygambar)
				.thumbnail(0.5f)
				.override(200, 200)
				.crossFade()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(imageView);
                   }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if (v == edfoto) {
            Intent i = new Intent(MyAccount.this, EditFotoProfil.class);
            startActivity(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_account, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    if (id == R.id.action_save) {
            updateData();
            return true;
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateData() {
        editnama = (EditText) findViewById(R.id.textnama);
        final String nama = editnama.getText().toString().trim();

        class updatedata extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyAccount.this, "", "Simpan Data", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MyAccount.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();

                param.put("username",getUsername(MyAccount.this));
                param.put("nama", nama);
                String result = rh.sendPostRequest(url_update, param);

                return result;
            }

        }
        updatedata u = new updatedata();
        u.execute();
    }

}