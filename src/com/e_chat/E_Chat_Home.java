package com.e_chat;

import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpStatus;
import org.ksoap2.serialization.SoapObject;

import android.R.menu;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class E_Chat_Home extends Activity {

	Button b1, b2, b3;
	ImageButton ib1, ib2;
	ListView lv1;
	String name, phone, id;
	OTP_Reg otp = new OTP_Reg();
	CustomListAdapter cs;

	List<String> listMobileNo = new ArrayList<String>();
	List<String> idArr = new ArrayList<String>();
	List<String> idd = new ArrayList<String>();
	List<String> nameArr = new ArrayList<String>();
	List<Bitmap> photoArr = new ArrayList<Bitmap>();
	public static String[] iidArrr;
	public static String[] phname;
	public static String[] alluser;

	public static String FLAG = "false";

	public static String user_id = "1";

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_e__chat__home);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#48b3ff")));
		try {
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		} catch (Exception e) {

		}

		SoapObject obj;
		soapclass sc;
		String ou, ognm;

		// user_id=otp.uid;

		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		lv1 = (ListView) findViewById(R.id.listView1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		// id = user_id;

		// allUser();

		// emotion(user_id);
		int cc = 0;

		if (FLAG == "false") {
			Uri simUri = Uri.parse("content://icc/adn");
			ContentResolver cr = getContentResolver();
			Cursor cursorSim = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

			while (cursorSim.moveToNext()) {
				int c = 0;
				String id2 = cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts._ID));
				String name = "";
				// =
				// cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				idArr.add(id2);
				if (Integer.parseInt(cursorSim
						.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id2 }, null);
					while (pCur.moveToNext()) {
						name = cursorSim.getString(cursorSim.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						// Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
						if (phone.contains("+91")) {
							ognm = phone.substring(3, phone.length());
						} else {
							ognm = phone;
						}

						String sp = ognm.replaceAll("\\s", "");
						String spp = sp.replaceAll("\\W", "");
						// Toast.makeText(getApplicationContext(),name, 3).show();

						obj = new SoapObject(soapclass.NAMESPACE, "contact_number");
						obj.addProperty("phone", spp);
						sc = new soapclass();
						ou = sc.Callsoap(obj, "http://tempuri.org/contact_number");
						if (!ou.equals("") && !ou.equals("error")) {
							Toast.makeText(getApplicationContext(), ou, Toast.LENGTH_SHORT).show();

							String[] K = ou.split("#");
							if (!K[0].matches(user_id)) {

								idd.add(K[0]);
								if (name != "") {
									nameArr.add(name);
								}
								nameArr.add(K[2]);
								listMobileNo.add(K[2]);
								Bitmap bmp = downloadBitmap(K[3]);
								photoArr.add(bmp);
								cc++;
							}

						} else {

							// Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
						}

					}
					pCur.close();

				}
			}
			FLAG = "true";
		}

		iidArrr = new String[cc];
		String[] nnameArr = new String[cc];
		String[] llistMobileNo = new String[cc];
		Bitmap[] pphotoArr = new Bitmap[cc];

		for (int j = 0; j < cc; j++) {

			iidArrr[j] = idd.get(j);

			nnameArr[j] = nameArr.get(j);
			llistMobileNo[j] = listMobileNo.get(j);
			pphotoArr[j] = photoArr.get(j);
		}
		// Toast.makeText(getApplicationContext(),iidArrr.length,
		// Toast.LENGTH_SHORT).show();
		// cs=new CustomListAdapter(this, iidArr, nnameArr, llistMobileNo, pphotoArr);

		String ou2;

		obj = new SoapObject(soapclass.NAMESPACE, "chat_list_view");
		obj.addProperty("user_id", user_id);
		sc = new soapclass();
		ou2 = sc.Callsoap(obj, "http://tempuri.org/chat_list_view");

		if (!ou2.equals("") && !ou2.equals("error")) {

			Toast.makeText(getApplicationContext(), ou2, Toast.LENGTH_SHORT).show();
			String[] rw = ou2.split("@");

			String[] idArrr = new String[rw.length];

			String[] nnameArr2 = new String[rw.length];

			String[] sstatus = new String[rw.length];
			Bitmap[] pphotoArr2 = new Bitmap[rw.length];

			for (int i = 0; i < rw.length; i++) {

				String[] K = rw[i].split("#");
				idArrr[i] = K[0];
				nnameArr2[i] = K[1];
				sstatus[i] = K[2];
				Bitmap bmp2 = getRoundedCornerBitmap(downloadBitmap(K[3]), 100);
				pphotoArr2[i] = bmp2;

				// Toast.makeText(getApplicationContext(),K[3], Toast.LENGTH_SHORT).show();
				cs = new CustomListAdapter(this, idArrr, nnameArr2, sstatus, pphotoArr2);
				lv1.setAdapter(cs);
			}
		} else {

			// Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}

		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView extratxt3 = (TextView) arg1.findViewById(R.id.textView3);

				TextView extratxt1 = (TextView) arg1.findViewById(R.id.textView1);
				Intent i = new Intent(getApplicationContext(), Chat.class);
				i.putExtra("id", extratxt3.getText().toString());
				i.putExtra("name", extratxt1.getText().toString());
				startActivity(i);
				// Toast.makeText(getApplicationContext(), extratxt3.getText().toString(),
				// Toast.LENGTH_SHORT).show();
			}

		});

		// user_id=otp.uid;

//		name = getIntent().getStringExtra("nm");
//		phone = getIntent().getStringExtra("ph");

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getApplicationContext(), E_Chat_Home_contact.class);
				i.putExtra("idArr", iidArrr);
				startActivity(i);

			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), E_Chat_Home_Status.class);
				i.putExtra("idArr", iidArrr);
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
			if (statusCode != HttpStatus.SC_OK) {
				return null;

			}

			InputStream inputStream = urlConnection.getInputStream();
			if (inputStream != null) {
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		} catch (Exception e) {
			urlConnection.disconnect();
			Log.w("ImageDownloader", "Error downloading image from " + url);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return null;
	}

	public void allUser() {

		SoapObject obj = new SoapObject(soapclass.NAMESPACE, "all_user");
		soapclass sc = new soapclass();
		String ou = sc.Callsoap(obj, "http://tempuri.org/all_user");
		if (!ou.equals("") && !ou.equals("error")) {
			String[] K = ou.split("#");
			int s = K.length;
			alluser = new String[s];
			for (int i = 0; i < s; i++) {
				alluser[i] = K[i];
			}
			// Toast.makeText(getApplicationContext(), "size"+s, Toast.LENGTH_SHORT).show();

		} else {

			// Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}
	}

	public void emotion(String uid) {

		SoapObject obj = new SoapObject(soapclass.NAMESPACE, "set_status");
		soapclass sc = new soapclass();
		String ou = sc.Callsoap(obj, "http://tempuri.org/set_status");
		if (!ou.equals("") && !ou.equals("error")) {

			// Toast.makeText(getApplicationContext(), "size"+s, Toast.LENGTH_SHORT).show();

		} else {

			// Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
		}

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
		canvas.drawRoundRect(rectF, widthh, heighth, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.e__chat__home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent j = new Intent(getApplicationContext(), Settings.class);
			startActivity(j);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
