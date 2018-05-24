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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.LazyAdapter_progpel;
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

/**
 * Created by Ratan on 7/29/2015.
 */
public class ProgramPelatihan extends Fragment implements View.OnClickListener {

    String kdprog;
    String idk;
    String s_idkejuruan;
    String ket; // ket ini ada nama kejuruan
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DataSiswa = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/programpelatihan.php";

    public static final String TAG_ID_PROG = "idprog";
    public static final String TAG_ID_SUBJUR = "idsubjur";
    public static final String TAG_NAMAPROG = "namaprogram";
    public static final String TAG_GAMBAR = "gambar";

    JSONArray string_json = null;
    ListView list;
    LazyAdapter_progpel adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.activity_progpel, container, false);
        // Button btn = (Button) rootView.findViewById(R.id.btn);


        getActivity().setTitle("PROGRAM PELATIHAN");
        DataSiswa = new ArrayList<HashMap<String, String>>();
        new AmbilData().execute();

     //   Intent i = getActivity().getIntent();
     //   idk = i.getStringExtra(TAG_ID_SUBJUR);


        list = (ListView) rootView.findViewById(R.id.listprog);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DataSiswa.get(position);

                //Intent in = new Intent(getApplicationContext(), com.barateknologi.bbplk_cevest.frommcpm.Kejuruan.class);
                //   in.putExtra(TAG_ID_PROG, map.get(TAG_ID_PROG));
                Intent in = new Intent(getActivity().getApplicationContext(), com.barateknologi.bbplk_cevest.frommcpm.MainActivity.class);
                in.putExtra(TAG_ID_PROG, map.get(TAG_ID_PROG));
                in.putExtra(TAG_NAMAPROG, map.get(TAG_NAMAPROG));
                //Toast.makeText(getApplicationContext(), map.get(TAG_ID_PROG), Toast.LENGTH_SHORT).show();
                startActivity(in);

            }
        });




        return rootView;
    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_progpel(getActivity(), datakejuruan);
        list.setAdapter(adapter);
    }
    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  pDialog = new ProgressDialog(ProgramPelatihan_Activity.this);
            pDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
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


                string_json = json.getJSONArray("jsonsubjur"); //ini nama json

                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String idprog=c.getString(TAG_ID_PROG);
                    String idsubjur = c.getString(TAG_ID_SUBJUR);
                    String namaprogram = c.getString(TAG_NAMAPROG);
                    String link_image = c.getString(TAG_GAMBAR);

                    HashMap<String, String> map = new HashMap<String, String>();

                 //   map.put(TAG_ID_PROG,idprog);
                 //   map.put(TAG_ID_SUBJUR,idsubjur);
                    map.put(TAG_NAMAPROG, namaprogram);
                    map.put(TAG_GAMBAR, link_image);

                //    kdprog= map.put(TAG_ID_PROG,idprog);

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




    @Override
    public void onClick(View v) {

    }




}
