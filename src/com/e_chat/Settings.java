package com.e_chat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Settings extends Activity
{

	Button b1,b2,b3,b4,b5;
	ImageButton ib1;
	E_Chat_Home home=new E_Chat_Home();
	String uid=home.user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

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
		b4=(Button)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		
		dp();
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Settings_Account.class);
				startActivity(i);
				
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Settings_Chat.class);
				startActivity(i);
				
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Settings_Storage.class);
				startActivity(i);
				
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Settings_Emotion.class);
				startActivity(i);
				
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Setting_Help.class);
				startActivity(i);
				
			}
		});
		ib1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Settings_profile.class);
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
	
	public void dp()
	{
		SoapObject sob=new SoapObject(soapclass.NAMESPACE,"profile_pic");
		sob.addProperty("uid", uid);
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(sob, "http://tempuri.org/profile_pic");
		if(!ou.equals("ERROR")&&!ou.equals(""))
		{
			//Toast.makeText(getApplicationContext(),ou, Toast.LENGTH_SHORT).show();
			Bitmap bmp=downloadBitmap(ou);
			Bitmap bmp2=getRoundedCornerBitmap(bmp,100);
 			ib1.setImageBitmap(bmp2);
		}
	}
	
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        int widthh = bitmap.getWidth();
	        int heighth = bitmap.getHeight();
	      
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	        final RectF rectF = new RectF(rect);
	        final float roundPx = pixels;

	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, widthh,heighth, paint);

	        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	        return output;
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
