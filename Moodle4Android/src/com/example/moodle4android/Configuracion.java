package com.example.moodle4android;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

@SuppressLint("NewApi")
public class Configuracion extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		ActionBar bar = getActionBar();
		bar.setTitle(" Configuración");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracion, menu);
		return true;
	}
	
	public void OnBtnCerrarSesion(View V)
	{
		 SharedPreferences settings = getSharedPreferences("Settings", 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("isLoged", false);
		 editor.putString("nameLoged", "");
		 editor.putString("passLoged", "");
		 editor.putString("IdMoodle", "");
		 editor.putString("url", "");
		 editor.putString("ContextID", "");
		 editor.putString("Role", "");
		 editor.commit();
		 Intent startMain = new Intent(Intent.ACTION_MAIN);
		 startMain.addCategory(Intent.CATEGORY_HOME);
		 startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 startActivity(startMain);
		 this.finish();
	}
	
	public void OnBtnInfoAplicacion(View V)
	{
		Intent intent = new Intent(this, Acercade.class);
	    startActivity(intent);
	}

}
