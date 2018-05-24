package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.barateknologi.bbplk_cevest.Master.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PCIT-001 on 10/10/2016.
 */
public class Detail_Siswa_Activity extends AppCompatActivity {
    String idk;

    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    private static String url_kelas = "http://barateknologi.org/androidfinger/Data_Siswa_Baru.php";


    public static final String TAG_ID = "id_siswa";
    public static final String TAG_KELAS = "kelas";  //ambil 3 field
    public static final String TAG_NAMA = "nama";
    public static final String TAG_GAMBAR = "gambar";

  //  public static final String TAG_KELAS = "kelas";
      // public static final String TAG_DESKRIPSI = "Keterangan";
    //public static final String TAG_KELUAR = "keluar";




    JSONArray string_json = null;

    ListView list;
    LazyAdapter_subjur adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjur);


        setTitle("DATA SISWA");
        DaftarKelas = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();

        list = (ListView) findViewById(R.id.list);

        Intent i = getIntent();
        idk= i.getStringExtra(TAG_KELAS);


    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datasiswa) {
        adapter = new LazyAdapter_subjur(this, datasiswa);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Detail_Siswa_Activity.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idk",idk));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonsiswa"); //ini nama json
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String id = c.getString(TAG_ID);
                    String nama = c.getString(TAG_NAMA);
                    String link_image = c.getString(TAG_GAMBAR);
                    String kelas = c.getString(TAG_KELAS);
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ID, id);
                    map.put(TAG_NAMA, nama);
                    map.put(TAG_GAMBAR, link_image);
                    map.put(TAG_KELAS, kelas);

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
