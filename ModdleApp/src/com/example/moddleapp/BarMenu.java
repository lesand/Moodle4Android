package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

@SuppressLint("NewApi")
public class BarMenu extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_menu);
		ActionBar bar = getActionBar();
		bar.hide();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_menu, menu);
		return true;
	}
	
	
	public void OnClickTareas(View v)
	{
		Intent intent = new Intent(getApplicationContext(), Homeworks.class);
		startActivity(intent);
	}

}
