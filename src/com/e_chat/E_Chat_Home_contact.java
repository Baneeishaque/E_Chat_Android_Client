package com.e_chat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD) public class E_Chat_Home_contact extends Activity {
	
	
	
	Button b1,b2,b3;
	ImageButton ib2,ib3;
	EditText et;
	ListView lv;
	E_Chat_Home home=new E_Chat_Home();
	String phone,uid=home.user_id;
	CustomListAdapter cs;
	
	public static String FLAG="false";

	List<String> listMobileNo = new ArrayList<String>();
	List<String> idArr = new ArrayList<String>();
	List<String> idd = new ArrayList<String>();
	List<String> nameArr = new ArrayList<String>();
	List<Bitmap> photoArr = new ArrayList<Bitmap>();
	 ;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__home_contact);
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
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		ib3=(ImageButton)findViewById(R.id.imageButton3);
		et=(EditText)findViewById(R.id.editText1);
		lv=(ListView)findViewById(R.id.listView1);
		b3=(Button)findViewById(R.id.button3);
		
		 final String []idArrr;

		ib3.setVisibility(View.INVISIBLE);
		et.setVisibility(View.INVISIBLE);
		SoapObject obj;
		soapclass sc;
		String ou,ognm;
		
		
		int cc=0;
		if(FLAG=="false")
		{
		Uri simUri = Uri.parse("content://icc/adn");
		ContentResolver cr = getContentResolver();
		Cursor cursorSim = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

		while (cursorSim.moveToNext())
		{
			String id2 = cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts._ID));
			String name ="";
			//cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			idArr.add(id2);
			if (Integer.parseInt(cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
			{

				Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?", new String[] { id2 }, null);
				while (pCur.moveToNext()) 
				{
					 phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));				
					 name=cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					if (phone.contains("+91")) 
					{
						ognm = phone.substring(3, phone.length());
					} else 
					{
						ognm = phone;
					}
					 
					String sp=ognm.replaceAll("\\s", "");
					String spp=sp.replaceAll("\\W", "");
					//Toast.makeText(getApplicationContext(), spp, 3).show();
					
					obj=new SoapObject(soapclass.NAMESPACE,"contact_number");
					obj.addProperty("phone",spp);
					sc=new soapclass();
					ou=sc.Callsoap(obj, "http://tempuri.org/contact_number");
					//Toast.makeText(getApplicationContext(), "ddd"+ou, 3).show();
					if(!ou.equals("")&& !ou.equals("error"))
					{										
							//Toast.makeText(getApplicationContext(),ou,Toast.LENGTH_SHORT).show();
					
							
							
						String []K=ou.split("#");
						if(!K[0].matches(uid))
						{
						idd.add(K[0]);
						if(name=="")
						{
							nameArr.add(K[2]);
							
						}
						nameArr.add(name);
			 			listMobileNo.add(K[2]);
			 			Bitmap bmp=downloadBitmap(K[3]);
			 			 Bitmap bmp2=getRoundedCornerBitmap(bmp,300);
			 			photoArr.add(bmp2);
			 			cc++;
			 			//Toast.makeText(getApplicationContext(),vl+"" , Toast.LENGTH_SHORT).show();
						}
						
						}						
					else
					{
						
						Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
					}
					

					

				}
				pCur.close();

			}
		}
		FLAG="true";
		}
		
		
		idArrr=new String[cc];
		//cc=idArrr.length;
		String []nnameArr=new String[cc];
		String []llistMobileNo=new String[cc];
		Bitmap []pphotoArr=new Bitmap[cc];
		
			
			for(int j=0;j<cc;j++)
			{
			idArrr[j]=idd.get(j);
			Toast.makeText(getApplicationContext(),idArrr[j] , Toast.LENGTH_SHORT).show();
			nnameArr[j]=nameArr.get(j);
			llistMobileNo[j]=listMobileNo.get(j);
			pphotoArr[j]=photoArr.get(j);
			}
		
		
		cs=new CustomListAdapter(this, idArrr, nnameArr, llistMobileNo, pphotoArr);
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
		
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Home_Status.class);
				i.putExtra("idArr",idArrr);
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
		
		
		ib2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				ib3.setVisibility(View.VISIBLE);
				et.setVisibility(View.VISIBLE);
				
			}
		});
		ib3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),E_Chat_Home.class);
				startActivity(i);
				
			}
		});
	}

	
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        int widthh = bitmap.getWidth();
	        int heighth = bitmap.getHeight();
	      
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, widthh, heighth);
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
		getMenuInflater().inflate(R.menu.e__chat__home_contact, menu);
		return true;
	}
	public void onBackPressed()
	{
		Intent i=new Intent(getApplicationContext(),E_Chat_Home.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

}
