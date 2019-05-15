package com.e_chat;

import org.ksoap2.serialization.SoapObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class OTP_Reg extends Activity {

	Button b1,b2;
	EditText txt_otp;
	String name,phone,otp,totp;
	public static String uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SQLiteDatabase db;
		setContentView(R.layout.activity_otp__reg);
		db = openOrCreateDatabase("Chat", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Register(phone varchar(50),name varchar(50));");

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
		
		txt_otp=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		
		
		name=getIntent().getStringExtra("name");
		phone=getIntent().getStringExtra("phone");
		otp=getIntent().getStringExtra("otp");
		//Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				totp=txt_otp.getText().toString();
				if(totp.matches("123"))
				{
				
				SoapObject obj=new SoapObject(soapclass.NAMESPACE,"new_insert");
				obj.addProperty("phone",phone);
				obj.addProperty("name",name);
				obj.addProperty("photo","");
				soapclass sc=new soapclass();
				String ou=sc.Callsoap(obj, "http://tempuri.org/new_insert");
				if(!ou.equals("ERROR")&&!ou.equals(""))
				{
					uid=ou;
					//Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();	
					Intent i=new Intent(getApplicationContext(),E_Chat_Home.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
				}
				}
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.otp__reg, menu);
		return true;
	}

}
