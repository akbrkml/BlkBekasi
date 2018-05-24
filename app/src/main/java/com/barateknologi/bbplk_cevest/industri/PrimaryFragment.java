package com.barateknologi.bbplk_cevest.industri;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.LazyAdapter_progpel_daftar;
import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.frommcpm.RequestHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrimaryFragment extends Fragment {
    String name="";
    String kdprog;
    private Button savebtn;

    public static final String SAVE_URL = "http://barateknologi.org/androidfinger/pendaftaran.php";

    public static final String KEY_TEXT_ID = "id";  //untuk data registere
    public static final String KEY_TEXT_IDPROG = "idprog";


    // keawah untuk ambil data
    private ProgressDialog pDialog;

    String idreg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.primary_layout_industri, container, false);

      //  getActivity().setTitle("PENDAFTARAN PESERTA");
        savebtn = (Button) rootView.findViewById(R.id.btnsave);

        return rootView;
    }



    public void savedata() {


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


    public void onClick(View v) {
        if (v == savebtn) {
            Toast.makeText(getActivity(), "Data Belum Masuk...........", Toast.LENGTH_LONG).show();
            savedata();




        }


    }

 }


