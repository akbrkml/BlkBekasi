package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
public class SubKejuruan_Activity extends AppCompatActivity {
    String idk;
    String s_idkejuruan;
    String ket; // ket ini ada nama kejuruan
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> DataSiswa = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/subkejuruan.php";

    public static final String TAG_ID_SUBJUR = "idsubjur";
    public static final String TAG_ID = "idkejuruan";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_NAMASUBJUR = "namasubjur";
    public static final String TAG_KETERANGAN="keterangan";

    JSONArray string_json = null;
    ListView list;
    LazyAdapter_subjur adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjur);


        setTitle("BBPLK BEKASI");
        DataSiswa = new ArrayList<HashMap<String, String>>();
        new AmbilData().execute();

        list = (ListView) findViewById(R.id.list);

        Intent i = getIntent();
        s_idkejuruan= i.getStringExtra(TAG_ID); //ini oke
      // TextView idkejuruan=(TextView) findViewById(R.id.requestidsiswa);
      //  idkejuruan.setText(s_idkejuruan);
       idk= i.getStringExtra(TAG_ID);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = DataSiswa.get(position);

                Intent in = new Intent(getApplicationContext(), ProgramPelatihan_Activity.class);
                in.putExtra(TAG_ID, map.get(TAG_ID));
                in.putExtra(TAG_ID_SUBJUR, map.get(TAG_ID_SUBJUR));
            //    in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
              //  in.putExtra(TAG_KELAS, map.get(TAG_KELAS));
              //  in.putExtra(TAG_NAMA, map.get(TAG_NAMA));
              //  in.putExtra(TAG_KETERANGAN, map.get(TAG_KETERANGAN));
                Toast.makeText(getApplicationContext(), map.get(TAG_ID_SUBJUR), Toast.LENGTH_SHORT).show();
                startActivity(in);


                //mengirim nama kejuruan
                //  String Slecteditem = list.getItemAtPosition(position).toString();

                // in.putExtra(TAG_JUDUL, Slecteditem);


            }
        });




    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_subjur(this, datakejuruan);
        list.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SubKejuruan_Activity.this);
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

            runOnUiThread(new Runnable() {
                public void run() {

                    SetListViewAdapter(DataSiswa);




                }
            });

        }



    }

}
