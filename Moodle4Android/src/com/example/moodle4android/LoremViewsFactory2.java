package com.example.moodle4android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import moodleObjects.ListHomeWorks;
import moodleObjects.connecFunc;
import moodleObjects.course;
import moodleObjects.courseContentM;
import moodleObjects.enumObjects;
import moodleObjects.infoCourse;
import moodleObjects.infoHomeWork;
import moodleObjects.itemCourse;
import moodleObjects.moodleObj;
import moodleObjects.sectionCourse;
import moodleObjects.userInfo;
import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class LoremViewsFactory2 implements RemoteViewsService.RemoteViewsFactory {
  private static final String[] items={"lorem", "ipsum", "dolor",
                                        "sit", "amet", "consectetuer",
                                        "adipiscing", "elit", "morbi",
                                        "vel", "ligula", "vitae",
                                        "arcu", "aliquet", "mollis",
                                        "etiam", "vel", "erat",
                                        "placerat", "ante",
                                        "porttitor", "sodales",
                                        "pellentesque", "augue",
                                        "purus"};
  private Context ctxt=null;
  ArrayList<infoCourse> ListOfCourses;
  private int appWidgetId;
  boolean IsLoged;
  userInfo user;
  Hashtable<infoHomeWork,infoCourse> contenedor;
  ArrayList<infoHomeWork> ListHomeWorks;

  public LoremViewsFactory2(Context ctxt, Intent intent) {
      this.ctxt=ctxt;
      appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                      AppWidgetManager.INVALID_APPWIDGET_ID);
      
      SharedPreferences settings = ctxt.getSharedPreferences("Settings",0);
      contenedor=new Hashtable<infoHomeWork,infoCourse>();
      ListHomeWorks = new ArrayList<infoHomeWork>();
      IsLoged = settings.getBoolean("isLoged", false);
      if(IsLoged)
	  {
			 user = new userInfo(settings.getString("nameLoged", ""), settings.getString("passLoged", ""), settings.getString("IdMoodle", ""),settings.getString("url", ""), settings.getString("ContextID", ""), settings.getString("Role", ""));
			 fillItems();
	  }else
	  {
		  ListOfCourses = new ArrayList<infoCourse>();
		  ListOfCourses.add(new infoCourse("-1", "Sin Conexion", "No ha iniciado Sesión", null));
	  }
  }
  
  public void fillItems()
  {
	  try
		{
			connecFunc.getInstance().tryConnect("userid="+user.getIdMoodle(),"core_enrol_get_users_courses", user.getUrl());
			String str=connecFunc.getInstance().xml;
			enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE,str);
			obj.parseString();
			ArrayList<moodleObj> listClases=new ArrayList<moodleObj>();
			listClases=obj.giveMetheObject();
			ListOfCourses = new ArrayList<infoCourse>();
			for(int i=0; i<listClases.size(); i++)
			{
				course tempCourse = (course)listClases.get(i);
				infoCourse courseCurrent= new infoCourse(tempCourse.getCourseIDNumber(), tempCourse.getCourseShortName(), tempCourse.getCourseFullName(), user);
				ListOfCourses.add(courseCurrent);
				FillHash(courseCurrent);
			}
			
		}
		catch(Exception ex)
		{
			
		}
  }
  
  public void FillHash(infoCourse temppCourse)
  {
	    try{
		  	connecFunc.getInstance().tryConnect("courseid="+temppCourse.getIdMoodleCourse(),"core_course_get_contents", user.getUrl());
			String str=connecFunc.getInstance().xml;
			enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE_CONTENT,str);
			obj.parseString();
			ArrayList<moodleObj> list=new ArrayList<moodleObj>();
			list=obj.giveMetheObject();
			courseContentM global = (courseContentM) list.get(0);
			for(int i=0; i<global.getSections().size(); i++)
			{
				sectionCourse currentSection = global.getSections().get(i);
				for(int e=0; e<currentSection.getItems().size(); e++)
				{
					itemCourse currentItem = currentSection.getItems().get(e);
					if(currentItem.getItemType().compareTo("assign")==0)
					{
						infoHomeWork tempHomeWork = new infoHomeWork(currentItem.getName(), currentItem.getIDItemCourse(), currentSection.getSectionId(), currentItem.getLabel());
						String URlBegin = user.getUrl();
						URlBegin = obtainUrl(URlBegin);
						String Url = URlBegin+"proxy/index.php/api/assign/id/" + tempHomeWork.getidItemCourse()+"/format/json";
						String json = readJSONFeed(Url);
						JSONObject jObject = new JSONObject(json);
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
						tempHomeWork.setDateInit(FechaCreacion);
						tempHomeWork.setDateEnd(FechaEntrega);
						tempHomeWork.setDescription(Descripcion);
						ListHomeWorks.add(tempHomeWork);
						contenedor.put(tempHomeWork, temppCourse);
					}
				}
			
			}
	    }catch(Exception ex)
	    {
	    	
	    }
	  
  }
	
  
  @Override
  public void onCreate() {
    // no-op
  }
  
  @Override
  public void onDestroy() {
    // no-op
  }

  @Override
  public int getCount() {
    return(ListHomeWorks.size());
  }

  @SuppressLint("NewApi")
@Override
  public RemoteViews getViewAt(int position) {
	  
	if(IsLoged)
	{
		RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.row2);
		
		infoHomeWork tempHomeWork = ListHomeWorks.get(position);
		infoCourse currentCourse = contenedor.get(tempHomeWork);
		row.setTextViewText(R.id.textwidget2, "Nombre: " + tempHomeWork.getName() );
		Intent i=new Intent();
		Bundle extras=new Bundle();
		extras.putSerializable("infoHomework", tempHomeWork);
		extras.putSerializable("infoCourse",currentCourse);
		i.putExtra("infoHomework", extras);
		row.setOnClickFillInIntent(R.id.textwidget2, i);
		return(row);
		
	}else
	{
		RemoteViews row=new RemoteViews(ctxt.getPackageName(),
		                R.layout.row2);
		
		row.setTextViewText(R.id.textwidget2, ListOfCourses.get(position).getNameLargeCourse());
		
		Intent i=new Intent();
		Bundle extras=new Bundle();
		
		extras.putString(WidgetProvider2.EXTRA_WORD,  ListOfCourses.get(position).getNameLargeCourse());
		i.putExtras(extras);
		row.setOnClickFillInIntent(R.id.textwidget2, i);

		return(row);
	}
    
  }

  @Override
  public RemoteViews getLoadingView() {
    return(null);
  }
  
  @Override
  public int getViewTypeCount() {
    return(1);
  }

  @Override
  public long getItemId(int position) {
    return(position);
  }

  @Override
  public boolean hasStableIds() {
    return(true);
  }

  @Override
  public void onDataSetChanged() {
    // no-op
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
}
