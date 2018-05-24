package com.barateknologi.bbplk_cevest.Kejuruan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barateknologi.bbplk_cevest.LazyAdapter_kelas;
import com.barateknologi.bbplk_cevest.LazyAdapter_subjur;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SubKejuruan extends Fragment {

    String idk;
    String s_idkejuruan;
    String ket; // ket ini ada nama kejuruan
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DataSiswa = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/subkejuruan.php";

    public static final String TAG_ID_SUBJUR = "idsubjur";
    public static final String TAG_ID = "idkejuruan";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_NAMASUBJUR = "namasubjur";
    public static final String TAG_KETERANGAN="keterangan";

    JSONArray string_json = null;
    ListView list;
    LazyAdapter_subjur adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.activity_subjur, container, false);
        // Button btn = (Button) rootView.findViewById(R.id.btn);

     //   getActivity().setTitle("SUB KEJURUAN");
        DataSiswa = new ArrayList<HashMap<String, String>>();
        new AmbilData().execute();

        list = (ListView) rootView.findViewById(R.id.list);

        Intent i = getActivity().getIntent();
        s_idkejuruan= i.getStringExtra(TAG_ID); //ini oke
        // TextView idkejuruan=(TextView) findViewById(R.id.requestidsiswa);
        //  idkejuruan.setText(s_idkejuruan);
        idk= i.getStringExtra(TAG_ID);








        return rootView;
    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_subjur(getActivity(), datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  pDialog = new ProgressDialog(SubKejuruan_Activity.this);
            pDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {



            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idk","1"));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);

            try {


                string_json = json.getJSONArray("jsonsubjur"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);

                    String idsubjur = c.getString(TAG_ID_SUBJUR);
                    String idkejuruan = c.getString(TAG_ID);
                    String nama = c.getString(TAG_NAMASUBJUR);
                    String link_image = c.getString(TAG_GAMBAR);
                    String keterangan = c.getString(TAG_KETERANGAN);
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ID_SUBJUR,idsubjur);
                    map.put(TAG_ID,idkejuruan);
                    map.put(TAG_NAMASUBJUR,nama);
                    map.put(TAG_GAMBAR, link_image);
                    map.put(TAG_KETERANGAN, keterangan);



                    DataSiswa.add(map);

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

                    SetListViewAdapter(DataSiswa);


                }
            });

        }



    }


}