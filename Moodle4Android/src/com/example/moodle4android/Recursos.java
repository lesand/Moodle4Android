package com.example.moodle4android;





import moodleObjects.*;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Recursos extends Activity {

	
	TableLayout tableRecursos;
	listRecursos recursos;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recursos);
		getActionBar().setTitle("Recursos");
		Bundle b = getIntent().getBundleExtra("recursosInfo");
		if(b!=null)
		{
			 recursos = (listRecursos) b.getSerializable("recursosInfo");
			 int sectioniD = b.getInt("sectionId");
			 tableRecursos = (TableLayout)findViewById(R.id.TableRecursos);
			 RenderRecursos(sectioniD);
		}else
		{
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recursos, menu);
		return true;
	}
	
	public void RenderRecursos(int sectioniD)
	{
		if(recursos.SizeList() == 0)
		{
			TableRow TableRowTitle = new TableRow(this);
			TableRowTitle.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout2));
			TableRowTitle.setClickable(true);
			TextView Message = new TextView(this);
			Message.setPaintFlags(Message.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
			Message.setText("  No hay Tareas que mostrar");
			Message.setGravity(Gravity.CENTER);
			TableRowTitle.addView(Message);
			tableRecursos.addView(TableRowTitle);
			return;
		}
		
		TableRow TableRowTitle = new TableRow(this);
		//TableRowTitle.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout3));
		TableRowTitle.setClickable(true);
		
		TextView Label1 = new TextView(this);
		Label1.setText("Numero");
		Label1.setTextColor(Color.BLACK);
		Label1.setGravity(Gravity.CENTER);
		Label1.setTextSize(16);
		Label1.setPaintFlags(Label1.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label1);
		TextView Label2 = new TextView(this);
		Label2.setText("Nombre");
		Label2.setTextColor(Color.BLACK);
		Label2.setGravity(Gravity.CENTER);
		Label2.setTextSize(16);
		Label2.setPaintFlags(Label2.getPaintFlags() |Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label2);
		TextView Label3 = new TextView(this);
		Label3.setText("Descargar");
		Label3.setGravity(Gravity.CENTER);
		Label3.setTextSize(16);
		Label3.setTextColor(Color.BLACK);
		Label3.setPaintFlags(Label3.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
		TableRowTitle.addView(Label3);
		tableRecursos.addView(TableRowTitle);
		
		for(int i=0; i<recursos.SizeList(); i++)
		{
			if(sectioniD != -1)
			{
				if(recursos.getElement(i).getidSection().compareTo(Integer.toString(sectioniD))!=0)
				{
					continue;
				}
			}
			
			infoRecurso currentRecurso = recursos.getElement(i);
			TableRow row = new TableRow(this);
			row.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
			
			row.setClickable(true);
			//row.setOnClickListener(clickRowListener);			
			
			
			TextView numero = new TextView(this);		
			numero.setText(Integer.toString(i+1));
			numero.setGravity(Gravity.CENTER);	
			numero.setTextSize(14);
			row.addView(numero);
			
			TextView tema = new TextView(this);		
			tema.setText(currentRecurso.getName());
			tema.setGravity(Gravity.CENTER);
			tema.setTextSize(14);
			row.addView(tema);
			
		
			TextView clickText = new TextView(this); 
			clickText.setId(i);
			clickText.setText("ir >");
			clickText.setTextSize(15);
			clickText.setTextColor(Color.parseColor("#FF032862"));
			clickText.setPaintFlags(clickText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
			clickText.setGravity(Gravity.CENTER);
			clickText.setOnClickListener(ClickUrlListener);
			row.addView(clickText);
		
			tableRecursos.addView(row);
			
		}
	}
	
	OnClickListener ClickUrlListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			infoRecurso currentRecurso = recursos.getElement(arg0.getId());
			System.out.println("content" + currentRecurso.getDescription());
			Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(currentRecurso.getDescription()));
			startActivity(intent);
		}
	};

}
