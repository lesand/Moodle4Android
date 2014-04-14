package com.example.moddleapp;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import moodleObjects.InfoUser;
import moodleObjects.weekObject;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class SemanaContenido extends SlidingActivity {

	TextView tema;
	TextView Objetivos;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_semana_contenido);
		ActionBar bar = getActionBar();
		bar.setTitle(" Semana 1");
		bar.setIcon(getResources().getDrawable(R.drawable.semanaico));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		
		setBehindContentView(R.layout.activity_bar_menu_teacher);
		getSlidingMenu().setMode(SlidingMenu.RIGHT);
		getSlidingMenu().setBehindOffset(110);
		
		//Setear valores de la semana :D
		int currentSemana = InfoUser.getInstance().currentWeek;
		weekObject week = InfoUser.getInstance().listWeeks.get(currentSemana);
		tema = (TextView)findViewById(R.id.ContentTema);
		tema.setText(week.tema);
		bar.setTitle("Semana " + Integer.toString(currentSemana+1));
		Objetivos =(TextView)findViewById(R.id.ContentObjetivo);
		Objetivos.setText(week.objetivos);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.semana_contenido, menu);
		return true;
	}
	
	
	 public void onBtnClickHomeWork(View V)
	 {
		 
		 Intent intent = new Intent(this, Homeworks.class);
		 startActivity(intent);
		 
		 
	 }
	 
	 public void OnBtnClickLecturas(View V)
	 {
		 Intent intent = new Intent(this, Lecturas.class);
		 startActivity(intent);
	 }
	 
	 public void OnBtnCreateHomeWork(View V)
	 {
		 Intent intent = new Intent(this, CreateHomeWork.class);
		 startActivity(intent);
	 }
	 
	 public void OnBtnCreatePresentation(View V)
	 {
		 Intent intent = new Intent(this, CreatePresentation.class);
		 startActivity(intent);
	 }
	 
	 public void OnBtnCreateResource(View V)
	 {
		 Intent intent = new Intent(this, CreateResource.class);
		 startActivity(intent);
	 }

}
