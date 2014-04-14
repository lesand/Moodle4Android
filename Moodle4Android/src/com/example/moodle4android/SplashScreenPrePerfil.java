package com.example.moodle4android;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

@SuppressLint("NewApi")
public class SplashScreenPrePerfil extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen_pre_perfil);
		getActionBar().hide();
		

	    Thread loading = new Thread() {
	        public void run() {
	            try {
	                sleep(5000);
	                Bundle b = null;
	                b = getIntent().getBundleExtra("listparticipantsInfo");
	                Intent main = new Intent(SplashScreenPrePerfil.this, Perfil.class);
	                main.putExtra("listparticipantsInfo", b);
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
		getMenuInflater().inflate(R.menu.splash_screen_pre_perfil, menu);
		return true;
	}

}
