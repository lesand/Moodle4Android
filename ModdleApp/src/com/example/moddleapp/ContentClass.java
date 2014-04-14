package com.example.moddleapp;







import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;


import moodleObjects.*;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


@SuppressLint("NewApi")
public class ContentClass extends SlidingActivity {

	TabHost Tabhost;
	LinearLayout weeksLayout;
	ExpandableListView listContent;
	ExpandableListView listEvaluation;
	TableLayout TableWeeks;
	boolean isFull;
	courseContentM global;
	View CurrentRow;
	int lastX;
	expandableListClass listAdapter;
	
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_class);		
		ActionBar bar = getActionBar();
		bar.setTitle(" Contenido General");
		bar.setIcon(getResources().getDrawable(R.drawable.icocontent));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		
		//Seteamos el Menu
		setBehindContentView(R.layout.activity_bar_menu);		 
		getSlidingMenu().setBehindOffset(110);
		
		
		
		//Seteamos los Tabs
		Tabhost = (TabHost)findViewById(R.id.tabhost);
		Tabhost.setup();
		TabSpec spec = Tabhost.newTabSpec("TagContenidoGeneral");
		spec.setIndicator("General");
		spec.setContent(R.id.tab1);
		Tabhost.addTab(spec);
		spec = Tabhost.newTabSpec("TagPuntaje");
		spec.setIndicator("Puntaje");
		spec.setContent(R.id.tab2);
		Tabhost.addTab(spec);
		spec = Tabhost.newTabSpec("TagSemanas");
		spec.setIndicator("Semanas");
		spec.setContent(R.id.tab3);
		Tabhost.addTab(spec);
		
		//cambiamos color de los label del tab
		for(int i=0;i<Tabhost.getTabWidget().getChildCount();i++) 
		{
		    TextView tv = (TextView)Tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
		    tv.setTextColor(Color.WHITE);
		} 

		//Seteamos a los Layouts
		listContent = (ExpandableListView)findViewById(R.id.expandableListGeneralContent);		
		//listEvaluation = (ExpandableListView)findViewById(R.id.expandableListEvaluation);
		TableWeeks = (TableLayout)findViewById(R.id.TableWeeks);
		
		//Llenamos los Tabs
		
		try {
			if(savedInstanceState == null)
			{
				//Aca Cargo Data del Expandable
				fillgeneral();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(InfoUser.getInstance().global.getSections().size() != 0)
    	{
			fillpuntaje();
			fillsemanas();
    	}
		
		
		
	}
	public void fillgeneral() throws ParserConfigurationException, SAXException, IOException{
		 
		boolean ExistInfo = false;
		InfoUser.getInstance().Update();	
    	//Declaramos lo necesario
    	String [] tiles = new String[InfoUser.getInstance().categories.size()];
    	WebView[][] Subtiles = new WebView[InfoUser.getInstance().categories.size()][1];
    	
    	if(InfoUser.getInstance().global.getSections().size() == 0)
    	{
    		Tabhost.removeAllViews();
    		LinearLayout linea = (LinearLayout)findViewById(R.id.tab1);
			TextView Label1 = new TextView(this);
			Label1.setText("NO HAY TAREAS QUE MOSTRAR");
			Label1.setGravity(Gravity.CENTER);
			Label1.setTextSize(20);
			Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			linea.addView(Label1);
    		
    	}
        
    	for(int i=0; i<InfoUser.getInstance().categories.size(); i++)
		{
			String valueTotal = "";
			sectionCourse tempS = InfoUser.getInstance().findsection(InfoUser.getInstance().categories.get(i));
			if(tempS == null)
			{
				itemCourse tempitem = InfoUser.getInstance().findItem(InfoUser.getInstance().categories.get(i));
				if(tempitem == null)
				{
					break;					
				}
				ExistInfo = true;
				tiles[i] = "\n					" + InfoUser.getInstance().categories.get(i)+ " \n";
				valueTotal = tempitem.getLabel();
			}else
			{
				ExistInfo = true;
				tiles[i] = "\n					" + InfoUser.getInstance().categories.get(i)+ "\n";
				for(int e=0; e<tempS.getItems().size(); e++ )
				{
					valueTotal = valueTotal + tempS.getItems().get(e).getLabel();
				}
				
			}
			
			WebView webview = new WebView(this);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.loadData(valueTotal, "text/html", null);
			Subtiles[i][0] = webview;
		}
		
    	if(ExistInfo)
    	{
    		 listAdapter = new expandableListClass(this, tiles, Subtiles, null, null);
    		 listContent.setAdapter(listAdapter);
    		 
    		
    	}else
    	{
    		
    		LinearLayout linea = (LinearLayout)findViewById(R.id.tab1);
    		linea.removeAllViews();
			TextView Label1 = new TextView(this);
			Label1.setText("NO HAY CONTENIDO DE LA CLASE QUE MOSTRAR");
			Label1.setGravity(Gravity.CENTER);
			Label1.setTextSize(20);
			Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			linea.addView(Label1);
    	}
		
			
		
		
	}
	
	public void fillpuntaje()
	{
		boolean ExistInfo=false;
		String [] tiles = new String[1];
    	WebView[][] Subtiles = new WebView[1][1];
		String sev = "SISTEMA DE EVALUACIÓN DEL CURSO";
		String valueTotal = "";
		sectionCourse tempsc = InfoUser.getInstance().findsection(sev);
		if(tempsc == null)
		{
			itemCourse tempitem = InfoUser.getInstance().findItem(sev);
			if(tempitem == null)
			{
				return;				
			}
			ExistInfo = true;
			tiles[0] = sev;
			valueTotal = tempitem.getLabel();
			
			
		}else
		{
			ExistInfo = true;
			tiles[0] = sev;
			for(int e=0; e<tempsc.getItems().size(); e++ )
			{
				valueTotal = valueTotal + tempsc.getItems().get(e).getLabel();
			}
			
		}
		WebView webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadData(valueTotal, "text/html", null);
	
		LinearLayout ll = (LinearLayout)findViewById(R.id.tab2);
		if(ExistInfo){
			ll.removeAllViews();
			ll.addView(webview);
		}else
		{
			ll.removeAllViews();
			TextView Label1 = new TextView(this);
			Label1.setText("NO HAY PUNTAJE QUE MOSTRAR");
			Label1.setGravity(Gravity.CENTER);
			Label1.setTextSize(20);
			Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			ll.addView(Label1);
		}
			
		
	}
	public void fillsemanas()
	{
		TableWeeks.removeAllViews();
		if(InfoUser.getInstance().listWeeks.size() == 0)
		{
			LinearLayout ll = (LinearLayout)findViewById(R.id.tab3);
			ll.removeAllViews();
			TextView Label1 = new TextView(this);
			Label1.setText("NO HAY SEMANAS QUE MOSTRAR");
			Label1.setGravity(Gravity.CENTER);
			Label1.setTextSize(20);
			Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			ll.addView(Label1);
		}
		
		TableRow TableRowTitle = new TableRow(this);
		TableRowTitle.setBackground(getResources().getDrawable(R.drawable.stylelabeltables));
		TableRowTitle.setClickable(true);
		TextView Label1 = new TextView(this);
		Label1.setText("Numero");
		Label1.setTextColor(Color.WHITE);
		Label1.setGravity(Gravity.CENTER);
		Label1.setTextSize(16);
		Label1.setPaintFlags(Label1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG |Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label1);
		TextView Label2 = new TextView(this);
		Label2.setText("Tema");
		Label2.setTextColor(Color.WHITE);
		Label2.setGravity(Gravity.CENTER);
		Label2.setTextSize(16);
		Label2.setPaintFlags(Label2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG |Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label2);
		TextView Label3 = new TextView(this);
		Label3.setText("Información");
		Label3.setGravity(Gravity.CENTER);
		Label3.setTextSize(16);
		Label3.setTextColor(Color.WHITE);
		Label3.setPaintFlags(Label3.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG |Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label3);
		TableWeeks.addView(TableRowTitle);
		
		
		//Seteamos Contenido
		for(int i=0; i<InfoUser.getInstance().listWeeks.size(); i++)
		{
			TableRow row = new TableRow(this);
			row.setBackground(getResources().getDrawable(R.drawable.styletable));
			row.setClickable(true);
			row.setOnClickListener(clickRowListener);			
			
			
			TextView numero = new TextView(this);		
			numero.setText(Integer.toString(i+1));
			numero.setGravity(Gravity.CENTER);		
			row.addView(numero);
			
			TextView tema = new TextView(this);		
			tema.setText(InfoUser.getInstance().listWeeks.get(i).tema);
			tema.setGravity(Gravity.CENTER);		
			row.addView(tema);
			
			TextView clickText = new TextView(this); 
			clickText.setId(i);
			clickText.setText("Mas >");
			clickText.setTextColor(Color.parseColor("#FF032862"));
			clickText.setPaintFlags(clickText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
			clickText.setGravity(Gravity.CENTER);
			clickText.setOnClickListener(LinkListener);
			row.addView(clickText);
			TableWeeks.addView(row);
		
		}
		

	}

     

	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			if(CurrentRow != null)
			{
				((TableRow)CurrentRow).setBackground(getResources().getDrawable(R.drawable.styletable));
				
			}
			
			CurrentRow = v;
			v.setBackgroundColor(Color.parseColor("#0a1c25"));
		}
		
	};
	
	OnClickListener LinkListener = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			Log.d("id", Integer.toString(v.getId()));
			InfoUser.getInstance().currentWeek = v.getId();
			Intent intent = new Intent(getApplicationContext(), SemanaContenido.class);
			startActivity(intent);
			
		}
	
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		fillpuntaje();
		fillsemanas();
	};
	
	

	
	public void OnClickTareas(View v)
	{
		Intent intent = new Intent(getApplicationContext(), Homeworks.class);
		startActivity(intent);
	}
	
	public void OnClickPerfil(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ShowPerfil.class);
		startActivity(intent);
	}
	
	public void OnClickMembers(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ShowMembers.class);
		startActivity(intent);
	}
	
	public void OnClickMessages(View v)
	{
		Intent intent = new Intent(getApplicationContext(), Messages.class);
		startActivity(intent);
	}

}
