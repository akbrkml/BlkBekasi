package com.barateknologi.bbplk_cevest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by PCIT-001 on 15/10/2016.
 */
public class Detail_Berita extends AppCompatActivity {

    public static final String TAG_ID = "id_berita";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_ISI_BERITA = "isi_berita";
    public static final String TAG_GAMBAR = "gambar";
    private String id;
    private String judul;
    private String gambar;
    private String isi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        setTitle("Detail Berita");

        initComponents();
    }

    private void initComponents(){
        getExtras();

        TextView namakelas = (TextView) findViewById(R.id.textberita);
        ImageView thumb_image = (ImageView) findViewById(R.id.gambar);
        TextView Keterangan=(TextView) findViewById(R.id.textketerangan);

        namakelas.setText(judul);
        Glide.with(this).load(gambar)
                .thumbnail(0.5f)
                .override(200, 200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumb_image);
        Keterangan.setText(isi);
    }

    private void getExtras(){
        Intent i = getIntent();
        id = i.getStringExtra(TAG_ID);
        judul = i.getStringExtra(TAG_JUDUL);
        gambar = i.getStringExtra(TAG_GAMBAR);
        isi = i.getStringExtra(TAG_ISI_BERITA);
    }

}
