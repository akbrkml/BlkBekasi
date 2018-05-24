package com.barateknologi.bbplk_cevest.perusahaan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.barateknologi.bbplk_cevest.R;

import java.io.FileInputStream;



public class MenuActivity extends AppCompatActivity {
    private String fup = "un";
    // private String fpass="pass";
    String unp;
    String tempunper= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan);
        setTitle("DESKRIPSI PERUSAHAAN");

        try {
            FileInputStream fin = openFileInput(fup);
            int c;



            while ((c = fin.read()) != -1) {
                tempunper = tempunper + Character.toString((char) c);
                TextView tnama=(TextView) findViewById(R.id.editTextnamaper);
                tnama.setText(tempunper);
            }




        } catch (Exception e) {
        }


    }

    public void klik_loker(View view) {
        finish();
        Intent i = new Intent(MenuActivity.this, LokerActivity.class);
        startActivity(i);

    }

    public void klik_profil(View view) {
        finish();
        Intent i = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(i);

    }
}
