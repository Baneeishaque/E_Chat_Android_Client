package com.e_chat;

import java.io.ByteArrayOutputStream;

import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class E_Chat_Settings_profile extends Activity {
	
	EditText txt_name,txt_phone;
	Button b1;
	E_Chat_Home home=new E_Chat_Home();
	String uid=home.user_id,new_no,name,pic;
	ImageButton ib;
	protected static final int PICK_IMAGE_REQUEST = 0;
	private static final int PICK_FROM_GALLERY=2;
	String img_str;
	Bitmap bitmap;
	Uri uri;
	public static int w;
	final int PIC_CROP = 2;
	public static int h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_e__chat__settings_profile);
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
		
		
		txt_name=(EditText)findViewById(R.id.editText1);
		txt_phone=(EditText)findViewById(R.id.editText2);
		b1=(Button)findViewById(R.id.button1);
		ib=(ImageButton)findViewById(R.id.imageButton1);
		 h=ib.getMeasuredHeight();
		w=ib.getMeasuredWidth();
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

			
			}
		});
		

		//Toast.makeText(getApplicationContext(), uid,Toast.LENGTH_SHORT).show();
		SoapObject obj=new SoapObject(soapclass.NAMESPACE,"user_profile");
		obj.addProperty("user_id",uid);
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(obj, "http://tempuri.org/user_profile");
		if(!ou.equals("")&& !ou.equals("error"))
 		{
 			String []K=ou.split("#");
 			//Toast.makeText(getApplicationContext(),K[3], Toast.LENGTH_SHORT).show();
 			txt_name.setText(K[1]+"");
 			txt_phone.setText(K[2]+"");
 			Bitmap bmp=downloadBitmap(K[3]);
 			Bitmap bmp2=getRoundedCornerBitmap(bmp,100);
 			ib.setImageBitmap(bmp2);
 			
 			
 		}
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new_no=txt_phone.getText().toString();
				name=txt_name.getText().toString();
				if(img_str !=null)
				{
					pic=img_str;
				}
				else
				{
					dp();
				}
				
				SoapObject obj=new SoapObject(soapclass.NAMESPACE,"profile_update");
				obj.addProperty("user_id",uid);
				obj.addProperty("user_phone",new_no);
				obj.addProperty("user_name",name);
				obj.addProperty("user_pic",pic);
				soapclass sc=new soapclass();
				String ou=sc.Callsoap(obj, "http://tempuri.org/profile_update");
				//Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
				if(!ou.equals("ERROR")&&!ou.equals(""))
				{
					Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
					Intent i=new Intent(getApplicationContext(),Settings.class);
					home.FLAG="false";
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	 {
        	super.onActivityResult(requestCode, resultCode, data);
        	if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
			 {
	     		Uri uri = data.getData();
	     
	           		 try {
	               			 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
	               			Bitmap bmp2=getRoundedCornerBitmap(bitmap,100);
	               			 ib.setImageBitmap(bmp2);
	                			ByteArrayOutputStream stream=new ByteArrayOutputStream();
			      	 bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
			       	 byte[] image=stream.toByteArray();
	               			 img_str = Base64.encodeToString(image, 0);
	                
	                		     
	            		} catch (IOException e) {
	                e.printStackTrace();
	           	 }
         
        		 
        	 }
        	}
        	
        	
        
	
	 	
	
public void ImageCropFunction()
{
	// Image Crop Code
	 try 
	 {
		 Intent CropIntent = new Intent("com.android.camera.action.CROP");
		 Toast toast = Toast.makeText(this, "cropping", Toast.LENGTH_SHORT);
		 CropIntent.setDataAndType(uri, "image/*");

		 CropIntent.putExtra("crop", "true");
		 CropIntent.putExtra("outputX", 180);
		 CropIntent.putExtra("outputY", 180);
		 CropIntent.putExtra("aspectX", 3);
		 CropIntent.putExtra("aspectY", 4);
		 CropIntent.putExtra("scaleUpIfNeeded", true);
		 CropIntent.putExtra("return-data", true);

		 startActivityForResult(CropIntent, 1);

	 } 
	 catch(ActivityNotFoundException anfe)
	 {
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		    }
		}	 

	private void performCrop(Uri uri)
	{
		try 
		{
			   //call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			    //indicate image type and Uri
			cropIntent.setDataAndType(uri, "image/*");
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			    //indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			    //indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP); 
		}
		catch(ActivityNotFoundException anfe){
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}	 
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
	
	
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	        Bitmap output = Bitmap.createBitmap(180, 180, Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        int widthh = bitmap.getWidth();
	        int heighth = bitmap.getHeight();
	      
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, 180, 180);
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
	
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	    int targetWidth = 50;
	    int targetHeight = 50;
	    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
	                        targetHeight,Bitmap.Config.ARGB_8888);

	    Canvas canvas = new Canvas(targetBitmap);
	    Path path = new Path();
	    path.addCircle(((float) targetWidth - 1) / 2,
	        ((float) targetHeight - 1) / 2,
	        (Math.min(((float) targetWidth), 
	        ((float) targetHeight)) / 2),
	        Path.Direction.CCW);

	    canvas.clipPath(path);
	    Bitmap sourceBitmap = scaleBitmapImage;
	    canvas.drawBitmap(sourceBitmap, 
	        new Rect(0, 0, sourceBitmap.getWidth(),
	        sourceBitmap.getHeight()), 
	        new Rect(0, 0, targetWidth, targetHeight), null);
	    return targetBitmap;
	}
	
	
	
	public void dp()
	{
		SoapObject sob=new SoapObject(soapclass.NAMESPACE,"profile_pic");
		sob.addProperty("uid", uid);
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(sob, "http://tempuri.org/profile_pic");
		pic=ou;
		if(!ou.equals("ERROR")&&!ou.equals(""))
		{
			Bitmap bmp=downloadBitmap(ou);
 			
 			ib.setImageBitmap(bmp);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.e__chat__settings_profile, menu);
		return true;
	}

}
