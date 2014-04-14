package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class CreatePresentation extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_presentation);
		ActionBar bar = getActionBar();
		bar.setTitle(" Subir Presentacion");
		bar.setIcon(getResources().getDrawable(R.drawable.presentations));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_presentation, menu);
		return true;
	}

}
