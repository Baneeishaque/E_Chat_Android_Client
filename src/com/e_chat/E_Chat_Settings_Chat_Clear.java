package com.e_chat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class E_Chat_Settings_Chat_Clear extends Activity {

	ListView lv;
	E_Chat_Home home=new E_Chat_Home();
	String uid=home.user_id;
	CustomListAdapter3 cs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__settings__chat__clear);
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
	
		lv=(ListView)findViewById(R.id.listView1);
		
		
		SoapObject obj=new SoapObject(soapclass.NAMESPACE,"chat_list_view");
		obj.addProperty("user_id",uid);
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(obj, "http://tempuri.org/chat_list_view");
		if(!ou.equals("")&& !ou.equals("error"))
		{		
			
			String[] rw = ou.split("@");
			
			String []idArr=new String[rw.length];
			String []nnameArr=new String[rw.length];
			Bitmap []pphotoArr=new Bitmap[rw.length];
			
			for (int i = 0; i < rw.length; i++)
			{
				    String []K=rw[i].split("#");
				    idArr[i]=K[0];
				    nnameArr[i]=K[1];
				   
				    
				    pphotoArr[i]=downloadBitmap(K[3]);
				    
					
				//Toast.makeText(getApplicationContext(),K[3], Toast.LENGTH_SHORT).show();
					//cs=new CustomListAdapter3(this, idArr, nnameArr,  pphotoArr);
			}	
		}
		else
		{
			
			Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}
		
		lv.setAdapter(cs);
		
		
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
		getMenuInflater().inflate(R.menu.e__chat__settings__chat__clear, menu);
		return true;
	}

}
