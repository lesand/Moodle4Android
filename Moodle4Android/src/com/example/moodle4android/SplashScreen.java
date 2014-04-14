package com.example.moodle4android;

import moodleObjects.userInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ProgressBar;

@SuppressLint("NewApi")
public class SplashScreen extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		getActionBar().hide();
		ProgressBar progress = (ProgressBar)findViewById(R.id.progressBarAndroid);

	    Thread loading = new Thread() {
	        public void run() {
	            try {
	                sleep(5000);
	                Bundle b = null;
	                if(getIntent().getBundleExtra("userInfo") != null)
	                {
	                	b = getIntent().getBundleExtra("userInfo");
	                }else
	                {
	                	SharedPreferences settings = getSharedPreferences("Settings", 0);
	                	userInfo user = new userInfo(settings.getString("nameLoged", ""), settings.getString("passLoged", ""), settings.getString("IdMoodle", ""), settings.getString("IdMoodle", ""),settings.getString("ContextID", ""), settings.getString("Role", "") );
	            		b.putSerializable("userInfo", user);
	            			
	            		
	                }
	                	                
	                Intent main = new Intent(SplashScreen.this, Courses.class);
	                main.putExtra("userInfo", b);
	                startActivity(main);
	            }

	            catch (Exception e) {
	                e.printStackTrace();
	            }

	            finally {
	                finish();
	            }
	        }
	    };

	    loading.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
