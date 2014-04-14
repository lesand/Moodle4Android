package com.example.moddleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import moodleObjects.InfoUser;
import moodleObjects.objectMessage;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Chat extends Activity {
	LinearLayout layoutChat;
	TextView Text;
	LinearLayout layoutmessage;
	SimpleDateFormat dfm;
	EditText TextMessage;
	ArrayList<objectMessage> listMessages;
	int lastMessageId;
	ScrollView sv;
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		ActionBar bar = getActionBar();
		bar.setTitle(" Chat");
		bar.setIcon(getResources().getDrawable(R.drawable.chat));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		dfm = new SimpleDateFormat("yyyyMMddHHmm");
		TextMessage = (EditText)findViewById(R.id.SendMessageText);
		layoutChat = (LinearLayout)findViewById(R.id.linearLayoutChat);
		 sv = (ScrollView)findViewById(R.id.layout30);
		
		lastMessageId = -1;
		listMessages = new ArrayList<objectMessage>();
		try {
			agregarMisMensajes();
			agregarMesajesOtros();
			OrdenarMensajes();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AgregarMensajes();
		scrollDown();
		
		 TimerTask timerTask = new TimerTask() 
	     { 
	         public void run()  
	         { 
	        	 Chat.this.runOnUiThread(new Runnable() {
	                 @Override
	                 public void run() {
	                	 try {
	     					getLastMensaje();
	     				} catch (JSONException e) {
	     					// TODO Auto-generated catch block
	     					e.printStackTrace();
	     				}
	                 }
	             });
	        	 
	         } 
	     }; 
	     
	     // Aquí se pone en marcha el timer cada segundo. 
	     Timer timer = new Timer(); 
	     // Dentro de 0 milisegundos avísame cada 1000 milisegundos 
	     timer.scheduleAtFixedRate(timerTask, 0, 10000);
	     
	     
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}
	public void agregarMisMensajes() throws JSONException
	{
		
		//String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/allMessages/user_id/3/useridfrom/4/format/json/";
		String MyId = Integer.toString(InfoUser.getInstance().userId);
		String ChatId = Integer.toString(InfoUser.getInstance().currentIdChat);
		String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/allMessages/user_id/" + ChatId + "/useridfrom/" + MyId +"/format/json/";
		String json = readJSONFeed(Url);
		if(json == null)
		{
			return;
		}
		JSONArray jObject = new JSONArray(json);
		for(int i=0; i<jObject.length(); i++)
		{
			int user = 0;
			long timeCreate = jObject.getJSONObject(i).getLong("timecreated");
			String Message = jObject.getJSONObject(i).getString("fullmessage");
			int idmessage = jObject.getJSONObject(i).getInt("id");
			objectMessage Newmessage = new objectMessage(user, Message, timeCreate, idmessage);
			listMessages.add(Newmessage);
		}
		
	
	}
	public void agregarMesajesOtros() throws JSONException
	{

		//String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/allMessages/user_id/4/useridfrom/3/format/json/";
		String MyId = Integer.toString(InfoUser.getInstance().userId);
		String ChatId = Integer.toString(InfoUser.getInstance().currentIdChat);
		String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/allMessages/user_id/" + MyId + "/useridfrom/" + ChatId +"/format/json/";
		String json = readJSONFeed(Url);
		if(json == null)
		{
			return;
		}
		JSONArray jObject = new JSONArray(json);
		for(int i=0; i<jObject.length(); i++)
		{
			int user = 1;
			long timeCreate = jObject.getJSONObject(i).getLong("timecreated");
			String Message = jObject.getJSONObject(i).getString("fullmessage");
			int idmessage = jObject.getJSONObject(i).getInt("id");
			objectMessage Newmessage = new objectMessage(user, Message, timeCreate, idmessage);
			listMessages.add(Newmessage);
			lastMessageId = idmessage;
		}
	}
	public void OrdenarMensajes()
	{
	  objectMessage tempMessage;
	  
	  for(int i=0; i<listMessages.size()-1; i++)
	  {
		  for(int j=i+1; j<listMessages.size(); j++)
		  {
			  if(listMessages.get(i).TimeCreate() > listMessages.get(j).TimeCreate())
			  {
				  tempMessage = listMessages.get(j);
				  listMessages.set(j, listMessages.get(i));
				  listMessages.set(i, tempMessage);
			  }
		  }
		  
	  }
	  
	  
	}
	public void AgregarMensajes()
	{
		for(int i=0; i<listMessages.size(); i++)			
		{
			if(listMessages.get(i).getUser() == 0)
			{
				layoutmessage = new LinearLayout(this);
				layoutmessage.setOrientation(LinearLayout.HORIZONTAL);
				layoutmessage.setBackgroundColor(Color.parseColor("#cae1f1"));		
				//AgregarImagen
				//SetearImagen(layoutmessage, getResources().getDrawable(R.drawable.jlaw), getResources(), R.drawable.jlaw);
				//AgregarTexto
				Text = new TextView(this);
				Text.setText(listMessages.get(i).getMessage());
				Text.setBackgroundColor(Color.parseColor("#cae1f1"));
				Text.setGravity(Gravity.LEFT);
				Text.setTextSize(12);
				Text.setPaintFlags(Text.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
				layoutmessage.addView(Text);
				layoutChat.addView(layoutmessage);
				TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				layoutmessage.setLayoutParams(params);
			}else
			{
				layoutmessage = new LinearLayout(this);
				layoutmessage.setOrientation(LinearLayout.HORIZONTAL);
				layoutmessage.setBackgroundColor(Color.parseColor("#FFFFFF"));		
				//AgregarImagen
				//SetearImagen(layoutmessage, getResources().getDrawable(R.drawable.jlaw), getResources(), R.drawable.jlaw);
				//AgregarTexto
				Text = new TextView(this);
				Text.setText(listMessages.get(i).getMessage());
				Text.setBackgroundColor(Color.parseColor("#FFFFFF"));
				Text.setGravity(Gravity.LEFT);
				Text.setTextSize(12);
				Text.setPaintFlags(Text.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
				layoutmessage.addView(Text);
				layoutChat.addView(layoutmessage);
				TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				layoutmessage.setLayoutParams(params);
			}
			
		}
		
	}
	public void getLastMensaje() throws JSONException
	{
		String MyId = Integer.toString(InfoUser.getInstance().userId);
		String ChatId = Integer.toString(InfoUser.getInstance().currentIdChat);
		String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/lastM/user_id/" + MyId + "/useridfrom/" + ChatId +"/format/json/";
		//String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/lastM/user_id/4/useridfrom/3/format/json/";
		String json = readJSONFeed(Url);
		if(json == null)
		{
			return;
		}
		JSONArray jObject = new JSONArray(json);
		int idMessage = jObject.getJSONObject(0).getInt("id");
		if(lastMessageId == idMessage)
		{
			System.out.println("damn");
			return;
		}
				
		String Message = jObject.getJSONObject(0).getString("fullmessage");
		System.out.println(Message);
		layoutmessage = new LinearLayout(this);
		layoutmessage.setOrientation(LinearLayout.HORIZONTAL);
		layoutmessage.setBackgroundColor(Color.parseColor("#FFFFFF"));		
		//AgregarImagen
		//SetearImagen(layoutmessage, getResources().getDrawable(R.drawable.jlaw), getResources(), R.drawable.jlaw);
		//AgregarTexto
		Text = new TextView(this);
		Text.setText(Message);
		Text.setBackgroundColor(Color.parseColor("#FFFFFF"));
		Text.setGravity(Gravity.LEFT);
		Text.setTextSize(12);
		Text.setPaintFlags(Text.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
		layoutmessage.addView(Text);		
		layoutChat.addView(layoutmessage);
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutmessage.setLayoutParams(params);
		scrollDown();
		lastMessageId = idMessage;
		
	}
	public void RenderMyMessage()
	{
		
		layoutmessage = new LinearLayout(this);
		layoutmessage.setOrientation(LinearLayout.HORIZONTAL);
		layoutmessage.setBackgroundColor(Color.parseColor("#cae1f1"));		
		//AgregarImagen
		//SetearImagen(layoutmessage, getResources().getDrawable(R.drawable.jlaw), getResources(), R.drawable.jlaw);
		//AgregarTexto
		Text = new TextView(this);
		Text.setText(TextMessage.getText().toString());
		Text.setBackgroundColor(Color.parseColor("#cae1f1"));
		Text.setGravity(Gravity.LEFT);
		Text.setTextSize(12);
		Text.setPaintFlags(Text.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
		layoutmessage.addView(Text);		
		layoutChat.addView(layoutmessage);
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutmessage.setLayoutParams(params);
		scrollDown();
		TextMessage.setText("");
		
	}
	public void SetearImagen(LinearLayout layout, Drawable picture, Resources recourse, int idPicture)
	{
		Drawable myDrawable = picture;
		Bitmap bitmap = decodeSampledBitmapFromResource(recourse, idPicture, 100, 100);
		//Drawable myDrawable = getResources().getDrawable(R.drawable.jlaw);
		//Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
	    paint.setShader(shader);
	    paint.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
		ImageView myImageView = new ImageView(this);
		myImageView.setPadding(0, 7, 0, 0);
		myImageView.setImageBitmap(circleBitmap);		
		layout.addView(myImageView);
		android.view.ViewGroup.LayoutParams layoutParams = myImageView.getLayoutParams();
		layoutParams.width = 150;
		layoutParams .height = 160;
		myImageView.setLayoutParams(layoutParams);
	}
	public void postData() throws ParseException {
	        // Create a new HttpClient and Post Header
			String MyId = Integer.toString(InfoUser.getInstance().userId);
			String ChatId = Integer.toString(InfoUser.getInstance().currentIdChat);
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://54.218.122.112/proxy/index.php/api/chat/");
	        try {
	            // Add your data
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("useridfrom", MyId));
	            nameValuePairs.add(new BasicNameValuePair("useridto", ChatId));                        
	            nameValuePairs.add(new BasicNameValuePair("fullmessage", TextMessage.getText().toString()));
	            String currentDate = dfm.format(Calendar.getInstance().getTime());
	   		    long unixtime2 = dfm.parse(currentDate).getTime();
	   		    unixtime2 = unixtime2 /1000L;
	   		    String FechaCurrent = Long.toString(unixtime2);
	            nameValuePairs.add(new BasicNameValuePair("timecreated", FechaCurrent));    
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));      
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy); 
	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	        	org.apache.http.Header[] headers = response.getAllHeaders();
	        	for (org.apache.http.Header header : headers) 
	        	{
	        		System.out.println("Key : " + header.getName() 
	        		      + " ,Value : " + header.getValue());
	        	}                        
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        }
	        
	    }
	void scrollDown()
	 {
	     Thread scrollThread = new Thread(){
	         public void run(){
	             try {
	                 sleep(200);
	                 Chat.this.runOnUiThread(new Runnable() {
	                     public void run() {
	                         sv.fullScroll(View.FOCUS_DOWN);
	                     }    
	                 });
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	         }
	     };
	     scrollThread.start();
	 }
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(res, resId, options);
		}
	public String readJSONFeed(String URL) {
	        StringBuilder stringBuilder = new StringBuilder();
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(URL);
	        try {
	            HttpResponse response = httpClient.execute(httpGet);
	            StatusLine statusLine = response.getStatusLine();
	            int statusCode = statusLine.getStatusCode();
	            if (statusCode == 200) {
	                HttpEntity entity = response.getEntity();
	                InputStream inputStream = entity.getContent();
	                BufferedReader reader = new BufferedReader(
	                        new InputStreamReader(inputStream));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    stringBuilder.append(line);
	                }
	                inputStream.close();
	            } else {
	                Log.d("JSON", "Failed to download file");
	            }
	        } catch (Exception e) {
	            Log.d("readJSONFeed", e.getLocalizedMessage());
	        }        
	        return stringBuilder.toString();
	    }
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

			//Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}

			return inSampleSize;
		}
	public void OnClickSendMessage(View v)
		{
			try {
				if(TextMessage.getText().toString().compareTo("")!=0)
					postData();
					RenderMyMessage();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
