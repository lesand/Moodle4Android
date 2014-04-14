package com.example.moodle4android;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import moodleObjects.infoCourse;
import moodleObjects.listPerfilUserInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

@SuppressLint("NewApi")
public class SendMassiveMessage extends Activity {

	listPerfilUserInfo participantes;
	infoCourse currentCourse;
	EditText MessageEdit;
	SimpleDateFormat dfm;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_massive_message);
		getActionBar().setTitle(" Enviar Aviso");
		Bundle b = getIntent().getBundleExtra("listparticipantsInfo");
		if(b!=null)
		{
			participantes = (listPerfilUserInfo)b.getSerializable("listparticipants");
			currentCourse = (infoCourse)b.getSerializable("currentCourse");
			MessageEdit = (EditText)findViewById(R.id.EditMessageTeacher);
			dfm = new SimpleDateFormat("yyyyMMddHHmm");
			
		}else
		{
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_massive_message, menu);
		return true;
	}
	
	public void OnClickSendMessage(View v)
	{
		if(MessageEdit.getText().toString().compareTo("")==0)
		{
			AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SendMassiveMessage.this);
	   	 	dlgAlert.setMessage("El Mensaje que esta enviando esta vacio");
	        dlgAlert.setTitle("AVISO");
	        dlgAlert.setPositiveButton("OK", null);
	        dlgAlert.setCancelable(true);
	        dlgAlert.create().show();

	        dlgAlert.setPositiveButton("Ok",
	        new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        		return;
	            }
	        });
	        
	        return;
		}
		for(int i=0; i<participantes.SizeList(); i++)
		{
			if(participantes.getElement(i).getUserid().compareTo(currentCourse.getCurrentUser().getIdMoodle())==0)
			{
				continue;
			}else
			{
				try{
					postData(participantes.getElement(i).getUserid());
					AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SendMassiveMessage.this);
			   	 	dlgAlert.setMessage("Se Envio Aviso Exitosamente");
			        dlgAlert.setTitle("AVISO");
			        dlgAlert.setPositiveButton("OK", null);
			        dlgAlert.setCancelable(true);
			        dlgAlert.create().show();

			        dlgAlert.setPositiveButton("Ok",
			        new DialogInterface.OnClickListener() {
			        	public void onClick(DialogInterface dialog, int which) {
			        		return;
			            }
			        });
				}catch(Exception ex)
				{
					continue;
				}
				
			}
			
		}
	}
	
	public void postData(String ChatId) throws ParseException {
        // Create a new HttpClient and Post Header
		String MyId = currentCourse.getCurrentUser().getIdMoodle();
		String Url = obtainUrl(currentCourse.getCurrentUser().getUrl());
	    HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Url+"proxy/index.php/api/chat/");
        try {
            // Add your data
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("useridfrom", MyId));
            nameValuePairs.add(new BasicNameValuePair("useridto", ChatId));                        
            nameValuePairs.add(new BasicNameValuePair("fullmessage", MessageEdit.getText().toString()));
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

}
