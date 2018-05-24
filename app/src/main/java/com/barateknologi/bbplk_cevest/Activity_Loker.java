package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class Activity_Loker extends AppCompatActivity {


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarLowongan = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/view_loker.php";


    public static final String TAG_ID_LOKER = "id_loker";                 //ambil 3 field
    public static final String TAG_JUDUL = "lowongan";
    public static final String TAG_POSISI = "posisi";
    public static final String TAG_NAMAPER = "namaperusahaan";
    public static final String TAG_GAMBAR = "gambar";

    //public static final String TAG_KELUAR = "keluar";

    JSONArray string_json = null;

    ListView list;
    LazyAdapter_loker adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loker);


        setTitle("BBPLK BEKASI-LOWONGAN KERJA");
        DaftarLowongan = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DaftarLowongan.get(position);

                // Starting new intent
                Intent in = new Intent(getApplicationContext(), Detail_Loker.class); //awalnya DetailLoko

                in.putExtra(TAG_ID_LOKER, map.get(TAG_ID_LOKER));
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                in.putExtra(TAG_POSISI, map.get(TAG_POSISI));
                startActivity(in);

          //      Toast.makeText(getApplicationContext(), map.get(TAG_ID_LOKER), Toast.LENGTH_SHORT).show();


            }
        });



    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_loker(this, datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Loker.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonviewloker"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String id_loker = c.getString(TAG_ID_LOKER);
                    String judul = c.getString(TAG_JUDUL);
                    String posisi = c.getString(TAG_POSISI);
                    String namaperusahaan = c.getString(TAG_NAMAPER);
                    String link_image = c.getString(TAG_GAMBAR);
              //      ;

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ID_LOKER, id_loker);
                    map.put(TAG_POSISI, posisi);
                    map.put(TAG_NAMAPER,namaperusahaan);
                    map.put(TAG_GAMBAR, link_image);


                    DaftarLowongan.add(map);



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

                    SetListViewAdapter(DaftarLowongan);




                }
            });

        }



    }

    //panggil menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_berita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.kirim_berita) {
            return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
