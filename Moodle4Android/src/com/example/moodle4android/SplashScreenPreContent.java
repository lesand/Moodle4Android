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
public class SplashScreenPreContent extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen_pre_content);
		
		getActionBar().hide();
		

	    Thread loading = new Thread() {
	        public void run() {
	            try {
	                sleep(5000);
	                Bundle b = null;
	                b = getIntent().getBundleExtra("courseInfo");
	                Intent main = new Intent(SplashScreenPreContent.this, CourseSections.class);
	                main.putExtra("courseInfo", b);
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
		getMenuInflater().inflate(R.menu.splash_screen_pre_content, menu);
		return true;
	}

}
