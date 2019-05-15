package com.e_chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter2 extends ArrayAdapter<String>
{
	private final String[] nameArr;
	private final String[] emoLabArr;
	private final Bitmap[] emoArr;
	private Activity context;
	
	public CustomListAdapter2(Activity context,String[] nameArr, Bitmap[] emoArr,String[] emoLabArr) {
		super(context, R.layout.eomtionview,nameArr);
		// TODO Auto-generated constructor stub
		this.context=context;
		
		this.nameArr=nameArr;
		
		this.emoArr=emoArr;
		this.emoLabArr=emoLabArr;
	}
	public View getView(int position,View view,ViewGroup parent) 
	{
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.eomtionview, null,true);
		
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		TextView extratxt1 = (TextView) rowView.findViewById(R.id.textView1);
		TextView extratxt2 = (TextView) rowView.findViewById(R.id.textView2);
		extratxt1.setText(nameArr[position]);
		imageView.setImageBitmap((emoArr[position]));
		extratxt2.setText(emoLabArr[position]);
		return rowView;
	};
}
