package com.example.moodle4android;

import java.io.InputStream;
import java.net.URL;

import moodleObjects.ListHomeWorks;
import moodleObjects.infoCourse;
import moodleObjects.listPerfilUserInfo;
import moodleObjects.perfilUserInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Perfil extends Activity {

	listPerfilUserInfo participantes;
	int currentParticipant;
	infoCourse currentCourse;
	Button btnsendMessage;
	TableLayout TablePerfil;
	TextView NameUser;
	ImageView PicturePerfil;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		ActionBar bar = getActionBar();
		bar.setTitle(" Perfil");
		Bundle b = getIntent().getBundleExtra("listparticipantsInfo");
		if(b!=null)
		{
			participantes = (listPerfilUserInfo)b.getSerializable("participantsInfo");
			String currentParticipant = b.getString("participant");
			currentCourse = (infoCourse)b.getSerializable("currentCourse");
			TablePerfil = (TableLayout)findViewById(R.id.tableInPerfil);
			btnsendMessage = (Button)findViewById(R.id.btnSendmessage);
			NameUser =(TextView)findViewById(R.id.NameUser);
			PicturePerfil = (ImageView)findViewById(R.id.imagePerfil);
			if(currentParticipant.compareTo(currentCourse.getCurrentUser().getIdMoodle())==0)
			{
				btnsendMessage.setVisibility(View.INVISIBLE);
			}
			
			renderInfoUser(findCurrentUser(currentParticipant));
		}else
		{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil, menu);
		return true;
	}
	
	private perfilUserInfo findCurrentUser(String currentParticipant)
	{
		for(int i=0; i<participantes.SizeList(); i++)
		{
			if(currentParticipant.compareTo(participantes.getElement(i).getUserid())==0)
			{
				return participantes.getElement(i);
			}
		}
		return null;
	}
	
	
	
	private void renderInfoUser(perfilUserInfo currentUser)
	{
		try{
			boolean label1render = false;
			boolean label2render = false;
			NameUser.setText(currentUser.getFullName());
			InputStream is = (InputStream) new URL(currentUser.getPicture()).getContent();
			BitmapDrawable d =  (BitmapDrawable) Drawable.createFromStream(is, "src name");
			Bitmap bitmap =  Bitmap.createScaledBitmap(d.getBitmap(), 100, 100, false);
			PicturePerfil.setImageBitmap(bitmap);
			
			if(currentUser.getCityTown() !=null)
			{
				if(!label1render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información Personal: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label1render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Ciudad: " + currentUser.getCityTown());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getCountry() !=null)
			{
				if(!label1render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información Personal: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label1render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Pais: " + currentUser.getCountry());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getAddress() !=null)
			{
				if(!label1render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información Personal: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label1render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Dirección: " + currentUser.getAddress());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getEmailAddress() !=null)
			{
				if(!label2render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información de Contacto: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label2render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Email: " + currentUser.getEmailAddress());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getWebPage() !=null)
			{
				if(!label2render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información de Contacto: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label2render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Pagina Web: " + currentUser.getWebPage());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getSkypedId() !=null)
			{
				if(!label2render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información de Contacto: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label2render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Skype Id: " + currentUser.getSkypedId());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getPhone() !=null)
			{
				if(!label2render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información de Contacto: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label2render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Telefono: " + currentUser.getPhone());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getMobilePhone() !=null)
			{
				if(!label2render)
				{
					TableRow TableTempRow = new TableRow(this);
					TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
					TextView LabelBanner1 = new TextView(this);
					LabelBanner1.setText("Información de Contacto: ");
					LabelBanner1.setTextColor(Color.BLACK);
					LabelBanner1.setGravity(Gravity.LEFT);
					LabelBanner1.setTextSize(16);
					TableTempRow.addView(LabelBanner1);
					label2render = true;
					TablePerfil.addView(TableTempRow);
				}
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Celular: " + currentUser.getMobilePhone());
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				TablePerfil.addView(TableTempRow);
				
			}
			
			if(currentUser.getDescription() !=null)
			{
				
				TableRow TableTempRow = new TableRow(this);
				TableTempRow.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout6));
				TextView LabelBanner1 = new TextView(this);
				LabelBanner1.setText("Descripción: ");
				LabelBanner1.setTextColor(Color.BLACK);
				LabelBanner1.setGravity(Gravity.LEFT);;
				LabelBanner1.setTextSize(16);
				TableTempRow.addView(LabelBanner1);
				label2render = true;
				TablePerfil.addView(TableTempRow);
				
				
				TableRow TableTempRow2 = new TableRow(this);
				TableTempRow2.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				TextView LabelBanner2 = new TextView(this);
				LabelBanner2.setText(currentUser.getAddress());
				LabelBanner2.setTextColor(Color.BLACK);
				LabelBanner2.setGravity(Gravity.LEFT);
				LabelBanner2.setTextSize(16);
				TableTempRow2.addView(LabelBanner2);
				TablePerfil.addView(TableTempRow2);
				
			}
				
				
				
				
			
			
		}catch(Exception ex)
		{
			
		}
		
		
	}

}
