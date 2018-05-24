package com.barateknologi.bbplk_cevest.MyLogin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barateknologi.bbplk_cevest.Master.JSONParser;
import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.RequestHandler;
import com.barateknologi.bbplk_cevest.network.APIClient;
import com.barateknologi.bbplk_cevest.network.APIInterfaces;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.echodev.resizer.Resizer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.barateknologi.bbplk_cevest.utils.SessionManager.getUsername;

public class EditFotoProfil extends AppCompatActivity implements View.OnClickListener {


    private static int SPLASH_TIME_OUT = 2000;
    private String file = "mydata";
    String temp = "";


    String mygambar;
    private ImageView imageView;
    private Button imgedit;
    private ProgressDialog pDialog;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    JSONParser jParser = new JSONParser(); // diarahkan ke master
    ArrayList<HashMap<String, String>> DaftarKelas = new ArrayList<HashMap<String, String>>();
    private static String url_kelas = "http://blkbekasi.kemnaker.go.id/androidfinger/cari.php";
    private static String url_update = "http://blkbekasi.kemnaker.go.id/androidfinger/edit_foto.php";
    JSONArray string_json = null;
    private ArrayList<File> mFile = new ArrayList<File>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_foto);
        Nammu.init(this);

        initPermission();
        initImagePicker();

        Log.d("Username", getUsername(EditFotoProfil.this));

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        imgedit = (Button) findViewById(R.id.add_friend);
        imgedit.setOnClickListener(this);


        try {
            FileInputStream fin = openFileInput(file);
            int c;
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

        } catch (Exception e) {
        }


        new AmbilData().execute();

    }


    class AmbilData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditFotoProfil.this);
            pDialog.setMessage("Proses Pencarian Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idk", getUsername(EditFotoProfil.this)));
            JSONObject json = jParser.makeHttpRequest(url_kelas, "GET", params);

            try {
                string_json = json.getJSONArray("jsoncaridata"); //ini nama json
                JSONObject c = string_json.getJSONObject(0);
                mygambar = c.getString("gambar");
                temp = c.getString("username");
                Log.d("username", temp);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                    new DownLoadImageTask(imageView).execute(mygambar);


                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if (v == imgedit) {
            try {
                upload();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == imageView) {
            showDialog();
        }


    }

    private void initPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                @Override
                public void permissionRefused() {
                    finish();
                }
            });
        }

    }

    private void initImagePicker() {
        EasyImage.configuration(this)
                .setImagesFolderName("BBPLK Bekasi")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(false);
    }

    //Respon dari add button ketika diklik, untuk memunculkan dialog
    void showDialog() {
        final CharSequence[] items = {"Kamera", "Galeri Foto",
                "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditFotoProfil.this);
        builder.setTitle("Tambah Foto");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Kamera")) {
                    EasyImage.openCamera(EditFotoProfil.this, 0);
                } else if (items[item].equals("Galeri Foto")) {
                    EasyImage.openGallery(EditFotoProfil.this, 0);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(EditFotoProfil.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        mFile.addAll(returnedPhotos);
        Picasso.with(getApplicationContext())
                .load(mFile.get(0))
                .fit()
                .centerCrop()
                .into(imageView);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void updatedata() {
        final String image = getStringImage(bitmap);
        class updatedata extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditFotoProfil.this, "", "Simpan Data", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditFotoProfil.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("username", temp);
                param.put("gambar", image);
                String result = rh.sendPostRequest(url_update, param);
                return result;
            }

        }
        updatedata u = new updatedata();
        u.execute();
    }

    private void upload(){
        APIInterfaces apiInterface = APIClient.builder()
                .create(APIInterfaces.class);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("username", temp);

        // Multiple Images
        for (int i = 0; i < mFile.size() ; i++) {
            File file = new File(String.valueOf(mFile.get(i)));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            String fileImage = getStringImage(bitmap);
            builder.addFormDataPart("gambar", fileImage);
        }

        MultipartBody requestBody = builder.build();
        Call<ResponseBody> call = apiInterface.upload(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditFotoProfil.this, MyAccount.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }

}