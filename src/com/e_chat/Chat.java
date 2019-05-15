package com.e_chat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Chat extends Activity {

	private static final String TAG = "ChatActivity";

	E_Chat_Home home=new E_Chat_Home();
	ListView lv;
	ImageButton ib1,ib2,ib3,ib4,ib5;
	TextView txt_name;
	EditText txt_msg_typing;
	TableRow tr;
	String name,toid,uid=home.user_id,msg,ext="txt";
	private arrayadapter chatArrayAdapter;
	private boolean side = false;
	
	public static String []id;
	public static String []mmsg;
	public static String []mtime;
	public static String []mdate;
	
	public static String []mmsg2;
	public static String []mtime2;
	public static String []mdate2;
	Handler timerHandler = new Handler();
	Runnable timerRunnable =new Runnable() 
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
		     status();
			//viewChat();
			timerHandler.postDelayed(this, 10000);
		}
		
	
	};
	
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		

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
		
		
		txt_name=(TextView)findViewById(R.id.textView1);
		txt_msg_typing=(EditText)findViewById(R.id.editText1);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		ib2=(ImageButton)findViewById(R.id.imageButton2);
		ib3=(ImageButton)findViewById(R.id.imageButton3);
		ib4=(ImageButton)findViewById(R.id.imageButton4);
		ib5=(ImageButton)findViewById(R.id.imageButton5);
		
		lv = (ListView) findViewById(R.id.listView1);
		tr=(TableRow)findViewById(R.id.tableRow3);
		
		toid=getIntent().getStringExtra("id");
		name=getIntent().getStringExtra("name");
		
		chatArrayAdapter = new arrayadapter(getApplicationContext(), R.layout.right_msg);
		lv.setAdapter(chatArrayAdapter);
		
		txt_msg_typing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendChatMessage();
				
			}
			
		});
		
		txt_msg_typing.setOnKeyListener(new View.OnKeyListener() {	        
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					  if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		                    return sendChatMessage();
		                }
					return false;
				}

	        });
	
		lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lv.setAdapter(chatArrayAdapter);
		
		txt_name.setText(name);
		
		SoapObject sob=new SoapObject(soapclass.NAMESPACE,"profile_pic");
		sob.addProperty("uid", uid);
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(sob, "http://tempuri.org/profile_pic");
		if(!ou.equals("ERROR")&&!ou.equals(""))
		{
			Bitmap bmp=downloadBitmap(ou);
			 Bitmap bmp2=getRoundedCornerBitmap(bmp,300);
 			ib1.setImageBitmap(bmp2);
		}
		else
		{
			
		}
		status();
		viewChat();
		timerHandler.postDelayed(timerRunnable, 0);
		
		ib5.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				
				msg=txt_msg_typing.getText().toString();
				SoapObject sob=new SoapObject(soapclass.NAMESPACE,"chat_insert");
				sob.addProperty("sid", uid);
				sob.addProperty("rid",toid);
				sob.addProperty("msg_cont",msg );
				sob.addProperty("msg_ext",ext);
				//Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_SHORT).show();
				soapclass sc=new soapclass();
				String ou=sc.Callsoap(sob, "http://tempuri.org/chat_insert");
				if(!ou.equals("ERROR")&&!ou.equals(""))
				{
					sendChatMessage();
					//viewChat();
					
					txt_msg_typing.setText("");
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Failed to send",Toast.LENGTH_SHORT).show();
				}		
			}
		});
		
		  chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
	            @Override
	            public void onChanged() {
	                super.onChanged();
	                lv.setSelection(chatArrayAdapter.getCount() - 1);
	            }
	        });
	}
		
	 private boolean sendChatMessage() {
	        chatArrayAdapter.add(new ChatMessage(side, txt_msg_typing.getText().toString()));
	        txt_msg_typing.setText("");
	       // side = !side;
	        return true;
	    }
	
	 public void status()
	 {
		 	SoapObject sob1=new SoapObject(soapclass.NAMESPACE,"status");
			sob1.addProperty("toid", uid);		
			soapclass sc=new soapclass();
			String ou=sc.Callsoap(sob1, "http://tempuri.org/status");
			if(!ou.equals("")&& !ou.equals("error"))
	 		{
	 			String []K=ou.split("#");
	 			//Toast.makeText(getApplicationContext(),K[3], Toast.LENGTH_SHORT).show();
	 			Bitmap bmp=downloadBitmap(K[0]);
	 			Bitmap bmp2=getRoundedCornerBitmap(bmp,100);
	 			ib2.setImageBitmap(bmp2);
	 			
	 			
	 		}
		 
	 }
	 
	 
	public void viewChat(){
		
		SoapObject sob1=new SoapObject(soapclass.NAMESPACE,"chat_select_person");
		sob1.addProperty("uid", uid);
		sob1.addProperty("toid", toid);		
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(sob1, "http://tempuri.org/chat_select_person");
		if(!ou.equals(""))
		{
			if(!ou.equals("error")){
				tolist2(ou);
				//Toast.makeText(getApplicationContext(),""+ou,Toast.LENGTH_SHORT).show();
			}
		}
		

	}	
	 private boolean sendChatMessage2(String msg) {
	        chatArrayAdapter.add(new ChatMessage(side, msg));
	       // txt_msg_typing.setText("");
	       // side = !side;
	        return true;
	    }
	
	public void tolist2(String ou) {
		// TODO Auto-generated method stub
		
			
		
		String[] rw = ou.split("@");
		int cc=rw.length;
		ArrayList<HashMap<String, String>> dat = new ArrayList<HashMap<String, String>>();
		mmsg=new String[rw.length];
		mtime=new String[rw.length];
		mdate=new String[rw.length];
		
		mmsg2=new String[rw.length];
		mtime2=new String[rw.length];
		mdate2=new String[rw.length];
		int k=-1,j=-1;
		//Toast.makeText(getApplicationContext(),, Toast.LENGTH_SHORT).show();
		for (int i = 0; i < rw.length; i++) {
			String[] elt = rw[i].split("#");
			HashMap<String, String> hmap = new HashMap<String, String>();
			if(elt[1].equals(uid)){
				
				k++;
				mmsg[i]=elt[5];
				sendChatMessage2(elt[5]);
				mtime[i]=elt[3];
				mdate[i]=elt[4];
				
				//adapter3(mmsg,mtime,mdate);
				//Toast.makeText(getApplicationContext(),mmsg[k], Toast.LENGTH_SHORT).show();
				
				
			}
			else{
				j++;
				mmsg2[j]=elt[5];
				side=!side;
				sendChatMessage2(elt[5]);
				side=!side;
				mtime2[j]=elt[3];
				mdate2[j]=elt[4];
				//adapter4(mmsg2,mtime2,mdate2);
			}
			
			
			}
		
	}
	public void adapter3(String[] mmsg,String[] mtime,String[] mdate)
	{
		CustomListAdapter3 cs=new CustomListAdapter3(this, mmsg, mmsg, mtime, mdate);
		lv.setAdapter(cs);
	}

	public void adapter4(String[] mmsg,String[] mtime,String[] mdate)
	{
		CustomListAdapter4 cs=new CustomListAdapter4(this, mmsg, mmsg, mtime, mdate);
		lv.setAdapter(cs);
	}
	
	public void insert()
	{
		msg=txt_msg_typing.getText().toString();
		SoapObject sob=new SoapObject(soapclass.NAMESPACE,"chat_insert");
		sob.addProperty("sid", uid);
		sob.addProperty("rid",toid);
		sob.addProperty("msg_cont",msg );
		sob.addProperty("msg_ext",ext);
		//Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_SHORT).show();
		soapclass sc=new soapclass();
		String ou=sc.Callsoap(sob, "http://tempuri.org/chat_insert");
		if(!ou.equals("ERROR")&&!ou.equals(""))
		{
			sendChatMessage2(msg);
			//viewChat();
			
			txt_msg_typing.setText("");
		}
		else
		{
			Toast.makeText(getApplicationContext(),"Failed to send",Toast.LENGTH_SHORT).show();
		}		
	}
	
	
	public void tolist(String ou) {
		// TODO Auto-generated method stub
		
			
		
		String[] rw = ou.split("@");
		int cc=rw.length;
		ArrayList<HashMap<String, String>> dat = new ArrayList<HashMap<String, String>>();
		mmsg=new String[rw.length];
		mtime=new String[rw.length];
		mdate=new String[rw.length];
		//Toast.makeText(getApplicationContext(),, Toast.LENGTH_SHORT).show();
		for (int i = 0; i < rw.length; i++) {
			String[] elt = rw[i].split("#");
			HashMap<String, String> hmap = new HashMap<String, String>();
			if(elt[1].equals(uid)){
			
				//Toast.makeText(getApplicationContext(),mmsg[i], Toast.LENGTH_SHORT).show();
			hmap.put("msg", elt[2]+": "+elt[3]);
				hmap.put("msg", elt[5]);
				
			}
			else{
				hmap.put("nam", elt[3]+": "+elt[2]);
				hmap.put("nam", elt[5]);
			}
			hmap.put("fid", elt[0]);
			
			dat.add(hmap);
			}
		
		ListAdapter la = new SimpleAdapter(this, dat, R.layout.chatview,new String[] { "nam", "msg" }, new int[] { R.id.textView1,R.id.textView2 });
		lv.setAdapter(la);
		
	}

	
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
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
	
	private Bitmap downloadBitmap(String url)
	{
	    HttpURLConnection urlConnection = null;
	    try
	    {
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
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

}
