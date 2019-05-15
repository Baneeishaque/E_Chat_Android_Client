package com.e_chat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.IInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends ArrayAdapter<String>{
	
	private final Activity context;
	private final String[] idArr;
	private final String[] nameArr;
	private final String[] listMobileNo;
	private final Bitmap[] photoArr;
	
	public CustomListAdapter(Activity context, String[] idArr,String[] nameArr,String[] listMobileNo, Bitmap[] photoArr) 
	{
		super(context, R.layout.chat_contacts_view, idArr);
		this.context=context;
		this.idArr=idArr;
		this.nameArr=nameArr;
		this.listMobileNo=listMobileNo;
		this.photoArr=photoArr;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.chat_contacts_view, null,true);
		
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		TextView extratxt1 = (TextView) rowView.findViewById(R.id.textView1);
		TextView extratxt2 = (TextView) rowView.findViewById(R.id.textView2);
		TextView extratxt3=(TextView)rowView.findViewById(R.id.textView3);
		extratxt3.setText(idArr[position]);
		extratxt1.setText(nameArr[position]);
		extratxt2.setText(listMobileNo[position]);
		imageView.setImageBitmap((photoArr[position]));
		return rowView;
		
	};
	
	 
}
