package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.frommcpm.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nur on 22/12/2016.
 */
public class Activity_Chat_Group extends AppCompatActivity {
    String id_reg;
    TextView isipesan;
    private TextView textjudul;
    private Button savebtn;

    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> DaftarId = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/view_chat.php";
    public static final String SAVE_URL = "http://barateknologi.org/androidfinger/input_pesan.php";
    public static final String KEY_TEXT_ID = "id";  //untuk data registere
    public static final String KEY_TEXT_MESSAGE = "message";

    //ambil 3 field
    public static final String TAG_IDREG = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_GAMBAR = "gambar";


    JSONArray string_json = null;
    JSONArray string_json1 = null;
    ListView list;
    LazyAdapter_chat adapter;


    String namapengguna;
    private static String url_cari = "http://barateknologi.org/androidfinger/cari.php";
    String temp = "";
    private String file = "mydata";
    private String file1 = "mypass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

          textjudul = (TextView) findViewById(R.id.judul);

          DaftarKelas = new ArrayList<HashMap<String, String>>();
          new AmbilData().execute();
          list = (ListView) findViewById(R.id.list);


          isipesan=(TextView)findViewById(R.id.textpesan);


    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_chat(this,datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Chat_Group.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonviewdaftar"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String idpr = c.getString(TAG_IDREG);
                    String nama = c.getString(TAG_NAMA);
                    String message = c.getString(TAG_MESSAGE);
                    String link_image = c.getString(TAG_GAMBAR);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_IDREG, idpr);
                    map.put(TAG_NAMA,nama);
                    map.put(TAG_MESSAGE,message);
                    map.put(TAG_GAMBAR, link_image);


                    DaftarKelas.add(map);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                    SetListViewAdapter(DaftarKelas);




                }
            });

        }


    }





    public void savedata() {
        final String textisi = isipesan.getText().toString().trim();
        class UploadImage extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Activity_Chat_Group.this,"Please wait...","uploading",false,false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s==""){
                    Toast.makeText(Activity_Chat_Group.this, "Data Belum Masuk...........", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Activity_Chat_Group.this, s, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                com.barateknologi.bbplk_cevest.frommcpm.RequestHandler rh = new com.barateknologi.bbplk_cevest.frommcpm.RequestHandler();
                HashMap<String,String> param = new HashMap<String,String>();
                param.put(KEY_TEXT_ID,namapengguna);  //CARI ID REGISTER
                param.put(KEY_TEXT_MESSAGE,textisi);


                String result = rh.sendPostRequest(SAVE_URL, param);
                return result;
            }
        }

        UploadImage u = new UploadImage();
        u.execute();
    }






    class AmbilDataPengguna extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Chat_Group.this);
            pDialog.setMessage("Proses Pencarian Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("idk",temp));
            JSONObject json1 = jParser.makeHttpRequest(url_cari, "GET", params1);

            try {


                string_json1 = json1.getJSONArray("jsoncaridata"); //ini nama json
                JSONObject c = string_json1.getJSONObject(0);
                namapengguna = c.getString("id");
                HashMap<String, String> map = new HashMap<String, String>();
                DaftarId.add(map);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

     /*   protected void onPostExecute(String file_url_) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                  //   Toast.makeText(getBaseContext(), namapengguna, Toast.LENGTH_SHORT).show();
                   // txtnama = (TextView) findViewById(R.id.textnama);
                   // txtemail = (TextView) findViewById(R.id.textemail);
                    //   txtgambar = (TextView) findViewById(R.id.textgbr);

                   // txtnama.setText(namapengguna);
                   // txtemail.setText(emailpengguna);
                    //   txtgambar.setText(mygambar);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                   // new DownLoadImageTask(imageView).execute(mygambar);


                }
            });

        }
        */
    }











    public void kliksendchat(View view) {

        try {
            FileInputStream fin = openFileInput(file);
            int c;


            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }

        } catch (Exception e) {
        }

       // Toast.makeText(getApplicationContext(),temp, Toast.LENGTH_SHORT).show();

        new AmbilDataPengguna().execute();

      //  Toast.makeText(getApplicationContext(),namapengguna, Toast.LENGTH_SHORT).show();

        savedata();
        isipesan.clearFocus();
        Intent i = new Intent(Activity_Chat_Group.this, Activity_Chat_Group.class);
        startActivity(i);
        finish();
    }







}
