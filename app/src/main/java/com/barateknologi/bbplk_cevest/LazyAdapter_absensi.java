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

public class LazyAdapter_absensi extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_absensi(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_absen, null);



		TextView enroll = (TextView) vi.findViewById(R.id.textnama);
		TextView datetimee=(TextView) vi.findViewById(R.id.textjammasuk);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);

		HashMap<String, String> daftar_kelas = new HashMap<String, String>();
		daftar_kelas = data.get(position);
	

		enroll.setText(daftar_kelas.get(Activity_Absen.TAG_NAMA));
		datetimee.setText(daftar_kelas.get(Activity_Absen.TAG_DATE));
		imageLoader.DisplayImage(daftar_kelas.get(Siswa_Activity.TAG_GAMBAR), thumb_image);
		return vi;
	}
}