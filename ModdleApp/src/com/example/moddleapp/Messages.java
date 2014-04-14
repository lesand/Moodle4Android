package com.example.moddleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import moodleObjects.InfoUser;
import moodleObjects.user;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.xml.sax.SAXException;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Messages extends Activity {

	LinearLayout layoutMessages;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		ActionBar bar = getActionBar();
		bar.setTitle(" Messages");
		bar.setIcon(getResources().getDrawable(R.drawable.chat));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		layoutMessages = (LinearLayout)findViewById(R.id.LayoutMessages);
		try {
			FillMessages();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@SuppressLint("NewApi")
	public void FillMessages() throws JSONException, ParserConfigurationException, SAXException, IOException
	{		
		
		InfoUser.getInstance().UpdateParticipants();
	
		for(int i=0; i<InfoUser.getInstance().Participants.size(); i++)
		{
		
			user TempUser = (user) InfoUser.getInstance().Participants.get(i);
			String MyId = Integer.toString(InfoUser.getInstance().userId);
			String Url = "http://54.218.122.112/proxy/index.php/api/chat/action/lastM/user_id/" + MyId + "/useridfrom/" + TempUser.getUserid() +"/format/json/";
			String json = readJSONFeed(Url);
			if(json == null)
			{
				continue;
			}
			JSONArray jObject = new JSONArray(json);
			String Message = jObject.getJSONObject(0).getString("fullmessage");
			
			
			LinearLayout layoutMessagesHorizontal = new LinearLayout(this);
			layoutMessagesHorizontal.setOrientation(LinearLayout.HORIZONTAL);
			layoutMessagesHorizontal.setId(i);
			layoutMessagesHorizontal.setOnClickListener(clickRowListener);
			layoutMessagesHorizontal.setBackground(getResources().getDrawable(R.drawable.stylelayout2));
			layoutMessages.addView(layoutMessagesHorizontal);
			
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 90);
			//params.setMargins(left, top, right, bottom);
			layoutMessagesHorizontal.setLayoutParams(params);
			
			DisplayMetrics  metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			float weight = (float) 0.08;
			
			InputStream is = (InputStream) new URL(TempUser.getPicture()).getContent();
			BitmapDrawable d =  (BitmapDrawable) Drawable.createFromStream(is, "src name");
			Bitmap bitmap =  Bitmap.createScaledBitmap(d.getBitmap(), 100, 100, false);
			ImageView PerfilBitmap = new ImageView(this);
			PerfilBitmap.setImageBitmap(bitmap);
			layoutMessagesHorizontal.addView(PerfilBitmap);
			TableRow.LayoutParams ParamsImage = new TableRow.LayoutParams(80, 80, weight);
			PerfilBitmap.setLayoutParams(ParamsImage);
			PerfilBitmap.setPadding(0, 15, 0, 0);
			
			
			LinearLayout layoutMesageVertical = new LinearLayout(this);
			layoutMesageVertical.setOrientation(LinearLayout.VERTICAL);
			layoutMessagesHorizontal.addView(layoutMesageVertical);
			TableRow.LayoutParams ParamsLayout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, (float)1.02);
			layoutMesageVertical.setLayoutParams(ParamsLayout);
			
			TextView TextName = new TextView(this);
			TextName.setText(TempUser.getFullName());
			TextName.setTextSize(11);
			layoutMesageVertical.addView(TextName);
			
			TextView TextLastMessage = new TextView(this);			
			TextLastMessage.setText(Message);
			TextLastMessage.setTextColor(Color.parseColor("#032944"));
			TextLastMessage.setTextSize(12);
			layoutMesageVertical.addView(TextLastMessage);
		}
	}
	
	 public static int getDPI(int size, DisplayMetrics metrics){
	     return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;        
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.drawable.menubarmessage, menu);
		return true;
	}
	
	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			user tempUser = (user)InfoUser.getInstance().Participants.get(v.getId());
			InfoUser.getInstance().currentIdChat = Integer.parseInt(tempUser.getUserid());
			Intent intent = new Intent(getApplicationContext(), Chat.class);
			startActivity(intent);
				
			
			
		}
		
	};

}
