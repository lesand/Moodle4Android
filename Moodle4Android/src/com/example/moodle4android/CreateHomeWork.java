package com.example.moodle4android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import moodleObjects.infoCourse;



import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class CreateHomeWork extends Activity {

	EditText NombreTarea;
	EditText DescripcionTarea;
	DatePicker FechaEntrega;
	SimpleDateFormat dfm;
	TimePicker HoraEntrega;
	String FechaE;
	String FechaCurrent;
	infoCourse currentCourse;
	String SectionID;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_home_work);
		getActionBar().setTitle(" Crear Tarea");
		Bundle b = getIntent().getBundleExtra("homeworkInfo");
		if(b != null)
		{
			currentCourse = (infoCourse)b.getSerializable("currentCourse");
			SectionID = b.getString("SectionId");
			NombreTarea = (EditText)findViewById(R.id.editNameHomework);
			DescripcionTarea = (EditText)findViewById(R.id.editDescripcionTarea);
			FechaEntrega = (DatePicker)findViewById(R.id.dateFechaEntrega);
			dfm = new SimpleDateFormat("yyyyMMddHHmm");
			HoraEntrega = (TimePicker)findViewById(R.id.TimeHour);
		
		}else
		{
			finish();
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_home_work, menu);
		return true;
	}
	
	public void onBtnClickCreateHomeWork(View V)
	{
		try{
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
		}catch(Exception ex)
		{
			
		}
	}
	
	 public void postData()
	 {
		 
		 String Url = obtainUrl(currentCourse.getCurrentUser().getUrl()) + "proxy/index.php/api/assigns/";
		 HttpClient httpclient = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost(Url);
		 try 
		 {
			 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	         nameValuePairs.add(new BasicNameValuePair("action", "insertAssign"));
	         nameValuePairs.add(new BasicNameValuePair("courseId", currentCourse.getIdMoodleCourse()));
	         nameValuePairs.add(new BasicNameValuePair("sectionID", SectionID));
	         nameValuePairs.add(new BasicNameValuePair("dueDate", FechaE));
	         nameValuePairs.add(new BasicNameValuePair("timeAdded", FechaCurrent));
	         nameValuePairs.add(new BasicNameValuePair("name", NombreTarea.getText().toString()));
	         nameValuePairs.add(new BasicNameValuePair("intro", DescripcionTarea.getText().toString()));
	         nameValuePairs.add(new BasicNameValuePair("allowsubmissionsfromdate", FechaCurrent));
	         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	         StrictMode.setThreadPolicy(policy);
	         HttpResponse response = httpclient.execute(httppost);
	         org.apache.http.Header[] headers = response.getAllHeaders();
	         for (org.apache.http.Header header : headers) 
	         { 
	        		System.out.println("Key : " + header.getName() 
	        		      + " ,Value : " + header.getValue() +"value get: " );
	         }
			 
		 }catch(Exception ex)
		 {
			 
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
