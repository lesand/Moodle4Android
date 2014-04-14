package com.example.moddleapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import moodleObjects.InfoUser;
import moodleObjects.connecFunc;
import moodleObjects.course;
import moodleObjects.enumObjects;
import moodleObjects.moodleObj;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

@SuppressLint("NewApi")
public class Showclases extends Activity {

	ListView listClasses;
	boolean impause = false;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showclases);
		ActionBar bar = getActionBar();
		bar.setTitle(" Clases");
		bar.setIcon(getResources().getDrawable(R.drawable.icoclass));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		
		SharedPreferences settings = getSharedPreferences("Settings", 0);
	    boolean IsLoged = settings.getBoolean("isLoged", false);
	    
	    if(!IsLoged)
	    {
	    	finish();
	    }else
	    {
	    	try {
				connecFunc.getInstance().tryConnect("userid=3","core_enrol_get_users_courses");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 String str=connecFunc.getInstance().xml;
			 enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE,str);
			 try {
				obj.parseString();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			 ArrayList<moodleObj> list=new ArrayList<moodleObj>();
			 list=obj.giveMetheObject();
			 InfoUser.getInstance().userId = 4;
		     InfoUser.getInstance().listClasesInfo = list;
	    }
		
		//En Esta parte hay que hacer que se seteen las clases
		
		listClasses = (ListView)findViewById(R.id.listofclass);
		ArrayList<String> listItems=new ArrayList<String>();
	
		ArrayList<moodleObj> ListCourses=InfoUser.getInstance().listClasesInfo;
		for(moodleObj objeto :ListCourses)
	    {
	    		course Current=(course) objeto;
	    		listItems.add(Current.getCourseFullName());
	    		
	    }
		
		ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		listClasses.setAdapter(myarrayAdapter);
		listClasses.setTextFilterEnabled(true);		
		listClasses.setOnItemClickListener(new OnItemClickListener() {
	    
	    	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    	       
	    		    InfoUser.getInstance().CurrentCourse = (course)InfoUser.getInstance().listClasesInfo.get(arg2);
	    	    	Intent intent = new Intent(getApplicationContext(), ContentClass.class );
	    	    	startActivity(intent);    	    	
	    	        
	    	}

	    });
		
	       
		
		
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.drawable.menubaredit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.editbtn:
				 				Intent intent = new Intent(this, ConfigActivtiy.class);
				 				startActivity(intent);
								break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	@Override
	protected void onResume() {
		SharedPreferences settings = getSharedPreferences("Settings", 0);
	    boolean IsLoged = settings.getBoolean("isLoged", false);
	    
	    if(!IsLoged)
	    {
	    	finish();
	    }
		super.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
				moveTaskToBack(true);
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
