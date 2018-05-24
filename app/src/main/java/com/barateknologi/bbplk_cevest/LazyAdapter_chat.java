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
import com.barateknologi.bbplk_cevest.frommcpm.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter_chat extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter_chat(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.list_chat, null);

	//	TextView idprog=(TextView) vi.findViewById(R.id.textidprog);
		TextView namaanggota=(TextView) vi.findViewById(R.id.textnama);
		TextView namaprogram=(TextView) vi.findViewById(R.id.textnmprog);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.gambar);

		HashMap<String, String> daftar_kelas = new HashMap<String, String>();
		daftar_kelas = data.get(position);
	
	//	idprog.setText(daftar_kelas.get(HomeFragment.TAG_IDREG));
		namaanggota.setText(daftar_kelas.get(Activity_Chat_Group.TAG_NAMA));
		namaprogram.setText(daftar_kelas.get(Activity_Chat_Group.TAG_MESSAGE));
		imageLoader.DisplayImage(daftar_kelas.get(Activity_Chat_Group.TAG_GAMBAR), thumb_image);
		return vi;
	}
}