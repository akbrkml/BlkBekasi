package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
 * Created by nur on 22/07/2017.
 */
public class ProgramPelatihan_Baru extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser(); // diarahkan ke master

    ArrayList<HashMap<String, String>> Dataprog = new ArrayList<HashMap<String, String>>();

    private static String url_kelas = "http://barateknologi.org/androidfinger/progpel_daftar.php";

    public static final String TAG_ID_PROG = "idprog";
    public static final String TAG_ID_SUBJUR = "idsubjur";
    public static final String TAG_NAMAPROG = "namaprogram";


    JSONArray string_json = null;
    //ListView list;
    LazyAdapter_progpel_daftar adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progpel_daftar);
        new AmbilData().execute();




    }


    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
        adapter = new LazyAdapter_progpel_daftar(this, datakejuruan);
        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);
        staticSpinner.setAdapter(adapter);
    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProgramPelatihan_Baru.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET",params);
            try {
                string_json = json.getJSONArray("jsonsubjur"); //ini nama json
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String idprog=c.getString(TAG_ID_PROG);
                    String idsubjur = c.getString(TAG_ID_SUBJUR);
                    String namaprogram = c.getString(TAG_NAMAPROG);


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_ID_PROG,idprog);
                    map.put(TAG_ID_SUBJUR,idsubjur);
                    map.put(TAG_NAMAPROG, namaprogram);




                    Dataprog.add(map);

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

                    SetListViewAdapter(Dataprog);




                }
            });

        }



    }

}
