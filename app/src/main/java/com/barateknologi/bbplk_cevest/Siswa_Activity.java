package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Master.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PCIT-001 on 10/10/2016.
 */
public class Siswa_Activity extends AppCompatActivity {


    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/view_pendaftar.php";


   // public static final String TAG_ID = "id_siswa";                 //ambil 3 field
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_DESKRIPSI = "namaanggota";
    public static final String TAG_KETERANGAN = "alamat";
    //public static final String TAG_KELUAR = "keluar";




    JSONArray string_json = null;

    ListView list;
    LazyAdapter_kelas adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjur);


      //  setTitle("ABSENSI BERDASARKAN BIDANG");
        DaftarKelas = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();

        list = (ListView) findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DaftarKelas.get(position);

                Intent in = new Intent(getApplicationContext(),SubKejuruan_Activity.class);
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                in.putExtra(TAG_KELAS, map.get(TAG_KELAS));
                in.putExtra(TAG_DESKRIPSI, map.get(TAG_DESKRIPSI));
                in.putExtra(TAG_KETERANGAN, map.get(TAG_KETERANGAN));


                startActivity(in);

                //mengirim nama kejuruan
                 String Slecteditem = list.getItemAtPosition(position).toString();
                 Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                 in.putExtra(TAG_KELAS, Slecteditem);
                 in.putExtra(TAG_KETERANGAN,Slecteditem);




            }
        });




    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_kelas(this, datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Siswa_Activity.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonkelas"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);


                    String kelas = c.getString(TAG_KELAS);
                    String link_image = c.getString(TAG_GAMBAR);
                    String Keterangan = c.getString(TAG_DESKRIPSI);
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_KELAS, kelas);
                    map.put(TAG_GAMBAR, link_image);
                    map.put(TAG_DESKRIPSI,Keterangan);

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
