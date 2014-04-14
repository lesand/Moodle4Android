package com.example.moddleapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ar.com.daidalos.afiledialog.FileChooserDialog;
import java.io.File;



@SuppressLint("NewApi")
public class ShowHomeWork extends Activity {
	
	
	EditText nameFile;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_home_work);
		ActionBar bar = getActionBar();
		bar.setTitle(" Tarea");
		bar.setIcon(getResources().getDrawable(R.drawable.homeworkico));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		nameFile = (EditText)findViewById(R.id.namefileSelected);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_home_work, menu);
		return true;
	}
	
	
	
	public void OnBtnSelectdFileClicked(View v)
	{
		FileChooserDialog dialog = new FileChooserDialog(this);
		dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
			@Override
	         public void onFileSelected(Dialog source, File file) {
	             source.hide();
	             nameFile.setText(file.getName());
	                }
			
			@Override
	         public void onFileSelected(Dialog source, File folder, String name) {
	             source.hide();
	             Toast toast = Toast.makeText(source.getContext(), "File created: " + folder.getName() + "/" + name, Toast.LENGTH_LONG);
	             toast.show();
	                }
	        });
		dialog.show();
		
		
		
	}

}
