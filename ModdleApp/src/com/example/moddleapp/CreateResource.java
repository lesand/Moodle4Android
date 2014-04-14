package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;

@SuppressLint("NewApi")
public class CreateResource extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_resource);
		ActionBar bar = getActionBar();
		bar.setTitle(" Crear Recurso");
		bar.setIcon(getResources().getDrawable(R.drawable.resources));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_resource, menu);
		return true;
	}

}
