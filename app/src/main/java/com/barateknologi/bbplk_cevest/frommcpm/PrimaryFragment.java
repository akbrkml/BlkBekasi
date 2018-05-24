package com.barateknologi.bbplk_cevest.frommcpm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Kejuruan_Activity;
import com.barateknologi.bbplk_cevest.LazyAdapter_progpel_daftar;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrimaryFragment extends Fragment implements View.OnClickListener {
    String name="";
    String kdprog;
    private Button savebtn;

    public static final String SAVE_URL = "http://blkbekasi.kemnaker.go.id/androidfinger/pendaftaran.php";
    public static final String KEY_TEXT_ID = "id";  //untuk data registere
    public static final String KEY_TEXT_IDPROG = "idprog";


    // keawah untuk ambil data
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONParser jParser1 = new JSONParser();
     // diarahkan ke master
  //  ArrayList<HashMap<String, String>> Dataprog = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> DaftarKelas1 = new ArrayList<HashMap<String, String>>();

    private static String url_kelas_reg = "http://blkbekasi.kemnaker.go.id/androidfinger/cari.php";
    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/progpel_daftar.php";
    JSONArray string_json = null;
    JSONArray string_json1 = null;
    public static final String TAG_ID_PROG = "idprog";
    public static final String TAG_NAMAPROG = "namaprogram";


    LazyAdapter_progpel_daftar adapter;
    Spinner staticSpinner;

    private String file = "mydata";
    String temp = "";
    String idreg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.primary_layout_mcpm, container, false);

      new AmbilData().execute();
    //    new AmbilData_register().execute();

       // getActivity().setTitle("PENDAFTARAN PESERTA");
        savebtn = (Button) rootView.findViewById(R.id.btnsave);
        savebtn.setOnClickListener(this);



        final Spinner cardStatusSpinner1 = (Spinner) rootView.findViewById(R.id.static_spinner);
        cardStatusSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
             //   EditText cp = (EditText) getActivity().findViewById(R.id.Editcontoh);
             //   cp.setText(getName(pos));
                kdprog=getName(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        try {
            FileInputStream fin = getActivity().openFileInput(file);
            int c;
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }

        } catch (Exception e) {
        }



        return rootView;
    }


    private String getName(int position){

        try {
            //Getting object of given index
            JSONObject json = string_json.getJSONObject(position);
            name = json.getString(TAG_ID_PROG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    public void savedata() {


      //  final String id ="17"; //editnama.getText().toString().trim();  //mengendalikan trim
      //  final String idprog="1"; //editnama.getText().toString().trim();  //mengendalikan trim
       //
       // final String idprog = editidprog.getSelectedView().toString().trim(); //mengambil nilai



        class UploadImage extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Please wait...","Simpan Data",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s==""){
                    Toast.makeText(getActivity(), "Data Belum Masuk...........", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> param = new HashMap<String,String>();
                    param.put(KEY_TEXT_IDPROG,kdprog);
                    param.put(KEY_TEXT_ID,idreg);
                String result = rh.sendPostRequest(SAVE_URL, param);
                return result;
            }
        }

        UploadImage u = new UploadImage();
        u.execute();
    }



    public void SetListViewAdapter(ArrayList<HashMap<String, String>> datakejuruan) {
       //hanya menampilkan ke spinner

     adapter = new LazyAdapter_progpel_daftar(getActivity(), datakejuruan);
     staticSpinner = (Spinner) getActivity().findViewById(R.id.static_spinner);
     staticSpinner.setAdapter(adapter);

    }


//ambil id register



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


            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("idk", temp));
            JSONObject json1 = jParser1.makeHttpRequest(url_kelas_reg, "GET",params1);



            try {

                string_json1 = json1.getJSONArray("jsoncaridata"); //ini nama json
                JSONObject c1 = string_json1.getJSONObject(0);
                idreg = c1.getString("id");
              //  HashMap<String, String> map1 = new HashMap<String, String>();
              //  DaftarKelas1.add(map1);




                string_json = json.getJSONArray("jsonsubjur"); //ini nama json
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String iddannama=c.getString(TAG_NAMAPROG);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID_PROG,iddannama);
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
                   // TextView txtnama = (TextView) getActivity().findViewById(R.id.EditNama);
                 //   txtnama.setText(idreg);




                }
            });

        }



    }



    @Override
    public void onClick(View v) {


        if (v == savebtn) {
          savedata();




        }


    }



}
