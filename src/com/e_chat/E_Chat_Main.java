package com.e_chat;

import java.util.Random;

import org.ksoap2.serialization.SoapObject;


import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class E_Chat_Main extends Activity {
	
	EditText txt_name,txt_phone;
	Button b1;
	
	private SQLiteDatabase db;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__main);

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
		
		db = openOrCreateDatabase("Chat", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Register(phone varchar(50),name varchar(50));");
		Cursor cs = db.rawQuery("SELECT * FROM Register", null);
		if (cs.getCount() > 0)
		{
			while(cs.moveToNext())
			{
			

			Intent i = new Intent(getApplicationContext(),E_Chat_Home.class);
			i.putExtra("ph", cs.getString(0));
			i.putExtra("nm", cs.getString(1));
			startActivity(i);
			}
		}

		else
		{
		}
		
		b1=(Button)findViewById(R.id.button1);
		txt_name=(EditText)findViewById(R.id.editText2);
		txt_phone=(EditText)findViewById(R.id.editText1);
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Random ra=new Random();
				int val=ra.nextInt(999);
				SmsManager sms=SmsManager.getDefault();
				sms.sendTextMessage(txt_phone.getText().toString(), null, val+"", null, null);
				
				
				Intent i=new Intent(getApplicationContext(), OTP_Reg.class);
				i.putExtra("name", txt_name.getText().toString());
				i.putExtra("phone", txt_phone.getText().toString());
				i.putExtra("otp", val+"");
				
				startActivity(i);
								
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__main, menu);
		return true;
	}

}
