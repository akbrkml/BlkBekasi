package com.barateknologi.bbplk_cevest.frommcpm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barateknologi.bbplk_cevest.LazyAdapterTentangSaya;

import com.barateknologi.bbplk_cevest.LazyAdapter_kelas;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.MyLogin.RegisterBaru;
import com.barateknologi.bbplk_cevest.R;

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
 * Created by Ratan on 7/29/2015.
 */
public class TTGsaya extends AppCompatActivity {

    private TextView textjudul;

    String username="";
    String temp= "";

    private String file = "mydata";


    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/view_pendaftar_ttg_saya.php";


                  //ambil 3 field
    public static final String TAG_IDREG = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_NAMAPROG = "namaprogram";
    public static final String TAG_GAMBAR = "gambar";





    JSONArray string_json = null;

    ListView list;
    @Nullable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


        textjudul = (TextView) findViewById(R.id.judul);
       // textjudul.setText("Selamat Datang");


        DaftarKelas = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();
        list = (ListView) findViewById(R.id.list);


        //ambil username

        try {
            FileInputStream fin = openFileInput(file);
            int c;


            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }





        } catch (Exception e) {
        }

      //  username=temp;

    }




    LazyAdapterTentangSaya adapter;

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new   LazyAdapterTentangSaya(this, datakejuruan);
        list.setAdapter(adapter);
    }

    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TTGsaya.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",temp));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonviewdaftar"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String idpr = c.getString(TAG_IDREG);
                    String nama = c.getString(TAG_NAMA);
                    String namaprogram = c.getString(TAG_NAMAPROG);
                    String link_image = c.getString(TAG_GAMBAR);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_IDREG, idpr);
                    map.put(TAG_NAMA,nama);
                    map.put(TAG_NAMAPROG,namaprogram);
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



}
