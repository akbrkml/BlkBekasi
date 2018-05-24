package com.barateknologi.bbplk_cevest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.barateknologi.bbplk_cevest.Master.ImageLoader;
import com.barateknologi.bbplk_cevest.Utama.Berita;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter_berita extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_berita(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;


		if (convertView == null)
			vi = inflater.inflate(R.layout.list_berita, null);

		TextView id_berita = (TextView) vi.findViewById(R.id.kode);
		TextView judul = (TextView) vi.findViewById(R.id.textberita);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);
		TextView isiberita=(TextView) vi.findViewById(R.id.textketerangan);

		//TextView keluar=(TextView) vi.findViewById(R.id.textkeluar);

		HashMap<String, String> daftar_kelas = new HashMap<String, String>();
		daftar_kelas = data.get(position);
	

		//id_berita.setText(daftar_kelas.get(Berita.TAG_ID));
		judul.setText(daftar_kelas.get(Berita.TAG_JUDUL));
		imageLoader.DisplayImage(daftar_kelas.get(Berita.TAG_GAMBAR), thumb_image);
		//isiberita.setText(daftar_kelas.get(Berita.TAG_ISI_BERITA));
        //keluar.setText(daftar_berita.get(Kejuruan.TAG_KELUAR));
		return vi;
	}
}