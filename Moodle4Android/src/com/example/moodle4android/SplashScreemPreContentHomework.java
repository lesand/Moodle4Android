package com.example.moodle4android;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

@SuppressLint("NewApi")
public class SplashScreemPreContentHomework extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screem_pre_content_homework);
		
		getActionBar().hide();
		

	    Thread loading = new Thread() {
	        public void run() {
	            try {
	                sleep(5000);
	                Bundle b = null;
	                b = getIntent().getBundleExtra("infoHomework");
	                Intent main = new Intent(SplashScreemPreContentHomework.this, HomeworkContent.class);
	                main.putExtra("infoHomework", b);
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
		getMenuInflater().inflate(R.menu.splash_screem_pre_content_homework,
				menu);
		return true;
	}

}
