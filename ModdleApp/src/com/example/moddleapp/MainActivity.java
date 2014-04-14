package com.example.moddleapp;





import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import moodleObjects.GoogleAuthentication;
import moodleObjects.InfoUser;
import moodleObjects.connecFunc;
import moodleObjects.enumObjects;
import moodleObjects.moodleObj;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar bar = getActionBar();
		bar.hide();
		bar.setTitle(" Autenticación");
		bar.setIcon(getResources().getDrawable(R.drawable.icologin));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		setContentView(R.layout.activity_main);		
	    getActionBar().show();
	    SharedPreferences settings = getSharedPreferences("Settings", 0);
	    boolean IsLoged = settings.getBoolean("isLoged", false);
	  
	    if(IsLoged)
	    {
	    	
	    	Log.d("Nombre", settings.getString("nameLoged", "zz"));
	    	Log.d("Nombre", settings.getString("passLoged", "zz"));
	    	SharedPreferences.Editor editor = settings.edit();
		   	Intent intent = new Intent(this, Showclases.class);
			startActivity(intent); 
			
	    }
	    
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void onBtnClicked(View V) throws MalformedURLException, XPathExpressionException, IOException, ParserConfigurationException, SAXException
	 {
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  StrictMode.setThreadPolicy(policy);
		 //GoogleAuthentication G;
		 // G = new GoogleAuthentication("lesand@unitec.edu", "LEONEL1991");
	
		  //boolean result = G.GetLoged();
		 
		 SharedPreferences settings = getSharedPreferences("Settings", 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("isLoged", true);
		 editor.putString("nameLoged", "Leonel");
		 editor.putString("passLoged", "Lesand");
		 editor.commit();
		 
		 
		 connecFunc.getInstance().tryConnect("userid=3","core_enrol_get_users_courses");
		 String str=connecFunc.getInstance().xml;
		 enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE,str);
		 obj.parseString();
		 ArrayList<moodleObj> list=new ArrayList<moodleObj>();
		 list=obj.giveMetheObject();
		 InfoUser.getInstance().userId = 4;
	     InfoUser.getInstance().listClasesInfo = list;
	     Intent intent = new Intent(this, Showclases.class);
		 startActivity(intent);
		 
	 }
	 
	 
	 
 
  

}
