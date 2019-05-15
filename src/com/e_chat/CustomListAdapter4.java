package com.e_chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter4 extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] idArr;
	private final String[] mmsg;
	private final String[] mtime;
	private final String[] mdate;
	public CustomListAdapter4(Activity context, String[] idArr,String[] msg,String[] time,String[] date) 
	{
		super(context, R.layout.incoming_msg, idArr);
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
		View rowView=inflater.inflate(R.layout.incoming_msg, null,true);
		
		TextView msg1 = (TextView) rowView.findViewById(R.id.chatTV);
		TextView time2 = (TextView) rowView.findViewById(R.id.timeTV);
		
		
		//msg1.setText(idArr[position]);
		msg1.setText(mmsg[position]);
		time2.setText(mtime[position]);

		
		return rowView;
		
	};

}


