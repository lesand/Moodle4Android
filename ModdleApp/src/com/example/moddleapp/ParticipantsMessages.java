package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;

@SuppressLint("NewApi")
public class ParticipantsMessages extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participants_messages);
		ActionBar bar = getActionBar();
		bar.setTitle(" Chose Participants");
		bar.setIcon(getResources().getDrawable(R.drawable.chat));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
	}
	
	
	
	public void RenderParticipants()
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.participants_messages, menu);
		return true;
	}

}
