package com.barateknologi.bbplk_cevest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.barateknologi.bbplk_cevest.Galerry.Berita_Galery;

import com.barateknologi.bbplk_cevest.MyLogin.MainActivity;
import com.barateknologi.bbplk_cevest.MyLogin.MyAccount;
import com.barateknologi.bbplk_cevest.MyLogin.PemakaiActivity;
import com.barateknologi.bbplk_cevest.Utama.UtamaActivity;
import com.barateknologi.bbplk_cevest.frommcpm.TTGsaya;
import com.barateknologi.bbplk_cevest.frommcpm.TentangSayaActivity;
import com.barateknologi.bbplk_cevest.perusahaan.PerusahaanActivity;
import com.barateknologi.bbplk_cevest.utils.SessionManager;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;


public class MainActivityBaru extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_ID = "id";
    String idk;
    private TextView textnamadidepan;
    private ImageView imageView;
    public static final String USER_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Muhammad Adenin-+62 81210201248", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        textnamadidepan = (TextView) headerView.findViewById(R.id.textaccountdepan);


        //  textnamadidepan.setText("Muhammad Adenin");
        textnamadidepan.setText("BBPLK BEKASI");

        imageView = (ImageView) findViewById(R.id.account);
        //  new DownLoadImageTask(imageView).execute("http://barateknologi.org/androidfinger/foto_pengguna/72.png");

        //ambil id kiriman
        Intent i = getIntent();
        idk = i.getStringExtra(TAG_ID);
        //   Toast.makeText(MainActivityBaru.this,"ngeriii",Toast.LENGTH_LONG).show();


    }


    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }


        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }


        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            // new DownLoadImageTask(imageView).execute("http://barateknologi.org/androidfinger/foto_pengguna/0.png");
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.kirim_berita) {
            Intent i = new Intent(MainActivityBaru.this, MainActivity_Kirim_Berita.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Biodata Saya
            Intent i = new Intent(MainActivityBaru.this, MyAccount.class);
            startActivity(i);
            //  finish();
        } else if (id == R.id.nav_gallery) {

/*
        } else if (id == R.id.nav_slideshow) {
           //  Intent i = new Intent(MainActivityBaru.this, com.barateknologi.bbplk_cevest.Kejuruan.MainActivity.class);
         //    startActivity(i);
        } else if (id == R.id.nav_manage) {
           // Intent i = new Intent(MainActivityBaru.this,Webview_baru.class);
           // startActivity(i);

            */
        } else if (id == R.id.nav_tentangsaya) {
            // Intent i = new Intent(MainActivityBaru.this, TentangSayaActivity.class);
            Intent i = new Intent(MainActivityBaru.this, TTGsaya.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            SessionManager.clear(MainActivityBaru.this);
            Intent i = new Intent(MainActivityBaru.this, MainActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void klik_perusahaan(View view) {
        Intent i = new Intent(MainActivityBaru.this, PerusahaanActivity.class);
        startActivity(i);
    }

    public void klik_chat(View view) {
        Intent i = new Intent(MainActivityBaru.this, Activity_Chat_Group.class);
        startActivity(i);
        //   Toast.makeText(getApplicationContext(), "ON PROSES PENGERJAAN - versi dua - ", Toast.LENGTH_SHORT).show();
    }

    public void klikberita(View view) {
        Intent i = new Intent(MainActivityBaru.this, UtamaActivity.class);
        startActivity(i);

    }

    public void klik_viewloker(View view) {
        Intent i = new Intent(MainActivityBaru.this, Activity_Loker.class);
        startActivity(i);
        //  Intent i = new Intent(MainActivityBaru.this, Berita.class);
        //   startActivity(i);

    }

    //klik absen
    public void klik_profil(View view) {
        Intent i = new Intent(MainActivityBaru.this, com.barateknologi.bbplk_cevest.Kejuruan.MainActivity.class);
        startActivity(i);

    }

    public void klik_input(View view) {
        Intent i = new Intent(MainActivityBaru.this, com.barateknologi.bbplk_cevest.frommcpm.MainActivity.class);
        startActivity(i);

    }

    public void klik_pengguna(View view) {
        Intent i = new Intent(MainActivityBaru.this, PemakaiActivity.class);
        startActivity(i);

    }

    public void klik_galery(View view) {
        Intent i = new Intent(MainActivityBaru.this, com.barateknologi.bbplk_cevest.Galerry.MainActivity.class);
        startActivity(i);

    }

    public void klikgaleryy(View view) {
        Intent i = new Intent(MainActivityBaru.this, Berita_Galery.class);
        startActivity(i);
    }
}
