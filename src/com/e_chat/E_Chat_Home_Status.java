package com.e_chat;

import java.io.ByteArrayOutputStream;

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
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera.Size;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi") public class E_Chat_Home_Status extends Activity {
	
	protected static final int CAMERA_REQUEST = 0;
	Button b1,b2,b3;
	ImageButton ib1,ib2,ib3;
	TextView txt;
	ListView lv;
	CustomListAdapter cs;
	E_Chat_Home home=new E_Chat_Home();
	String privacy,uid=home.user_id;
	 
	
	//String []user=home.alluser;
	@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__home__status);
		ActionBar bar=getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#48b3ff")));
		
	

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
		b3=(Button)findViewById(R.id.button3);
		txt=(TextView)findViewById(R.id.textView3);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		ib3=(ImageButton)findViewById(R.id.imageButton3);
		lv=(ListView)findViewById(R.id.listView1);
		
		String time,date,delay="0";
		String []val=getIntent().getStringArrayExtra("idArr");
		int s=val.length;
		
		//Toast.makeText(getApplicationContext(),s+"", Toast.LENGTH_SHORT).show();
		
		 SoapObject obj=new SoapObject(soapclass.NAMESPACE, "status");
		 obj.addProperty("toid",uid);
		 soapclass sc=new soapclass();
		String ou=sc.Callsoap(obj, "http://tempuri.org/status");
		
		if(!ou.equals("error")&&!ou.equals(""))
		{
			String []K=ou.split("#");
 			Bitmap bmp=downloadBitmap(K[0]);
 			ib1.setImageBitmap(bmp);
 			txt.setText(K[1]);
 			date=K[4];
 			time=K[5];
 			delay=K[2];
 			//Toast.makeText(getApplicationContext(),"#"+delay, Toast.LENGTH_SHORT).show();
		}
		
		int y=Integer.parseInt(delay.toString())/1440;
		int i;
		String ou3,ou2,n="";
		Bitmap bmp;
		String []nameArr=new String[s];
		Bitmap []emoArr=new Bitmap[s];
		String []emoLabArr=new String[s];
		
		for( i=0;i<s;i++)
			{		
			

				obj=new SoapObject(soapclass.NAMESPACE,"select_privacy");
				obj.addProperty("uid",val[i]);
				Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
				sc=new soapclass();
				String o=sc.Callsoap(obj, "http://tempuri.org/select_privacy");
				if(!o.equals("")&& !o.equals("error"))
				{		
					privacy=o;
					
				}
				if(privacy.matches("Nobody"))
				{
					ou2="";
				}
				else if(privacy.matches("Everyone"))
				{
					
					ou2=status(val[i]);
				}
				else
				{
					ou2=status(val[i]);
				}
					
				
					
					
				if(!ou2.equals("error")&&!ou2.equals(""))
				{
							
						String []K=ou2.split("#");
						emoArr[i]=downloadBitmap(K[0]);
						emoLabArr[i]=K[1];
						nameArr[i]=K[3];
							
				}
			
			}
		
			
				
				
			
			
		CustomListAdapter2 cs=new CustomListAdapter2(this, nameArr,emoArr,emoLabArr);
		lv.setAdapter(cs);

			
			
			
			
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getApplicationContext(), "ERROR"+home.iidArr[0], Toast.LENGTH_SHORT).show();
				
				Intent i=new Intent(getApplicationContext(),E_Chat_Home_contact.class);
				startActivity(i);
			
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Home.class);
				startActivity(i);
				
			}
		});
		
		ib1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
				
				
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

	
	
	
	
	public String status(String uid)
	{
		String ou2="";
			SoapObject obj=new SoapObject(soapclass.NAMESPACE, "status");
			obj.addProperty("toid",uid);
			soapclass sc=new soapclass();
			String ou=sc.Callsoap(obj, "http://tempuri.org/status");
			//Toast.makeText(getApplicationContext(),ou2, Toast.LENGTH_SHORT).show();
			if(!ou.equals("ERROR")&&!ou.equals(""))
			{
				ou2= ou;
		
			}
			return ou2;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{  
      	if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) 
		{  
            		Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            		//im.setImageBitmap(photo);
            		
           	try
			{
            		ByteArrayOutputStream stream=new ByteArrayOutputStream();
            		photo.compress(Bitmap.CompressFormat.JPEG, 90, stream);
 		      		byte[] image=stream.toByteArray();
 		     		String img_str = Base64.encodeToString(image, 0);
 		     		 SoapObject obj=new SoapObject(soapclass.NAMESPACE,"update_status");
 					obj.addProperty("uid",uid);
 					obj.addProperty("img",img_str);
 					//Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
 					soapclass sc=new soapclass();
 					String o=sc.Callsoap(obj, "http://tempuri.org/update_status");
 		     		
			}
           	catch (Exception e) 
			{
				// TODO: handle exception
			}
       	}  
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__home__status, menu);
		return true;
	}

}
