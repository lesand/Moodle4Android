package com.example.moodle4android;



import moodleObjects.userInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setTitle("Autenticación");
		SharedPreferences settings = getSharedPreferences("Settings", 0);
		boolean IsLoged = settings.getBoolean("isLoged", false);
		
		if(IsLoged)
		{
			 userInfo user = new userInfo(settings.getString("nameLoged", ""), settings.getString("passLoged", ""), settings.getString("IdMoodle", ""),settings.getString("url", ""), settings.getString("ContextID", ""), settings.getString("Role", ""));
			 Intent main = new Intent(LoginActivity.this, SplashScreen.class);
			 Bundle bundleUser = new Bundle();
			 bundleUser.putSerializable("userInfo", user);
			 main.putExtra("userInfo", bundleUser);
			 startActivity(main); 
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	public void OnClickBtnLogin(View V)
	{
		 SharedPreferences settings = getSharedPreferences("Settings", 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("isLoged", true);
		 editor.putString("nameLoged", "Leonel Sánchez");
		 editor.putString("passLoged", "Lesand");
		 editor.putString("IdMoodle", "3");
		 editor.putString("url", "http://www.barcampsps.com/moodle/");
		 editor.putString("ContextID", "101");
		 editor.putString("Role", "teacher");
		 editor.commit();
		 userInfo user = new userInfo("Leonel Sánchez", "Lesand", "3", "http://www.barcampsps.com/moodle/", "101", "teacher");
		 Intent main = new Intent(LoginActivity.this, SplashScreen.class);
		 Bundle bundleUser = new Bundle();
		 bundleUser.putSerializable("userInfo", user);
		 main.putExtra("userInfo", bundleUser);
		 startActivity(main);
	}

}
