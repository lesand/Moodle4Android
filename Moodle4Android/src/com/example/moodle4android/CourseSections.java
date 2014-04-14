package com.example.moodle4android;

import java.util.ArrayList;
import java.util.Locale;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import moodleObjects.*;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CourseSections extends SlidingActivity {
	LinearLayout LayoutSections;
	ArrayList<infoUrl> urls;
	ListHomeWorks homeworks;
	listRecursos recursos;
	listPerfilUserInfo Participants;
	infoCourse course;
	int hayContendio = 1;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_sections);
		getActionBar().setTitle("Secciones de Curso");
		setBehindContentView(R.layout.activity_bar_menu);	
		getSlidingMenu().setMode(SlidingMenu.RIGHT);
		getSlidingMenu().setBehindOffset(110);
		
		Bundle b = getIntent().getBundleExtra("courseInfo");
		if(b != null)
		{
			course = (infoCourse)b.getSerializable("courseInfo");
			/*try
			{
				
				Participants = new listPerfilUserInfo();
				Locale locale = new Locale("es");
				Locale.setDefault(locale);
				Configuration config = new Configuration();
				config.locale = locale;
				getBaseContext().getResources().updateConfiguration(config,
		        getBaseContext().getResources().getDisplayMetrics());
				connecFunc.getInstance().tryConnect("courseid="+course.getIdMoodleCourse(),"core_course_get_contents", course.getCurrentUser().getUrl());
				String str=connecFunc.getInstance().xml;
				enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE_CONTENT,str);
				obj.parseString();
				ArrayList<moodleObj> list=new ArrayList<moodleObj>();
				list=obj.giveMetheObject();
				courseContentM global = (courseContentM) list.get(0);
				LayoutSections = (LinearLayout)findViewById(R.id.LayoutSections);
				urls = new ArrayList<infoUrl>();
				homeworks = new ListHomeWorks(course);
				recursos = new listRecursos(course);
				RenderSections(global);
				ObtainParticipants("courseid="+course.getIdMoodleCourse(),"core_enrol_get_enrolled_users", course.getCurrentUser().getUrl());
			}
			catch(Exception ex)
			{
				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(CourseSections.this);
		   	 	dlgAlert.setMessage("En estos momentos no hay conexion a internet o surgio un problema con la plataforma");
		        dlgAlert.setTitle("AVISO");
		        dlgAlert.setPositiveButton("OK", null);
		        dlgAlert.setCancelable(true);
		        dlgAlert.create().show();

		        dlgAlert.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int which) {
		        		CourseSections.this.finish();
		            }
		        });
			}
			*/
			
			
		}else
		{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_sections, menu);
		return true;
	}
	
	public void RefreshInfo()
	{
		try{
			hayContendio = 0;
			Participants = new listPerfilUserInfo();
			Locale locale = new Locale("es");
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,
	        getBaseContext().getResources().getDisplayMetrics());
			connecFunc.getInstance().tryConnect("courseid="+course.getIdMoodleCourse(),"core_course_get_contents", course.getCurrentUser().getUrl());
			String str=connecFunc.getInstance().xml;
			enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE_CONTENT,str);
			obj.parseString();
			ArrayList<moodleObj> list=new ArrayList<moodleObj>();
			list=obj.giveMetheObject();
			courseContentM global = (courseContentM) list.get(0);
			LayoutSections = (LinearLayout)findViewById(R.id.LayoutSections);
			urls = new ArrayList<infoUrl>();
			homeworks = new ListHomeWorks(course);
			recursos = new listRecursos(course);
			RenderSections(global);
			ObtainParticipants("courseid="+course.getIdMoodleCourse(),"core_enrol_get_enrolled_users", course.getCurrentUser().getUrl());
		}catch(Exception ex)
		{
			AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(CourseSections.this);
	   	 	dlgAlert.setMessage("En estos momentos no hay conexion a internet o surgio un problema con la plataforma");
	        dlgAlert.setTitle("AVISO");
	        dlgAlert.setPositiveButton("OK", null);
	        dlgAlert.setCancelable(true);
	        dlgAlert.create().show();

	        dlgAlert.setPositiveButton("Ok",
	        new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        		CourseSections.this.finish();
	            }
	        });
		}
	}
	
	@Override
	protected void onResume() {
		final ProgressDialog dialog = ProgressDialog.show(CourseSections.this, "", "Cargando...", true);
		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
				   
					

			    CourseSections.this.runOnUiThread(new Runnable() {
			      @Override
			      public void run()
			      {
			    	  if(hayContendio == 0)
			    	  {
			    		  LayoutSections.removeAllViews();
			    	  }
			    	  RefreshInfo();
			    	  dialog.dismiss();
			      }
			    });
			  }
			}).start();

		
		super.onResume();
	};
	
	
	public void RenderSections(courseContentM Global)
	{
		int urlsCounter=0;;
		for(int i=0; i<Global.getSections().size(); i++)
		{
			boolean havehomeworks = false;
			boolean haveItems = false;
			boolean rendereoTareas = false;
			boolean rendereoRecursos = false;
			LinearLayout LayoutVertical = new LinearLayout(this);
			LayoutVertical.setOrientation(LinearLayout.VERTICAL);
			LayoutVertical.setId(i);
			LayoutVertical.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout3));
			LayoutVertical.setGravity(Gravity.CENTER);
			sectionCourse currentSection = Global.getSections().get(i);
			LayoutSections.addView(LayoutVertical);
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			int left = 2;
			int top = 20;
			int right = 2;
			int bottom = 2;
			params.setMargins(left, top, right, bottom);
			LayoutVertical.setLayoutParams(params);
			LinearLayout LayoutVertical2 = new LinearLayout(this);
			LayoutVertical2.setOrientation(LinearLayout.VERTICAL);
			LayoutVertical2.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout2));
			LayoutVertical2.setGravity(Gravity.CENTER);
			LayoutVertical.addView(LayoutVertical2);
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			LayoutVertical2.setLayoutParams(params2);
			TextView TextName = new TextView(this);
			TextName.setGravity(Gravity.CENTER);
			TextName.setText(currentSection.getSectionName());
			TextName.setTextSize(20);
			LayoutVertical2.addView(TextName);
			
			for(int e=0; e<currentSection.getItems().size(); e++)
			{	haveItems = true;
				itemCourse currentItem = currentSection.getItems().get(e);
				System.out.println(currentItem.getItemType());
				System.out.println("Name " + currentItem.getName());
				System.out.println("Label " + currentItem.getLabel());
				System.out.println("Idsection " + currentSection.getSectionId());
				if(currentItem.getItemType().compareTo("label")== 0)
				{
					
					WebView webview = new WebView(this);
					
					try{
						
						//webview.loadData(currentItem.getLabel(), "text/html", "utf-8");
						webview.loadDataWithBaseURL(null, currentItem.getLabel(), "text/html", "UTF-8", null);
					
						LayoutVertical.addView(webview);
						TableRow.LayoutParams vc= new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						webview.setLayoutParams(vc);
					}catch(Exception ex)
					{
						continue;
					}
					
				}else if(currentItem.getItemType().compareTo("assign")==0)
				{
					havehomeworks = true;
					homeworks.addElement(new infoHomeWork(currentItem.getName(), currentItem.getIDItemCourse(), currentSection.getSectionId(), currentItem.getLabel()));
					if(!rendereoTareas){
						TextView TextTarea = new TextView(this);
						TextTarea.setOnClickListener(ClickViewHomeworksListener);
						TextTarea.setId(Integer.parseInt(currentSection.getSectionId()));
						TextTarea.setText(" \n    Ver Tareas ");
						TextTarea.setTextColor(Color.parseColor("#FF032862"));
						TextTarea.setPaintFlags(TextTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
						TextTarea.setTextSize(18);
						LayoutVertical.addView(TextTarea);
						rendereoTareas = true;
					}
					
				}else if(currentItem.getItemType().compareTo("resource")==0)
				{
					recursos.addElement(new infoRecurso(currentItem.getName(), currentItem.getIDItemCourse(), currentSection.getSectionId(), currentItem.getLabel()));
					if(!rendereoRecursos)
					{
						TextView TextResource = new TextView(this);
						TextResource.setOnClickListener(ClickViewRecursosListener);
						TextResource.setId(Integer.parseInt(currentSection.getSectionId()));
						TextResource.setText(" \n    Ver Recursos ");
						TextResource.setTextColor(Color.parseColor("#FF032862"));
						TextResource.setPaintFlags(TextResource.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
						TextResource.setTextSize(18);
						LayoutVertical.addView(TextResource);
					}
				}else if(currentItem.getItemType().compareTo("url")== 0)
				{
					TextView TextUrl = new TextView(this);
					TextUrl.setOnClickListener(ClickUrlListener);
					TextUrl.setId(urlsCounter);
					TextUrl.setText("\n    " + currentItem.getName() );
					TextUrl.setTextColor(Color.parseColor("#FF032862"));
					TextUrl.setPaintFlags(TextUrl.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
					TextUrl.setTextSize(14);
					LayoutVertical.addView(TextUrl);
					urlsCounter++;
					urls.add(new infoUrl(currentItem.getIDItemCourse(), currentSection.getSectionId(), currentItem.getName(), currentItem.getLabel()));
				}
			}
			
			if(!haveItems)
			{
				TextView TextAdvise = new TextView(this);
				TextAdvise.setText("\n No hay contenido en esta sección que mostar");
				TextAdvise.setTextSize(20);
				LayoutVertical.addView(TextAdvise);
				
				
				
			}
			
			if(!havehomeworks)
			{
				if(course.getCurrentUser().getIdRole().compareTo("teacher")==0)
				{
					TextView TextCrearTarea = new TextView(this);
					TextCrearTarea.setOnClickListener(ClickAgregarTarea);
					TextCrearTarea.setId(Integer.parseInt(currentSection.getSectionId()));
					TextCrearTarea.setText(" \n    Agregar Tarea ");
					TextCrearTarea.setTextColor(Color.parseColor("#FF032862"));
					TextCrearTarea.setTextSize(20);
					TextCrearTarea.setPaintFlags(TextCrearTarea.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG | Paint.UNDERLINE_TEXT_FLAG);
					LayoutVertical.addView(TextCrearTarea);
				}
			}
			
			TextView TextEmpty = new TextView(this);
			TextEmpty.setText("                               ");
			TextEmpty.setTextSize(18);
			LayoutVertical.addView(TextEmpty);
		
			
		}
	}
	
	public void ObtainParticipants(String idcourse, String command, String url)
	{
		try{
			
			ArrayList<moodleObj> list=new ArrayList<moodleObj>();
			connecFunc.getInstance().tryConnect(idcourse, command, url);
			String str=connecFunc.getInstance().xml;
			enumObjects obj=new enumObjects(enumObjects.moodleEnum.PARTICIPANTS,str);
			obj.parseString();
			list=obj.giveMetheObject();
			for(int i=0; i<list.size(); i++)
			{
				user temp = (user)list.get(i);
				perfilUserInfo tempUser = new perfilUserInfo();
				tempUser.setUserid(temp.getUserid());
				tempUser.setPassword(temp.getPassword());
				tempUser.setUsername(temp.getUsername());
				tempUser.setFirstName(temp.getFirstName());
				tempUser.setLastName(temp.getLastName());
				tempUser.setFullName(temp.getFullName());
				tempUser.setEmailAddress(temp.getEmailAddress());
				tempUser.setCityTown(temp.getCityTown());
				tempUser.setCountry(temp.getCountry());
				tempUser.setDescription(temp.getDescription());
				tempUser.setPicture(temp.getPicture());
				tempUser.setWebPage(temp.getWebPage());
				tempUser.setSkypedId(temp.getSkypedId());
				tempUser.setPhone(temp.getPhone());
				tempUser.setMobilePhone(temp.getMobilePhone());
				tempUser.setAddress(temp.getAddress());
				Participants.addElement(tempUser);
			}
	    	
		}catch(Exception ex)
		{
			
		}
	}
	
	OnClickListener ClickUrlListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			infoUrl tempUrl = urls.get(arg0.getId());
			Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(tempUrl.getContent()));
			startActivity(intent);
		}
	};
	
	OnClickListener ClickViewHomeworksListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			Bundle b = new Bundle();
			b.putSerializable("homeworkInfo", homeworks);
			b.putInt("sectionId", arg0.getId());
			Intent main = new Intent(CourseSections.this, SplashScreenPreHomeworks.class);
            main.putExtra("homeworkInfo", b);
            startActivity(main);
		}
	};
	
	OnClickListener ClickViewRecursosListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			Bundle b = new Bundle();
			b.putSerializable("recursosInfo", recursos);
			b.putInt("sectionId", arg0.getId());
			Intent main = new Intent(CourseSections.this, SplashScreenPreRecursos.class);
            main.putExtra("recursosInfo", b);
            startActivity(main);
		}
	};
	
	OnClickListener ClickAgregarTarea = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			Bundle b = new Bundle();
			b.putSerializable("currentCourse", course);
			b.putString("SectionId", Integer.toString(arg0.getId()));
			Intent main = new Intent(CourseSections.this, CreateHomeWork.class);
			main.putExtra("homeworkInfo", b);
			startActivity(main);
		}
	};
	
	public void OnClickTareas(View V)
	{
		Bundle b = new Bundle();
		b.putSerializable("homeworkInfo", homeworks);
		b.putInt("sectionId", -1);
		Intent main = new Intent(CourseSections.this, SplashScreenPreHomeworks.class);
        main.putExtra("homeworkInfo", b);
        startActivity(main);
	}
	
	public void OnClickRecursos(View V)
	{

		Bundle b = new Bundle();
		b.putSerializable("recursosInfo", recursos);
		b.putInt("sectionId", -1);
		Intent main = new Intent(CourseSections.this, SplashScreenPreRecursos.class);
        main.putExtra("recursosInfo", b);
        startActivity(main);
	}
	
	public void OnClickPerfil(View V)
	{
		Bundle b = new Bundle();
		b.putSerializable("participantsInfo", Participants);
		b.putSerializable("currentCourse", course);
		b.putString("participant", course.getCurrentUser().getIdMoodle());
		Intent main = new Intent(CourseSections.this, SplashScreenPrePerfil.class);
        main.putExtra("listparticipantsInfo", b);
        startActivity(main);
	}
	
	public void OnClickParticipants(View V)
	{
		Bundle b = new Bundle();
		b.putSerializable("listparticipants", Participants);
		b.putSerializable("currentCourse", course);
		Intent main = new Intent(CourseSections.this, SplashScreenPreParticipants.class);
        main.putExtra("listparticipantsInfo", b);
        startActivity(main);
	}
	
	public void OnClickMensajes(View V)
	{
		Bundle b = new Bundle();
		b.putSerializable("listparticipants", Participants);
		b.putSerializable("currentCourse", course);
		Intent main = new Intent(CourseSections.this, SplashScreenPreMensajes.class);
        main.putExtra("listparticipantsInfo", b);
        startActivity(main);
	}

}
