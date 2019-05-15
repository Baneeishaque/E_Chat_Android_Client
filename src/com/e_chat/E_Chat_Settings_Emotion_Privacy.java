package com.e_chat;

import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi") public class E_Chat_Settings_Emotion_Privacy extends Activity
{

	Spinner s;
	SeekBar sb;
	public static String privacy;
	E_Chat_Home home=new E_Chat_Home();
	String uid=home.user_id;
	String p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__settings__emotion__privacy);

		try
    	{
    		if (android.os.Build.VERSION.SDK_INT > 9) 
    		{
    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    			StrictMode.setThreadPolicy(policy);
    		}
    	}
    	catch(Exception e)
    	{
    		
    	}
		
		
		s=(Spinner)findViewById(R.id.spinner1);
		sb=(SeekBar)findViewById(R.id.seekBar1);
		privacy=s.getSelectedItem().toString();
		
		SoapObject sob1=new SoapObject(soapclass.NAMESPACE,"select_privacy");
		soapclass sc1=new soapclass();
		sob1.addProperty("uid",uid);
		String ou1=sc1.Callsoap(sob1, "http://tempuri.org/select_privacy");
		if(!ou1.equals("error"))
		{
			
				s.setSelection(((ArrayAdapter< String>)s.getAdapter()).getPosition(ou1));
		}
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				int n=sb.getProgress();
				Toast.makeText(getApplicationContext(),n+"", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				 String privacy2=s.getSelectedItem().toString();
				 if(!privacy.matches(privacy2))
				 {
					 SoapObject obj=new SoapObject(soapclass.NAMESPACE,"set_privacy");
					 obj.addProperty("uid",uid);
					 obj.addProperty("privacy",privacy2);
					 soapclass sc=new soapclass();
					 String ou=sc.Callsoap(obj, "http://tempuri.org/set_privacy");
					 if(!ou.equals("")&& !ou.equals("error"))
					 {		
						 Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_SHORT).show();
			
					 }
				 }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
			
		}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__settings__emotion__privacy,
				menu);
		return true;
	}

}
