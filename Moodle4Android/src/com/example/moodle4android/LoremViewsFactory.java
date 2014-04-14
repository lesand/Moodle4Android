package com.example.moodle4android;

import java.util.ArrayList;

import moodleObjects.connecFunc;
import moodleObjects.course;
import moodleObjects.enumObjects;
import moodleObjects.infoCourse;
import moodleObjects.moodleObj;
import moodleObjects.userInfo;
import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class LoremViewsFactory implements RemoteViewsService.RemoteViewsFactory {
  private static final String[] items={"SISTEMAS OPERATIVOS II", "Intro a la Computacion"};
  private Context ctxt=null;
  ArrayList<infoCourse> ListOfCourses;
  private int appWidgetId;
  boolean IsLoged;
  userInfo user;

  public LoremViewsFactory(Context ctxt, Intent intent) {
      this.ctxt=ctxt;
      appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                      AppWidgetManager.INVALID_APPWIDGET_ID);
      
      SharedPreferences settings = ctxt.getSharedPreferences("Settings",0);
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
				ListOfCourses.add(new infoCourse(tempCourse.getCourseIDNumber(), tempCourse.getCourseShortName(), tempCourse.getCourseFullName(), user));
			}
			
		}
		catch(Exception ex)
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
    return(ListOfCourses.size());
  }

  @SuppressLint("NewApi")
@Override
  public RemoteViews getViewAt(int position) {
    RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                                     R.layout.row);
    
    row.setTextViewText(android.R.id.text1, ListOfCourses.get(position).getNameLargeCourse());

    Intent i=new Intent();
    Bundle extras=new Bundle();
    
    if(!IsLoged)
    {
    	extras.putString(WidgetProvider.EXTRA_WORD, ListOfCourses.get(position).getNameLargeCourse());
    	i.putExtras(extras);
    }else
    {
    	infoCourse currentCourse = ListOfCourses.get(position);
    	extras.putSerializable("courseInfo", currentCourse);
    	i.putExtra("courseInfo", extras);
    }
    row.setOnClickFillInIntent(android.R.id.text1, i);

    return(row);
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
}
