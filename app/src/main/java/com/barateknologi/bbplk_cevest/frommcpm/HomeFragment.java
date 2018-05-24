package com.barateknologi.bbplk_cevest.frommcpm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.*;
import com.barateknologi.bbplk_cevest.Kejuruan_Activity;
import com.barateknologi.bbplk_cevest.LazyAdapter_kejuruan;
import com.barateknologi.bbplk_cevest.Master.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class HomeFragment extends Fragment {

    private TextView textjudul;



    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/view_pendaftar.php";


                  //ambil 3 field
    public static final String TAG_IDREG = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_NAMAPROG = "namaprogram";
    public static final String TAG_GAMBAR = "gambar";





    JSONArray string_json = null;

    ListView list;
    LazyAdapter_kelas adapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.home_layout, container, false);
       // Button btn = (Button) rootView.findViewById(R.id.btn);



        textjudul = (TextView) rootView.findViewById(R.id.judul);
       // textjudul.setText("Selamat Datang");


        DaftarKelas = new ArrayList<HashMap<String, String>>();

        new AmbilData().execute();
        list = (ListView) rootView.findViewById(R.id.list);





        return rootView;
    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_kelas(getActivity(), datakejuruan);
        list.setAdapter(adapter);
    }

    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  pDialog = new ProgressDialog.show(getActivity().ProgramPelatihan.this);
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

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    SetListViewAdapter(DaftarKelas);


                }
            });

        }



    }



}
