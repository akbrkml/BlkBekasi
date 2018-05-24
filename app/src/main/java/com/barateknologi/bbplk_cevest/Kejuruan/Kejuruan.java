package com.barateknologi.bbplk_cevest.Kejuruan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Activity_Absen;
import com.barateknologi.bbplk_cevest.Kejuruan_Activity;
import com.barateknologi.bbplk_cevest.LazyAdapter_kejuruan;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.SubKejuruan_Activity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class Kejuruan extends Fragment implements View.OnClickListener {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKejuruan = new ArrayList<HashMap<String, String>>();
    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/kejuruan.php";

    public static final String TAG_ID = "idkejuruan";                 //ambil 3 field
    public static final String TAG_KDKEJURUAN = "KdKejuruan";
    public static final String TAG_NAMAKERUAN = "NamaKejuruan";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_DESK = "deskripsi";




    JSONArray string_json = null;

    ListView list;
    LazyAdapter_kejuruan adapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.primary_layout,null);
      //  final RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.home_layout, container, false);
        LinearLayout rootView = (LinearLayout ) inflater.inflate(R.layout.activity_kejuruan, container, false);
        getActivity().setTitle("PROFIL-KEJURUAN");

        DaftarKejuruan = new ArrayList<HashMap<String, String>>();
        new AmbilData().execute();

        list = (ListView) rootView.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DaftarKejuruan.get(position);

                Intent in = new Intent(getActivity().getApplicationContext(),SubKejuruan_Activity.class);
                in.putExtra(TAG_ID, map.get(TAG_ID));
                in.putExtra(TAG_KDKEJURUAN, map.get(TAG_KDKEJURUAN));
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));

                //in.putExtra(TAG_NAMA, map.get(TAG_NAMA));
                in.putExtra(TAG_DESK, map.get(TAG_DESK));
               // Toast.makeText(getActivity().getApplicationContext(), map.get(TAG_ID), Toast.LENGTH_SHORT).show();
                startActivity(in);




            }
        });


        return rootView;


    }


    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_kejuruan(getActivity(), datakejuruan);
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


                string_json = json.getJSONArray("jsonkelas"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String id = c.getString(TAG_ID);
                    String KdKejuruan = c.getString(TAG_KDKEJURUAN);
                    String link_image = c.getString(TAG_GAMBAR);
                    String namakejuruan = c.getString(TAG_NAMAKERUAN);
                    String desk= c.getString(TAG_DESK);

                    HashMap<String, String> map = new HashMap<String, String>();


                    map.put(TAG_ID, id);
                    map.put(TAG_KDKEJURUAN, KdKejuruan);
                    map.put(TAG_GAMBAR, link_image);
                    map.put(TAG_NAMAKERUAN,namakejuruan);
                    map.put(TAG_DESK,desk);


                    DaftarKejuruan.add(map);

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

                    SetListViewAdapter(DaftarKejuruan);




                }
            });

        }



    }






    @Override
    public void onClick(View v) {

    }


}
