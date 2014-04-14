package com.example.moodle4android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;







import moodleObjects.*;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeWorks extends Activity {

	LinearLayout LinearHomeWorks; 
	ArrayList<Integer> homeworkstodelete;
	ListHomeWorks homeworks;
	int LastRenderHomeWork;
	int sectionid;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_works);
		
		ActionBar bar = getActionBar();
		bar.setTitle(" Tareas");
		Bundle b = getIntent().getBundleExtra("homeworkInfo");
		if(b != null)
		{
			homeworks = (ListHomeWorks)b.getSerializable("homeworkInfo");
			sectionid = b.getInt("sectionId");
			LinearHomeWorks  = (LinearLayout)(findViewById(R.id.linearHomeWorks));
			homeworkstodelete = new ArrayList<Integer>();
			LastRenderHomeWork = -1;
			try
			{
				RenderHomeWorks(homeworks, sectionid);
				
			}catch(Exception ex)
			{
				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(HomeWorks.this);
		   	 	dlgAlert.setMessage("En estos momentos no hay conexion a internet o surgio un problema con la plataforma");
		        dlgAlert.setTitle("AVISO");
		        dlgAlert.setPositiveButton("OK", null);
		        dlgAlert.setCancelable(true);
		        dlgAlert.create().show();

		        dlgAlert.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int which) {
		        		HomeWorks.this.finish();
		            }
		        });
			}
			
		}else
		{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		if(homeworks.getCourse().getCurrentUser().getIdRole().compareTo("teacher")==0)
		{
			getMenuInflater().inflate(R.drawable.menubarteachers, menu);
			return true;
			
		}else
		{
			getMenuInflater().inflate(R.menu.home_works, menu);
			return true;
		}
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addactivitytbtn:
				if(sectionid == -1)
				{
					AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(HomeWorks.this);
					dlgAlert.setMessage("Tiene que ir a una Sección especifica para crear una tarea");
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
					Bundle b = new Bundle();
					b.putSerializable("currentCourse", homeworks.getCourse());
					b.putString("SectionId", Integer.toString(sectionid));
					Intent main = new Intent(HomeWorks.this, CreateHomeWork.class);
					main.putExtra("homeworkInfo", b);
					startActivity(main);
				}
				
				break;
			case R.id.deleteactivitytbtn:
				if(homeworkstodelete.size() == 0)
				{
					AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(HomeWorks.this);
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
					final ProgressDialog dialog = ProgressDialog.show(HomeWorks.this, "", "Eliminando Tareas...", true);
					new Thread(new Runnable() {
						  @Override
						  public void run()
						  {
							   
								

						    HomeWorks.this.runOnUiThread(new Runnable() {
						      @Override
						      public void run()
						      {
						    	  DeleteTareas();
						    	  LinearHomeWorks.removeAllViews();
								  RenderHomeWorks(homeworks, sectionid);
						    	  dialog.dismiss();
						      }
						    });
						  }
						}).start();
						
					
					
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public void DeleteTareas()
	{
		for(int i=0; i<homeworkstodelete.size(); i++)
		{
			infoHomeWork homeworkToDelete = homeworks.getElement(homeworkstodelete.get(i));
			String Url = obtainUrl(homeworks.getCourse().getCurrentUser().getUrl()) + "proxy/index.php/api/assigns/";
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(Url);
	        try
	        {
	        	  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		          nameValuePairs.add(new BasicNameValuePair("action", "deleteAssign"));
		          nameValuePairs.add(new BasicNameValuePair("courseId", homeworks.getCourse().getIdMoodleCourse()));
		          nameValuePairs.add(new BasicNameValuePair("sectionID", homeworkToDelete.getidSection()));
		          nameValuePairs.add(new BasicNameValuePair("itemId", homeworkToDelete.getidItemCourse()));
		          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		          StrictMode.setThreadPolicy(policy); 
		          HttpResponse response = httpclient.execute(httppost);
		          org.apache.http.Header[] headers = response.getAllHeaders();
		          for (org.apache.http.Header header : headers) 
		          {
		        		System.out.println("Key : " + header.getName() 
		        		      + " ,Value : " + header.getValue());
		          }                        
		          homeworks.deleteElement(homeworkstodelete.get(i));
	        	
	        }catch (Exception ex)
	        {
	        	continue;
	        }
		}
	}
	
	@Override
	protected void onResume() {
		if(homeworks.getCourse().getCurrentUser().getIdRole().compareTo("teacher")==0)
		{
			String url = obtainUrl(homeworks.getCourse().getCurrentUser().getUrl());
			url = url + "proxy/index.php/api/lastassign/idCourse/"+3+"/format/json/";
			try
			{
				String json = readJSONFeed(url);
				JSONObject jObject = new JSONObject(json);
				int tempid = jObject.getInt("id");
				if(tempid == LastRenderHomeWork)
				{
					super.onResume();
					
				}else
				{
					String NameTarea = jObject.getString("name");
					String FechaEntrega = jObject.getString("duedate");
					String FechaCreacion = jObject.getString("allowsubmissionsfromdate");
					String Descripcion = jObject.getString("intro");
					long unix_timeFechaEntrega = Long.parseLong(FechaEntrega); 
					Date date = new Date(0);
					date.setTime((long)unix_timeFechaEntrega*1000);
					FechaEntrega = FormatFecha(unix_timeFechaEntrega);
					long unix_timeFechaCreacion = Long.parseLong(FechaCreacion);
					date.setTime((long)unix_timeFechaCreacion*1000);
					FechaCreacion = FormatFecha(unix_timeFechaCreacion);
					infoHomeWork temp = new infoHomeWork(NameTarea,"-1",Integer.toString(sectionid),Descripcion);
					temp.setDateEnd(FechaEntrega);
					temp.setDateInit(FechaCreacion);
					homeworks.addElement(temp);
					RenderTarea(temp);
					LastRenderHomeWork = tempid;
				}
				
				
			}catch(Exception ex)
			{
				super.onResume();
			}
		}
		super.onResume();
		
	};
	
	public void RenderTarea(infoHomeWork Lasthomework)
	{
		if(homeworks.getCourse().getCurrentUser().getIdRole().compareTo("teacher")==0)
		{
			LinearLayout Temporal2 = new LinearLayout(this);
			Temporal2.setOrientation(LinearLayout.HORIZONTAL);
			CheckBox ch = new CheckBox(this);
			ch.setOnClickListener(clickcheckListener);
			ch.setId(homeworks.SizeList()-1);
			LinearLayout Temporal = new LinearLayout(this);
			Temporal.setId(homeworks.SizeList()-1);
			Temporal.setOrientation(LinearLayout.VERTICAL);
			Temporal.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout4));
			TextView NombreTarea = new TextView(this);
			TextView FechaEd = new TextView(this);
			TextView FechaFin = new TextView(this);
			TextView Calificacion = new TextView(this);
			FechaEd.setTextSize(9);
			FechaFin.setTextSize(9);
			Calificacion.setTextSize(9);
			NombreTarea.setPaintFlags(NombreTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			NombreTarea.setText("   Nombre: " + Lasthomework.getName());
			FechaEd.setText("   Fecha Creacion: " + Lasthomework.getDateInit());
			FechaFin.setText("   Fecha Entrega: " + Lasthomework.getDateEnd());
			Calificacion.setText("   Calificacion: -");
			Temporal2.addView(NombreTarea);
			Temporal2.addView(ch);
			Temporal.addView(Temporal2);
			Temporal.addView(FechaEd);
			Temporal.addView(FechaFin);
			Temporal.addView(Calificacion);
			Temporal.setOnClickListener(ClickViewHomeworkListening);
			LinearHomeWorks.addView(Temporal);
			int left = 2;
			int top = 20;
			int right = 2;
			int bottom = 2;
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(left, top, right, bottom);
			Temporal.setLayoutParams(params);
			
		}else
		{
			LinearLayout LayoutVertical = new LinearLayout(this);
			LayoutVertical.setOrientation(LinearLayout.VERTICAL);
			LayoutVertical.setId(homeworks.SizeList()-1);
			LayoutVertical.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout4));
			LayoutVertical.setOnClickListener(ClickViewHomeworkListening);
			TextView NombreTarea = new TextView(this);
			TextView FechaEd = new TextView(this);
			TextView FechaFin = new TextView(this);
			TextView Calificacion = new TextView(this);
			FechaEd.setTextSize(9);
			FechaFin.setTextSize(9);
			Calificacion.setTextSize(9);
			NombreTarea.setPaintFlags(NombreTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			NombreTarea.setText("   Nombre: " + Lasthomework.getName());
			FechaEd.setText("   Fecha Creacion: " + Lasthomework.getDateInit());
			FechaFin.setText("   Fecha Entrega: " + Lasthomework.getDateEnd());
			Calificacion.setText("   Calificacion: -");
			LayoutVertical.addView(NombreTarea);
			LayoutVertical.addView(FechaEd);
			LayoutVertical.addView(FechaFin);
			LayoutVertical.addView(Calificacion);
			LinearHomeWorks.addView(LayoutVertical);
			int left = 2;
			int top = 20;
			int right = 2;
			int bottom = 2;
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(left, top, right, bottom);
			LayoutVertical.setLayoutParams(params);
			
		}
		
	}
	
	
	@SuppressLint("NewApi")
	public void RenderHomeWorks(ListHomeWorks homeworks, int sectionid)
	{
		if(homeworks.SizeList() == 0)
		{
			TextView Message = new TextView(this);
			Message.setPaintFlags(Message.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			Message.setText("  No hay Tareas que mostrar");
			Message.setGravity(Gravity.CENTER);
			LinearHomeWorks.addView(Message);
		}
		
		try
		{
			for(int i=0; i<homeworks.SizeList(); i++)
			{
				infoHomeWork currentHomeWork = homeworks.getElement(i);
				if(sectionid != -1)
				{
					if(currentHomeWork.getidSection().compareTo(Integer.toString(sectionid))!=0)
					{
						continue;
						
					}
				}
				
				//ObtenemosURL
				String URlBegin = homeworks.getCourse().getCurrentUser().getUrl();
				URlBegin = obtainUrl(URlBegin);
				String Url = URlBegin+"proxy/index.php/api/assign/id/" + currentHomeWork.getidItemCourse()+"/format/json";
				String json = readJSONFeed(Url);
				JSONObject jObject = new JSONObject(json);
				String FechaEntrega = jObject.getString("duedate");
				String FechaCreacion = jObject.getString("allowsubmissionsfromdate");
				String Descripcion = jObject.getString("intro");
				int tempid = jObject.getInt("id");
				currentHomeWork.setDescription(Descripcion);
				long unix_timeFechaEntrega = Long.parseLong(FechaEntrega); 
				Date date = new Date(0);
				date.setTime((long)unix_timeFechaEntrega*1000);
				FechaEntrega = FormatFecha(unix_timeFechaEntrega);
				long unix_timeFechaCreacion = Long.parseLong(FechaCreacion);
				date.setTime((long)unix_timeFechaCreacion*1000);
				FechaCreacion = FormatFecha(unix_timeFechaCreacion);
				System.out.println("El ID DE ESA FOCKING TAREA ES " + tempid);
				if(homeworks.getCourse().getCurrentUser().getIdRole().compareTo("teacher")==0)
				{
					LinearLayout Temporal2 = new LinearLayout(this);
					Temporal2.setOrientation(LinearLayout.HORIZONTAL);
					CheckBox ch = new CheckBox(this);
					ch.setOnClickListener(clickcheckListener);
					ch.setId(i);
					LinearLayout Temporal = new LinearLayout(this);
					Temporal.setId(i);
					Temporal.setOrientation(LinearLayout.VERTICAL);
					Temporal.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout4));
					TextView NombreTarea = new TextView(this);
					TextView FechaEd = new TextView(this);
					TextView FechaFin = new TextView(this);
					TextView Calificacion = new TextView(this);
					FechaEd.setTextSize(9);
					FechaFin.setTextSize(9);
					Calificacion.setTextSize(9);
					NombreTarea.setPaintFlags(NombreTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
					NombreTarea.setText("   Nombre: " + currentHomeWork.getName());
					FechaEd.setText("   Fecha Creacion: " + FechaCreacion);
					FechaFin.setText("   Fecha Entrega: " + FechaEntrega);
					Calificacion.setText("   Calificacion: -");
					Temporal2.addView(NombreTarea);
					Temporal2.addView(ch);
					Temporal.addView(Temporal2);
					Temporal.addView(FechaEd);
					Temporal.addView(FechaFin);
					Temporal.addView(Calificacion);
					Temporal.setOnClickListener(ClickViewHomeworkListening);
					LinearHomeWorks.addView(Temporal);
					int left = 2;
					int top = 20;
					int right = 2;
					int bottom = 2;
					TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					params.setMargins(left, top, right, bottom);
					Temporal.setLayoutParams(params);
					currentHomeWork.setDateEnd(FechaEntrega);
					currentHomeWork.setDateInit(FechaCreacion);
					LastRenderHomeWork = tempid;
				}else
				{
					LinearLayout LayoutVertical = new LinearLayout(this);
					LayoutVertical.setOrientation(LinearLayout.VERTICAL);
					LayoutVertical.setId(i);
					LayoutVertical.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout4));
					LayoutVertical.setOnClickListener(ClickViewHomeworkListening);
					TextView NombreTarea = new TextView(this);
					TextView FechaEd = new TextView(this);
					TextView FechaFin = new TextView(this);
					TextView Calificacion = new TextView(this);
					FechaEd.setTextSize(9);
					FechaFin.setTextSize(9);
					Calificacion.setTextSize(9);
					NombreTarea.setPaintFlags(NombreTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
					NombreTarea.setText("   Nombre: " + currentHomeWork.getName());
					FechaEd.setText("   Fecha Creacion: " + FechaCreacion);
					FechaFin.setText("   Fecha Entrega: " + FechaEntrega);
					Calificacion.setText("   Calificacion: -");
					LayoutVertical.addView(NombreTarea);
					LayoutVertical.addView(FechaEd);
					LayoutVertical.addView(FechaFin);
					LayoutVertical.addView(Calificacion);
					LinearHomeWorks.addView(LayoutVertical);
					int left = 2;
					int top = 20;
					int right = 2;
					int bottom = 2;
					TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					params.setMargins(left, top, right, bottom);
					LayoutVertical.setLayoutParams(params);
					currentHomeWork.setDateEnd(FechaEntrega);
					currentHomeWork.setDateInit(FechaCreacion);
					LastRenderHomeWork = tempid;
				}
				
			}
			
		}
		catch(Exception Ex)
		{
			return;
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
	 
	 @SuppressLint("SimpleDateFormat")
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
	 
	 OnClickListener ClickViewHomeworkListening = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle b = new Bundle();
				b.putSerializable("infoHomework", homeworks.getElement(arg0.getId()));
				b.putSerializable("infoCourse", homeworks.getCourse());
				Intent main = new Intent(HomeWorks.this, SplashScreemPreContentHomework.class);
	            main.putExtra("infoHomework", b);
	            startActivity(main);
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

}
