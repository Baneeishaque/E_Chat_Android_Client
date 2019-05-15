package com.e_chat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class E_Chat_Home_Chat extends Activity {

	Button b1,b2,b3;
	ListView lv;
	E_Chat_Home home=new E_Chat_Home();
	String uid=home.user_id;
	
	
	CustomListAdapter cs;
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__home__chat);



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
		
	
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		lv=(ListView)findViewById(R.id.listView1);
		b3=(Button)findViewById(R.id.button3);
		
		String ou;
		
		
		SoapObject obj=new SoapObject(soapclass.NAMESPACE,"chat_list_view");
		obj.addProperty("user_id",uid);
		soapclass sc=new soapclass();
		ou=sc.Callsoap(obj, "http://tempuri.org/chat_list_view");
		//Toast.makeText(getApplicationContext(),ou,Toast.LENGTH_SHORT).show();
		if(!ou.equals("")&& !ou.equals("error"))
		{		
			
			//Toast.makeText(getApplicationContext(),ou,Toast.LENGTH_SHORT).show();
			String[] rw = ou.split("@");
			
			String []idArr=new String[rw.length];
			String []nnameArr=new String[rw.length];
			String []sstatus=new String[rw.length];
			Bitmap []pphotoArr=new Bitmap[rw.length];
			
			for (int i = 0; i < rw.length; i++)
			{
				    String []K=rw[i].split("#");
				    idArr[i]=K[0];
				    nnameArr[i]=K[1];
				    sstatus[i]=K[2];
				    
				    pphotoArr[i]=downloadBitmap(K[3]);
				    
					
				//Toast.makeText(getApplicationContext(),K[3], Toast.LENGTH_SHORT).show();
					cs=new CustomListAdapter(this, idArr, nnameArr, sstatus, pphotoArr);
			}	
		}
		else
		{
			
			Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}
		
		lv.setAdapter(cs);
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				TextView extratxt3=(TextView)arg1.findViewById(R.id.textView3);
				
				TextView extratxt1=(TextView)arg1.findViewById(R.id.textView1);
				Intent i = new Intent(getApplicationContext(),Chat.class);
				i.putExtra("id", extratxt3.getText().toString());
				i.putExtra("name", extratxt1.getText().toString());
				startActivity(i);
				//Toast.makeText(getApplicationContext(), extratxt3.getText().toString(), Toast.LENGTH_SHORT).show();
			}
			
			
		});
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Home.class);
				startActivity(i);
				
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Home_Status.class);
				startActivity(i);
				
			}
		});
		
	}
	
	
	


	private Bitmap downloadBitmap(String url) {
	    HttpURLConnection urlConnection = null;
	    try {
	        URL uri = new URL(url);
	        urlConnection = (HttpURLConnection) uri.openConnection();
	        int statusCode = urlConnection.getResponseCode();
	        if (statusCode != HttpStatus.SC_OK)
	        {
	            return null;
	           
	        }

	        InputStream inputStream = urlConnection.getInputStream();
	        if (inputStream != null)
	        {
	            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	            return bitmap;
	        }
	    } 
	    catch (Exception e) 
	    {
	        urlConnection.disconnect();
	        Log.w("ImageDownloader", "Error downloading image from " + url);
	    }
	    finally 
	    {
	        if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
	    }
	    return null;
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__home__chat, menu);
		return true;
	}

}
