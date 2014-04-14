package com.example.moddleapp;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import moodleObjects.InfoUser;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TimePicker;
import android.widget.Toast;
@SuppressLint("NewApi")
public class CreateHomeWork extends Activity {

	EditText NombreTarea;
	EditText DescripcionTarea;
	DatePicker FechaEntrega;
	SimpleDateFormat dfm;
	TimePicker HoraEntrega;
	String FechaE;
	String FechaCurrent;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_home_work);
		ActionBar bar = getActionBar();
		bar.setTitle(" Tareas");
		bar.setIcon(getResources().getDrawable(R.drawable.tareas));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		NombreTarea = (EditText)findViewById(R.id.editNameHomework);
		DescripcionTarea = (EditText)findViewById(R.id.editDescripcionTarea);
		FechaEntrega = (DatePicker)findViewById(R.id.dateFechaEntrega);
		dfm = new SimpleDateFormat("yyyyMMddHHmm");
		HoraEntrega = (TimePicker)findViewById(R.id.TimeHour);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_home_work, menu);
		return true;
	}
	
	
	
	 public void postData() throws JSONException {
	        // Create a new HttpClient and Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://54.218.122.112/proxy/index.php/api/assigns/");
	        try {
	            // Add your data
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("action", "insertAssign"));
	            nameValuePairs.add(new BasicNameValuePair("courseId", InfoUser.getInstance().CurrentCourse.getCourseIDNumber()));                        
	            //for the course content
	            nameValuePairs.add(new BasicNameValuePair("sectionID", InfoUser.getInstance().listWeeks.get(InfoUser.getInstance().currentWeek).idSeccion));            
	            nameValuePairs.add(new BasicNameValuePair("dueDate", FechaE));
	            nameValuePairs.add(new BasicNameValuePair("timeAdded", "FechaCurrent"));
	            nameValuePairs.add(new BasicNameValuePair("name", NombreTarea.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("intro", DescripcionTarea.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("allowsubmissionsfromdate", FechaCurrent));
	            System.out.println("UnixTime: "+ FechaCurrent);
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            
	           
	            //end coursecontent
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy); 
	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	        	org.apache.http.Header[] headers = response.getAllHeaders();
	        	for (org.apache.http.Header header : headers) 
	        	{
	        		System.out.println("Key : " + header.getName() 
	        		      + " ,Value : " + header.getValue() +"value get: " );
	        	}
	        	 
	              //JSONObject response=new JSONObject(responseBody);

	              
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        }
	        
	    }
	 
	 
	
	 
	 public void onBtnClickCreateHomeWork(View V) throws ParseException, ParserConfigurationException, SAXException, IOException, JSONException
	 {
		  //Obtengo la fecha del DatePicker
		 int   day  = FechaEntrega.getDayOfMonth();
		 int   month= FechaEntrega.getMonth();
		 int   year = FechaEntrega.getYear();
		 int Hour = HoraEntrega.getCurrentHour();
		 int Minute = HoraEntrega.getCurrentMinute();
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(year, month, day, Hour, Minute);
		 String formatedDate = dfm.format(calendar.getTime());
		 
		 long unixtime = dfm.parse(formatedDate).getTime();  
	     unixtime=unixtime/1000L;
	     
	
	     
	     FechaE = Long.toString(unixtime);
	     
	     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     Calendar cal = Calendar.getInstance();
	     String formatedDate2  =dateFormat.format(cal.getTime());
	     long unixtime2 = dateFormat.parse(formatedDate2).getTime();
	     FechaCurrent = Long.toString(unixtime2/1000L);
	     System.out.println("Fecha Formatd Date: "+ formatedDate2);
	     System.out.println("UnixTime: "+ FechaCurrent);
	    	
	     postData();
	     InfoUser.getInstance().Update();
	     
	 	 AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(CreateHomeWork.this);
   	 	
		 
   	 	dlgAlert.setMessage("Se Creo Exitosamente la Tarea");
        dlgAlert.setTitle("Aviso");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
        new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) {

            }
        });
	     
	 }

}
