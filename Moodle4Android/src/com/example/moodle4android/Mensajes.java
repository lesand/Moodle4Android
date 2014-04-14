package com.example.moodle4android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;



import moodleObjects.infoCourse;
import moodleObjects.listPerfilUserInfo;
import moodleObjects.perfilUserInfo;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Mensajes extends Activity {

	listPerfilUserInfo participantes;
	infoCourse currentCourse;
	LinearLayout layoutMessages;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensajes);
		ActionBar bar = getActionBar();
		bar.setTitle(" Ultimos Mensajes");
		Bundle b = getIntent().getBundleExtra("listparticipantsInfo");
		if(b!=null)
		{
			participantes = (listPerfilUserInfo)b.getSerializable("listparticipants");
			currentCourse = (infoCourse)b.getSerializable("currentCourse");
			layoutMessages = (LinearLayout)findViewById(R.id.LayoutMessages);
			renderMensajes();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		if(currentCourse.getCurrentUser().getIdRole().compareTo("teacher")==0)
		{
			getMenuInflater().inflate(R.drawable.menubarteachersmessages, menu);
			return true;
		}else
		{
			getMenuInflater().inflate(R.menu.mensajes, menu);
			return true;
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.sendmessagesbtn:
								Bundle b = new Bundle();
								b.putSerializable("listparticipants", participantes);
								b.putSerializable("currentCourse", currentCourse);
								Intent main = new Intent(Mensajes.this, SendMassiveMessage.class);
						        main.putExtra("listparticipantsInfo", b);
						        startActivity(main);
								break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void renderMensajes()
	{
		String myId = currentCourse.getCurrentUser().getIdMoodle();
		String Url = obtainUrl(currentCourse.getCurrentUser().getUrl());
		for(int i=0; i<participantes.SizeList(); i++)
		{
			try{
				perfilUserInfo TempUser = participantes.getElement(i);
				Url = Url + "proxy/index.php/api/chat/action/lastM/user_id/" + myId + "/useridfrom/" + TempUser.getUserid() +"/format/json/";
				String json = readJSONFeed(Url);
				if(json == null)
				{
					continue;
				}
				JSONArray jObject = new JSONArray(json);
				String Message = jObject.getJSONObject(0).getString("fullmessage");
				LinearLayout layoutMessagesHorizontal = new LinearLayout(this);
				layoutMessagesHorizontal.setOrientation(LinearLayout.HORIZONTAL);
				layoutMessagesHorizontal.setId(Integer.parseInt(TempUser.getUserid()));
				layoutMessagesHorizontal.setOnClickListener(clickRowListener);
				layoutMessagesHorizontal.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				layoutMessages.addView(layoutMessagesHorizontal);
				
				TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 200);
				
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
				TableRow.LayoutParams ParamsImage = new TableRow.LayoutParams(180, 180, weight);
				PerfilBitmap.setLayoutParams(ParamsImage);
				PerfilBitmap.setPadding(0, 15, 0, 0);
				
				
				LinearLayout layoutMesageVertical = new LinearLayout(this);
				layoutMesageVertical.setOrientation(LinearLayout.VERTICAL);
				layoutMessagesHorizontal.addView(layoutMesageVertical);
				TableRow.LayoutParams ParamsLayout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, (float)1.02);
				layoutMesageVertical.setLayoutParams(ParamsLayout);
				
				TextView TextName = new TextView(this);
				TextName.setText(TempUser.getFullName());
				TextName.setTextSize(16);
				layoutMesageVertical.addView(TextName);
				
				TextView TextLastMessage = new TextView(this);			
				TextLastMessage.setText(Message);
				TextLastMessage.setTextColor(Color.parseColor("#032944"));
				TextLastMessage.setTextSize(14);
				layoutMesageVertical.addView(TextLastMessage);
				
			}catch(Exception ex)
			{
				continue;
			}
		}
		
	}
	

	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
		
		
			
				Bundle b = new Bundle();
				b.putSerializable("MeUserInfo", findUser(currentCourse.getCurrentUser().getIdMoodle()));
				b.putSerializable("OtherParticipant", findUser(Integer.toString(v.getId())));
				b.putSerializable("CurrentCourse", currentCourse);
				Intent main = new Intent(Mensajes.this, SplashScreenPreChat.class);
		        main.putExtra("getChatParticipants", b);
		        startActivity(main);
			
			
			
			
			
		}
		
	};
	private perfilUserInfo findUser(String idUser)
	{
		for(int i=0; i<participantes.SizeList(); i++)
		{
			if(participantes.getElement(i).getUserid().compareTo(idUser)==0)
			{
				return participantes.getElement(i);
			}
		}
		
		return null;
	}
	public String obtainUrl(String URL)
	{
		if(URL.compareTo("http://www.barcampsps.com/moodle/")==0)
		{
			return URL.substring(0, 26);
		}else
		{
			return URL;
		}
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

}
