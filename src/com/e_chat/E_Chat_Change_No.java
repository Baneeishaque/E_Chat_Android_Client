package com.e_chat;

//import org.ksoap2.serialization.SoapObject;

import java.io.ObjectOutputStream.PutField;

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

public class E_Chat_Change_No extends Activity {

	EditText txt_oldno,txt_newno;
	Button b1;
	E_Chat_Home home=new E_Chat_Home();
	String oldno,newno,uid;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__change__no);



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
		
		txt_oldno=(EditText)findViewById(R.id.editText1);
		txt_newno=(EditText)findViewById(R.id.editText2);
		b1=(Button)findViewById(R.id.button1);
		uid=home.user_id;
		
		b1.setOnClickListener(new OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				oldno=txt_oldno.getText().toString();
				newno=txt_newno.getText().toString();
				
				SoapObject obj=new SoapObject(soapclass.NAMESPACE, "match_phone");
				obj.addProperty("uid",uid);
				soapclass sc=new soapclass();
				String ou=sc.Callsoap(obj, "http://tempuri.org/match_phone");
				Toast.makeText(getApplicationContext(), "ERROR"+ou, Toast.LENGTH_SHORT).show();
				if(!ou.equals("ERROR")&&!ou.equals(""))
				{
					if(ou.matches(oldno))
					{
			
						obj=new SoapObject(soapclass.NAMESPACE, "account_change");
						obj.addProperty("old_no",oldno);
						obj.addProperty("new_no",newno);
						sc=new soapclass();
						String ou2=sc.Callsoap(obj, "http://tempuri.org/account_change");
						if(!ou2.equals("ERROR")&&!ou2.equals(""))
						{
							Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
							Intent i=new Intent(getApplicationContext(),E_Chat_Settings_Account.class);
							startActivity(i);
						}
						
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Number missmatch", Toast.LENGTH_SHORT).show();
						txt_newno.setText("");
						txt_oldno.setText("");
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
				}
				
		}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__change__no, menu);
		return true;
	}

}
