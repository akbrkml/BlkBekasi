package com.barateknologi.bbplk_cevest.Utama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Detail_Berita;
import com.barateknologi.bbplk_cevest.LazyAdapter_berita;
import com.barateknologi.bbplk_cevest.MainActivityBaru;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;

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
public class Berita extends Fragment implements View.OnClickListener {


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/berita.php";

    public static final String TAG_ID = "id_berita";                 //ambil 3 field
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_ISI_BERITA = "isi_berita";
    public static final String TAG_GAMBAR = "gambar";
    public static String LINK_GAMBAR = "";

    //public static final String TAG_KELUAR = "keluar";

    JSONArray string_json = null;

    ListView list;
    LazyAdapter_berita adapter;

    @Nullable
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.activity_kejuruan, container, false);
        //   getActivity().setContentView(R.layout.activity_berita);


        getActivity().setTitle("HEADLINE - BBPLK BEKASI");
        DaftarKelas = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();

        list = (ListView) rootView.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DaftarKelas.get(position);

                // Starting new intent
                Intent in = new Intent(getActivity().getApplicationContext(), Detail_Berita.class); //awalnya DetailLoko

                in.putExtra(TAG_ID, map.get(TAG_ID));
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                in.putExtra(TAG_JUDUL, map.get(TAG_JUDUL));
                in.putExtra(TAG_ISI_BERITA, map.get(TAG_ISI_BERITA));


                startActivity(in);

                //mengirim nama kejuruan
                //  String Slecteditem = list.getItemAtPosition(position).toString();
                Toast.makeText(getActivity().getApplicationContext(), map.get(TAG_ID), Toast.LENGTH_SHORT).show();
                // in.putExtra(TAG_JUDUL, Slecteditem);


            }
        });
        return rootView;

    }


    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_berita(getActivity(),datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("berita"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String id_berita = c.getString(TAG_ID);
                    String judul = c.getString(TAG_JUDUL);
                    String link_image = c.getString(TAG_GAMBAR);
                    String isiberita = c.getString(TAG_ISI_BERITA);
                    Log.d("Gambar", link_image);
                    LINK_GAMBAR = link_image;
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ID, id_berita);
                    map.put(TAG_JUDUL, judul);
                    map.put(TAG_GAMBAR, link_image);
                    map.put(TAG_ISI_BERITA,isiberita);
                   // map.put(TAG_KELUAR,jamkeluar);




                    DaftarKelas.add(map);



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    SetListViewAdapter(DaftarKelas);


                }
            });

        }



    }

    /*
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getActivity().getMenuInflater().inflate(R.menu.main_berita, menu);
            return te;
        }

        /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_home) {
               // Intent i = new Intent(UtamaActivity.this, MainActivityBaru.class);
              //  startActivity(i);


                return true;

            }


            return super.onOptionsItemSelected(item);
        }

    */
    @Override
    public void onClick(View v) {

    }

}
