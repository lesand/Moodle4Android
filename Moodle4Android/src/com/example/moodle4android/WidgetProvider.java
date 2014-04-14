package com.example.moodle4android;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class WidgetProvider extends AppWidgetProvider {
  public static String EXTRA_WORD=
    "com.example.gteswidget.WORD";

  @SuppressWarnings("deprecation")
@SuppressLint("NewApi")
@Override
  public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,
                        int[] appWidgetIds) {
    for (int i=0; i<appWidgetIds.length; i++) {
      Intent svcIntent=new Intent(ctxt, WidgetService.class);
      Intent clickIntent;
      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
      
      RemoteViews widget=new RemoteViews(ctxt.getPackageName(),
                                          R.layout.widget);
      
      
      
      widget.setRemoteAdapter(appWidgetIds[i], R.id.words,
                              svcIntent);
      

      SharedPreferences settings = ctxt.getSharedPreferences("Settings",0);
      boolean IsLoged = settings.getBoolean("isLoged", false);
      
      
      
      if(!IsLoged)
      {
    	  widget.setTextViewText(R.id.TextNameUser, "Sin Conexión");
    	  clickIntent=new Intent(ctxt, LoremActivity.class);
    	  PendingIntent clickPI=PendingIntent
                              .getActivity(ctxt, 0,
                                            clickIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
    	  
    	  widget.setPendingIntentTemplate(R.id.words, clickPI);

          appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
      }else
      {
    	  widget.setTextViewText(R.id.TextNameUser, settings.getString("nameLoged", ""));
    	  clickIntent=new Intent(ctxt, SplashScreenPreContent.class);
    	  PendingIntent clickPI=PendingIntent
                              .getActivity(ctxt, 0,
                                            clickIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
    	  
    	  widget.setPendingIntentTemplate(R.id.words, clickPI);

          appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
    	  
      }
      
     
    }
    
    super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
  }
}
