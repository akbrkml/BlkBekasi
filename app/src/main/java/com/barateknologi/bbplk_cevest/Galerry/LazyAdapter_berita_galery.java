package com.barateknologi.bbplk_cevest.Galerry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.barateknologi.bbplk_cevest.Master.ImageLoader;
import com.barateknologi.bbplk_cevest.R;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter_berita_galery extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_berita_galery(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_item_galery_berita, null);


	//	ImageView thumb_image = (ImageView) vi.findViewById(R.id.item_img);
		TextView namafile = (TextView) vi.findViewById(R.id.textberita);

		HashMap<String, String> daftar_kelas = new HashMap<String, String>();
		daftar_kelas = data.get(position);

	//	imageLoader.DisplayImage(daftar_kelas.get(Berita_Galery.TAG_GAMBAR), thumb_image);
		namafile.setText(daftar_kelas.get(Berita_Galery.TAG_NAMAFILE));
		return vi;
	}
}