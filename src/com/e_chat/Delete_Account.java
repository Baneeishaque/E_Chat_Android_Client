package com.e_chat;

import org.ksoap2.serialization.SoapObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete_Account extends Activity
{

	EditText txt_no;
	Button b1;
	String phone,uid1;
	E_Chat_Home home=new E_Chat_Home();
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete__account);
		
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
		
		
		txt_no=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		uid1=home.user_id;
		b1.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				phone=txt_no.getText().toString();
				//Toast.makeText(getApplicationContext(), "ERROR"+phone, Toast.LENGTH_SHORT).show();
				
				SoapObject obj=new SoapObject(soapclass.NAMESPACE, "match_phone");
				obj.addProperty("uid",uid1);
				soapclass sc=new soapclass();
				String ou=sc.Callsoap(obj, "http://tempuri.org/match_phone");
				//Toast.makeText(getApplicationContext(), "ERROR"+ou, Toast.LENGTH_SHORT).show();
				if(!ou.equals("ERROR")&&!ou.equals(""))
				{
					if(phone.matches(ou))
					{
							//Toast.makeText(getApplicationContext(), "ERROR"+uid1, Toast.LENGTH_SHORT).show();
							obj=new SoapObject(soapclass.NAMESPACE, "account_delete");
							obj.addProperty("phone",phone);
							sc=new soapclass();
							String ou2=sc.Callsoap(obj, "http://tempuri.org/account_delete");
							if(!ou2.equals("ERROR")&&!ou2.equals(""))
							{
							 	Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
							 	Intent i=new Intent(getApplicationContext(),E_Chat_Main.class);
								startActivity(i);
							}
						 
						}
						else
						{
						Toast.makeText(getApplicationContext(), " check phone number", Toast.LENGTH_SHORT).show();
						}
		
				}
				else
				{
					Toast.makeText(getApplicationContext(), "incorrect phone"+ou, Toast.LENGTH_SHORT).show();
				}
				
				
			}	
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete__account, menu);
		return true;
	}

}
