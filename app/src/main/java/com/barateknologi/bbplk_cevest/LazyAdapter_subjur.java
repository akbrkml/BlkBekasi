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

public class LazyAdapter_subjur extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_subjur(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_subkejuruan, null);

	//	TextView idsubjur = (TextView) vi.findViewById(R.id.textidsubjur);
	//	TextView idkejuruan = (TextView) vi.findViewById(R.id.textid);
	 	TextView nama=(TextView) vi.findViewById(R.id.textnama);
		TextView ket=(TextView) vi.findViewById(R.id.textket);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar1);

		HashMap<String, String> daftar_siswa = new HashMap<String, String>();
		daftar_siswa= data.get(position);

	//	idsubjur.setText(daftar_siswa.get(SubKejuruan_Activity.TAG_ID_SUBJUR));
	//	idkejuruan.setText(daftar_siswa.get(SubKejuruan_Activity.TAG_ID));
	 	nama.setText(daftar_siswa.get(SubKejuruan_Activity.TAG_NAMASUBJUR));
		ket.setText(daftar_siswa.get(SubKejuruan_Activity.TAG_KETERANGAN));
		imageLoader.DisplayImage(daftar_siswa.get(SubKejuruan_Activity.TAG_GAMBAR), thumb_image);

		return vi;
	}
}