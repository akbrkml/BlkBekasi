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

public class LazyAdapter_progpel extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_progpel(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_progpel, null);

		TextView idpr = (TextView) vi.findViewById(R.id.textidprog);
		TextView idsubjur = (TextView) vi.findViewById(R.id.textidsubjur);
		TextView namaprogram = (TextView) vi.findViewById(R.id.textnamaprog);
	    ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);

		HashMap<String, String> daftar_siswa = new HashMap<String, String>();
		daftar_siswa= data.get(position);

		idpr.setText(daftar_siswa.get(ProgramPelatihan_Activity.TAG_ID_PROG));
		idsubjur.setText(daftar_siswa.get(ProgramPelatihan_Activity.TAG_ID_SUBJUR));
		namaprogram.setText(daftar_siswa.get(ProgramPelatihan_Activity.TAG_NAMAPROG));
	    imageLoader.DisplayImage(daftar_siswa.get(ProgramPelatihan_Activity.TAG_GAMBAR), thumb_image);

		return vi;
	}
}