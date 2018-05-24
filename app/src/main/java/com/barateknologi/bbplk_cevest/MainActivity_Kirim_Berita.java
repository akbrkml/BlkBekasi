package com.barateknologi.bbplk_cevest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Utama.Berita;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity_Kirim_Berita extends AppCompatActivity implements View.OnClickListener {

    //unutk input databse



    private Button buttonUpload;
    private EditText edittextjudul;
    private EditText edittextisi;
    private ImageView imageView;

    public static final String KEY_TEXT_JUDUL = "judul";
    public static final String KEY_TEXT_ISI = "isi_berita";
    public static final String KEY_IMAGE = "gambar";

   // public static final String UPLOAD_URL = "http://simplifiedcoding.16mb.com/PhotoUploadWithText/upload.php";
    public static final String UPLOAD_URL = "http://barateknologi.org/androidfinger/upload.php";
    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_berita);

        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        edittextjudul = (EditText) findViewById(R.id.edittextjudul);
        edittextisi=(EditText) findViewById(R.id.edittextisi);

       // buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void uploadImage(){
        final String textjudul = edittextjudul.getText().toString().trim();
        final String textisi = edittextisi.getText().toString().trim();
        final String image = getStringImage(bitmap);
        class UploadImage extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity_Kirim_Berita.this,"Please wait...","uploading",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity_Kirim_Berita.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> param = new HashMap<String,String>();
                param.put(KEY_TEXT_JUDUL,textjudul);
                param.put(KEY_TEXT_ISI,textisi);
                param.put(KEY_IMAGE,image);
                String result = rh.sendPostRequest(UPLOAD_URL, param);
                return result;
            }
        }
        UploadImage u = new UploadImage();
        u.execute();
    }


    @Override
    public void onClick(View v) {
        if(v == imageView){
            showFileChooser();
        }


        if(v == buttonUpload){
            uploadImage();
               Intent i = new Intent(MainActivity_Kirim_Berita.this, Berita.class);
               startActivity(i);

        }


    }




    //tinggal input data
}