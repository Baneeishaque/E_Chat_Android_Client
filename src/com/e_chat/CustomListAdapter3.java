package com.e_chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter3 extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] idArr;
	private final String[] mmsg;
	private final String[] mtime;
	private final String[] mdate;
	
	

	public CustomListAdapter3(Activity context, String[] idArr,String[] msg,String[] time,String[] date) 
	{
		super(context, R.layout.outgoing_msg, idArr);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.idArr=idArr;
		this.mmsg=msg;
		this.mtime=time;
		this.mdate=date;
	}	
	
	public View getView(int position,View view,ViewGroup parent) 
	{
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.outgoing_msg, null,true);
		
		TextView msg1 = (TextView) rowView.findViewById(R.id.chatTV);
		TextView time2 = (TextView) rowView.findViewById(R.id.timeTV);
		
		
		//msg1.setText(idArr[position]);
		msg1.setText(mmsg[position]);
		time2.setText(mtime[position]);
//		b1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				String id=extratxt2.getText().toString();
//				
//			}
//		});
		
		
		return rowView;
		
	};

}
