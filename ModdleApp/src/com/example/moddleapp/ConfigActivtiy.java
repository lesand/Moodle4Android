package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ConfigActivtiy extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_activtiy);
		ActionBar bar = getActionBar();
		bar.setTitle(" Configuración");
		bar.setIcon(getResources().getDrawable(R.drawable.settings));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_activtiy, menu);
		return true;
	}
	
	
	 public void OnBtnclicklogout(View V)
	 {
		 SharedPreferences settings = getSharedPreferences("Settings", 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("isLoged", false);
		 editor.putString("nameLoged", "");
		 editor.putString("passLoged", "");
		 editor.commit();
		 Intent startMain = new Intent(Intent.ACTION_MAIN);
		 startMain.addCategory(Intent.CATEGORY_HOME);
		 startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 startActivity(startMain);
		 this.finish();
		 
	 
	 }
	 
	
	
	public void onBtnClickinfo(View v)
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ConfigActivtiy.this);
   	 	
		 
   	 	dlgAlert.setMessage("Aplicacion creada por Joshua Welchez y Leonel Sánchez como proyecto de Graduacion para la carrera Ingenieria en Sistemas Computacionales de Unitec - mas Informacion llamar al Cel: 32376757 ó escribir al leonels@probit.co");
        dlgAlert.setTitle("Informacion");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
        new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) {

            }
        });
	}
	
	
	

}
