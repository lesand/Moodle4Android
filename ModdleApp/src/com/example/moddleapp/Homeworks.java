package com.example.moddleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import moodleObjects.HomeWork;
import moodleObjects.InfoUser;
import moodleObjects.itemCourse;
import moodleObjects.weekObject;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Homeworks extends Activity {

	TableLayout TableHomeWorks;
	ArrayList<Integer> homeworkstodelete;
	LinearLayout homework;
	SimpleDateFormat dfm;
	View CurrentRow;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homeworks);
		ActionBar bar = getActionBar();
		bar.setTitle(" Tareas");
		bar.setIcon(getResources().getDrawable(R.drawable.tareas));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		//TableHomeWorks = (TableLayout)findViewById(R.id.TableHomeWorks);
		homework = (LinearLayout)findViewById(R.id.linearHomeWorks);
		homeworkstodelete = new ArrayList<Integer>();
		 dfm = new SimpleDateFormat("yyyyMMddHHmm");
		
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.drawable.menubarteachers, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addactivitytbtn:
				 				Intent intent = new Intent(this, CreateHomeWork.class);
				 				startActivity(intent);
								break;
			case R.id.deleteactivitytbtn:
								if(homeworkstodelete.size() == 0)
								{
									AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Homeworks.this);
									dlgAlert.setMessage("No hay tareas seleccionadas");
							        dlgAlert.setTitle("Aviso");
							        dlgAlert.setPositiveButton("OK", null);
							        dlgAlert.setCancelable(true);
							        dlgAlert.create().show();

							        dlgAlert.setPositiveButton("Ok",
							        new DialogInterface.OnClickListener() {
							        	public void onClick(DialogInterface dialog, int which) {

							            }
							        });
								     
								}else
								{
									DeleteTareas();
									try {
										InfoUser.getInstance().Update();
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
									homework.removeAllViews();
									try {
										fillTareas();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Homeworks.this);
									dlgAlert.setMessage("Se Borro Exitosamente la Data Seleccionada");
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
								break;
								
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		homework.removeAllViews();
		try {
			fillTareas();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressLint("NewApi")
	public void fillTareas() throws JSONException
	{
		
		ArrayList<itemCourse> CurrentHomeWorkList = InfoUser.getInstance().listWeeks.get(InfoUser.getInstance().currentWeek).tarea;

		if(CurrentHomeWorkList == null)
		{			
			TextView Label1 = new TextView(this);
			Label1.setText("NO HAY TAREAS QUE MOSTRAR");
			Label1.setGravity(Gravity.CENTER);
			Label1.setTextSize(20);
			Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			homework.addView(Label1);
			return ;
		}
						
		//Seteamos Contenido
		for(int i=0; i<CurrentHomeWorkList.size(); i++)
		{
			String Url = "http://54.218.122.112/proxy/index.php/api/assign/id/" + CurrentHomeWorkList.get(i).getIDItemCourse()+"/format/json";
			String json = readJSONFeed(Url);
			JSONObject jObject = new JSONObject(json);
			String   Date = jObject.getString("duedate");
			long unix_time = Long.parseLong(Date); 
			Date date = new Date(0);
			date.setTime((long)unix_time*1000);
			String algo = FormatFecha(unix_time);
			AgregarVarios("  Tarea: " + CurrentHomeWorkList.get(i).getName(), "   Fecha Entrega: " + algo, "   Fecha: No hay Fecha Indicada", "   Calificacion: -", i);
			
		}
	}
	
	public String FormatFecha(long timestamp)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp*1000);
		SimpleDateFormat month_date = new SimpleDateFormat("MMMMMMMMM");
		String month_name = month_date.format(cal.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String day_name = sdf.format(cal.getTime());
		String Year = Integer.toString(cal.get(Calendar.YEAR));
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		String Hour = sdf2.format(cal.getTime());
		String Day =Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		
		return  day_name + ", " + Day + " de " + month_name + " de " + Year +", " + Hour ;
 	}
	
	public  void AgregarVarios(String Cadena1, String Cadena2, String Cadena3, String Cadena4, int idLayout)
	{
		LinearLayout Temporal2 = new LinearLayout(this);
		Temporal2.setOrientation(LinearLayout.HORIZONTAL);
		CheckBox ch = new CheckBox(this);
		ch.setOnClickListener(clickcheckListener);
		ch.setId(idLayout);
		LinearLayout Temporal = new LinearLayout(this);
		Temporal.setId(idLayout);
		Temporal.setOrientation(LinearLayout.VERTICAL);
		Temporal.setBackground(getResources().getDrawable(R.drawable.backgroundwithshadow));
		TextView NombreTarea = new TextView(this);
		TextView FechaEd = new TextView(this);
		TextView FechaFin = new TextView(this);
		TextView Calificacion = new TextView(this);
		FechaEd.setTextSize(9);
		FechaFin.setTextSize(9);
		Calificacion.setTextSize(9);
		NombreTarea.setPaintFlags(NombreTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		NombreTarea.setText(Cadena1);
		FechaEd.setText(Cadena2);
		FechaFin.setText(Cadena3);
		Calificacion.setText(Cadena4);
		Temporal2.addView(NombreTarea);
		Temporal2.addView(ch);
		Temporal.addView(Temporal2);
		Temporal.addView(FechaEd);
		Temporal.addView(FechaFin);
		Temporal.addView(Calificacion);
		Temporal.setOnClickListener(clickRowListener);
		homework.addView(Temporal);
		int left = 2;
		int top = 20;
		int right = 2;
		int bottom = 2;
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, right, bottom);
		Temporal.setLayoutParams(params);
	}
	
	public void DeleteTareas()
	{
		for(int i=0; i<homeworkstodelete.size(); i++)
		{
			itemCourse temp =InfoUser.getInstance().listWeeks.get(InfoUser.getInstance().currentWeek).tarea.get(homeworkstodelete.get(i));
			 HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://54.218.122.112/proxy/index.php/api/assigns/");
		        try {
		            // Add your data
		            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            nameValuePairs.add(new BasicNameValuePair("action", "deleteAssign"));
		            nameValuePairs.add(new BasicNameValuePair("courseId", InfoUser.getInstance().CurrentCourse.getCourseIDNumber()));                        
		            //for the course content
		            nameValuePairs.add(new BasicNameValuePair("sectionID", InfoUser.getInstance().listWeeks.get(InfoUser.getInstance().currentWeek).idSeccion));            
		            nameValuePairs.add(new BasicNameValuePair("itemId", temp.getIDItemCourse()));
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
		        		      + " ,Value : " + header.getValue());
		        	}                        
		        } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		        }
			
		}
		
		
		
	}
	
	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			
			Intent intent = new Intent(getApplicationContext(), ShowHomeWork.class);
			startActivity(intent);
			
		}
		
	};
	
	OnClickListener clickcheckListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(homeworkstodelete.contains(arg0.getId()))
			{
				if(homeworkstodelete.size() == 1)
				{
					homeworkstodelete = new ArrayList<Integer>();
				}else
				{
					homeworkstodelete.remove(arg0.getId());
				}
				
			}else
			{
				homeworkstodelete.add(arg0.getId());
			}
			
		}
	};
	
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
