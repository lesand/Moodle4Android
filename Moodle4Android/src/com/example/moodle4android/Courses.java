package com.example.moodle4android;



import java.util.ArrayList;







import moodleObjects.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Courses extends Activity {

	LinearLayout linearCourses;
	ArrayList<infoCourse> ListOfCourses;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses);
		getActionBar().setTitle("Clases");
		
		Bundle b = getIntent().getBundleExtra("userInfo");
		if(b != null)
		{
			userInfo user = (userInfo)b.getSerializable("userInfo");
			SharedPreferences settings = getSharedPreferences("Settings", 0);
		    boolean IsLoged = settings.getBoolean("isLoged", false);
			if(IsLoged)
			{
				try
				{
					connecFunc.getInstance().tryConnect("userid="+user.getIdMoodle(),"core_enrol_get_users_courses", user.getUrl());
					String str=connecFunc.getInstance().xml;
					enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE,str);
					obj.parseString();
					ArrayList<moodleObj> listClases=new ArrayList<moodleObj>();
					listClases=obj.giveMetheObject();
					linearCourses = (LinearLayout)findViewById(R.id.linearCourses);
					ListOfCourses = new ArrayList<infoCourse>();
					renderCourses(listClases, user);
				}
				catch(Exception ex)
				{
					AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Courses.this);
			   	 	
					 
			   	 	dlgAlert.setMessage("En estos momentos no hay conexion a internet");
			        dlgAlert.setTitle("AVISO");
			        dlgAlert.setPositiveButton("OK", null);
			        dlgAlert.setCancelable(true);
			        dlgAlert.create().show();

			        dlgAlert.setPositiveButton("Ok",
			        new DialogInterface.OnClickListener() {
			        	public void onClick(DialogInterface dialog, int which) {
			        		
			            }
			        });
			        
			        
				}
				
				
			}else
			{
				finish();
			}
			
		}else
		{
			finish();
		}
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
				 				Intent intent = new Intent(this, Configuracion.class);
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
	
	
	public  void renderCourses(ArrayList<moodleObj> listClases, userInfo user)
	{
		for(int i=0; i<listClases.size(); i++)
		{
			
			course tempCourse = (course)listClases.get(i);			
			LinearLayout layoutHorizontal = new LinearLayout(this);
			layoutHorizontal.setOnClickListener(ClickLayoutListener);
			layoutHorizontal.setOrientation(LinearLayout.VERTICAL);
			layoutHorizontal.setId(i);
			layoutHorizontal.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
			layoutHorizontal.setGravity(Gravity.CENTER);
			linearCourses.addView(layoutHorizontal);
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 170);
			layoutHorizontal.setLayoutParams(params);			
			TextView TextName = new TextView(this);
			TextName.setText("\n" + tempCourse.getCourseFullName()+ "\n");
			TextName.setTextSize(20);
			layoutHorizontal.addView(TextName);
			ListOfCourses.add(new infoCourse(tempCourse.getCourseIDNumber(), tempCourse.getCourseShortName(), tempCourse.getCourseFullName(), user));
		}
	}
	
	
	OnClickListener ClickLayoutListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			infoCourse currentCourse = ListOfCourses.get(arg0.getId());
			Bundle b = new Bundle();
			b.putSerializable("courseInfo", currentCourse);
			Intent main = new Intent(Courses.this, SplashScreenPreContent.class);
            main.putExtra("courseInfo", b);
            startActivity(main);
				
		}
	};

}
