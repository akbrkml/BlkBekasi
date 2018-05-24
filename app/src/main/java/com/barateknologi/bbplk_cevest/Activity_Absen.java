package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.barateknologi.bbplk_cevest.Master.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Activity_Absen extends AppCompatActivity {


    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/absensi.php";


   // public static final String TAG_ID = "id_siswa";                 //ambil 3 field
    public static final String TAG_ENROLL = "enroll_number";
    public static final String TAG_DATE = "date_time";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_GAMBAR = "gambar";
    //public static final String TAG_KELUAR = "keluar";




    JSONArray string_json = null;

    ListView list;
    LazyAdapter_absensi adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);


        setTitle("KONTROL ABSEN");
        DaftarKelas = new ArrayList<HashMap<String, String>>();
        new AmbilData().execute();
        list = (ListView) findViewById(R.id.list);
    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_absensi(this, datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Absen.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);
            try {
                string_json = json.getJSONArray("jsonabsen"); //ini nama json
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String enroll = c.getString(TAG_ENROLL);
                    String Keterangan = c.getString(TAG_DATE);
                    String nama=c.getString(TAG_NAMA);
                    String link_image = c.getString(TAG_GAMBAR);

                  //  String link_image = c.getString(TAG_DATE);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ENROLL,enroll);
                    map.put(TAG_DATE,Keterangan);
                    map.put(TAG_NAMA, nama);
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


    public void refreshabsen(View view) {
        Intent i = new Intent(Activity_Absen.this, Activity_Absen.class);
        startActivity(i);

    }

    public void kliksearch(View view) {
        Intent i = new Intent(Activity_Absen.this,Search_Absen.class);
        startActivity(i);

    }
}
