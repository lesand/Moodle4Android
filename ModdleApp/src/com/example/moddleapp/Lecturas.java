package com.example.moddleapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Lecturas extends Activity {

	ArrayList<Integer> homeworkstodelete;
	LinearLayout lecturas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturas);
		lecturas= (LinearLayout)findViewById(R.id.linearLecturas);
		
		AgregarVarios("<a href=\"http://unitec.libri.mx/libro.php?libroId=415\"><span face=\"Arial, sans-serif\" style=\"font-family: Arial, sans-serif;\"><span size=\"3\" style=\"font-size: medium;\"><span lang=\"es-HN\">http://unitec.libri.mx/libro.php?libroId=415#</span></span></span></a>", "  Tamaño: 612 KB", "Modificado: No tengo idea", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lecturas, menu);
		return true;
	}
	
	
	@SuppressLint("NewApi")
	public  void AgregarVarios(String Cadena1, String Cadena2,  String Cadena3, int idLayout)
	{
		LinearLayout Temporal2 = new LinearLayout(this);
		Temporal2.setOrientation(LinearLayout.HORIZONTAL);
		CheckBox ch = new CheckBox(this);
		//ch.setOnClickListener(clickcheckListener);
		ch.setId(idLayout);
		LinearLayout Temporal = new LinearLayout(this);
		Temporal.setId(idLayout);
		Temporal.setOrientation(LinearLayout.VERTICAL);
		Temporal.setBackground(getResources().getDrawable(R.drawable.backgroundwithshadow));
		WebView webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		TextView FechaEd = new TextView(this);
		TextView FechaFin = new TextView(this);		
		FechaEd.setTextSize(12);
		FechaFin.setTextSize(12);		
		webview.loadData(Cadena1, "text/html", null);
		FechaEd.setText(Cadena2);
		FechaFin.setText(Cadena3);
		Temporal2.addView(webview);
		Temporal2.addView(ch);
		Temporal.addView(Temporal2);
		Temporal.addView(FechaEd);
		Temporal.addView(FechaFin);
		//Temporal.setOnClickListener(clickRowListener);
		lecturas.addView(Temporal);
		int left = 2;
		int top = 20;
		int right = 2;
		int bottom = 2;
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, right, bottom);
		Temporal.setLayoutParams(params);
	}

}
