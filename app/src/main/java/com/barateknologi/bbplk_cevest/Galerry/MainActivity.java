package com.barateknologi.bbplk_cevest.Galerry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.barateknologi.bbplk_cevest.Activity_Loker;
import com.barateknologi.bbplk_cevest.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GalleryAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<ImageModel> data = new ArrayList<>();
  /*  public static String IMGS[] = {
            "http://barateknologi.org/androidfinger/foto_berita/berita1.jpg",
            "http://barateknologi.org/androidfinger/foto_berita/jagad.jpg",
            "http://barateknologi.org/androidfinger/foto_berita/fmd1.jpg",
            "http://barateknologi.org/androidfinger/foto_berita/berita1.jpg",

    };
*/

   //bagaimana mendapatkan ini
    String IMGS[] = {"http://barateknologi.org/androidfinger/foto_berita/berita1.jpg",
          "http://barateknologi.org/androidfinger/foto_berita/jagad.jpg",
           "http://drive.google.com/drive/folders/0B1KyHw69TzudNTltZFNURDZmS28", "Rupesh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_galery);

        //menampilkan image itu ada di sini om
        for (int i = 0; i < IMGS.length; i++) {

            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
          //  imageModel.setUrl(IMGS[i]);
          //  imageModel.setUrl("http://barateknologi.org/androidfinger/foto_berita/jagad.jpg");
            data.add(imageModel);

          //  imageModel.setUrl(IMGS[i]);
         //   data.add(imageModel);



        }

/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new GalleryAdapter(MainActivity.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                }));

    }



}
