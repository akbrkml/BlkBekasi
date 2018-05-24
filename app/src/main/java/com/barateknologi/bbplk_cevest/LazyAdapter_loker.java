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

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter_loker extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_loker(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_view_loker, null);

	//	TextView id_berita = (TextView) vi.findViewById(R.id.kode);
		TextView id_loker = (TextView) vi.findViewById(R.id.textidloker);
		TextView judul = (TextView) vi.findViewById(R.id.textjudullowongan);
		TextView posisi = (TextView) vi.findViewById(R.id.textposisi);
		TextView namaperusahaan=(TextView) vi.findViewById(R.id.textnamaper);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);

		HashMap<String, String> daftar_kelas = new HashMap<String, String>();
		daftar_kelas = data.get(position);
	

		//id_berita.setText(daftar_kelas.get(Berita.TAG_ID));
		id_loker.setText(daftar_kelas.get(Activity_Loker.TAG_ID_LOKER));
		judul.setText(daftar_kelas.get(Activity_Loker.TAG_JUDUL));
		posisi.setText(daftar_kelas.get(Activity_Loker.TAG_POSISI));

		namaperusahaan.setText(daftar_kelas.get(Activity_Loker.TAG_NAMAPER));
		imageLoader.DisplayImage(daftar_kelas.get(Activity_Loker.TAG_GAMBAR), thumb_image);

        //keluar.setText(daftar_berita.get(Kejuruan.TAG_KELUAR));
		return vi;
	}
}