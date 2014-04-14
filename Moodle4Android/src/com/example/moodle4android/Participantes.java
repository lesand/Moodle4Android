package com.example.moodle4android;







import java.io.InputStream;
import java.net.URL;


import moodleObjects.infoCourse;
import moodleObjects.listPerfilUserInfo;
import moodleObjects.perfilUserInfo;
import moodleObjects.user;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

@SuppressLint("NewApi")
public class Participantes extends Activity {

	listPerfilUserInfo participantes;
	infoCourse currentCourse;
	LinearLayout LayoutParticipants;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participantes);
		ActionBar bar = getActionBar();
		bar.setTitle(" Participantes");
		Bundle b = getIntent().getBundleExtra("listparticipantsInfo");
		if(b!=null)
		{
			participantes = (listPerfilUserInfo)b.getSerializable("listparticipants");
			currentCourse = (infoCourse)b.getSerializable("currentCourse");
			LayoutParticipants = (LinearLayout)findViewById(R.id.linearParticipants);
			renderParticipantes();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.participantes, menu);
		return true;
	}
	
	
	private void renderParticipantes()
	{
		for(int i=0; i<participantes.SizeList(); i++)
		{
			try{
				perfilUserInfo TempUser = (perfilUserInfo)participantes.getElement(i);
				LinearLayout Temporal2 = new LinearLayout(this);
				Temporal2.setOrientation(LinearLayout.HORIZONTAL);
				Temporal2.setId(Integer.parseInt(TempUser.getUserid()));
				Temporal2.setBackground(getResources().getDrawable(R.drawable.stylelinearlayout1));
				InputStream is = (InputStream) new URL(TempUser.getPicture()).getContent();
				BitmapDrawable d =  (BitmapDrawable) Drawable.createFromStream(is, "src name");
				Bitmap bitmap =  Bitmap.createScaledBitmap(d.getBitmap(), 100, 100, false);
				Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
				BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
				Paint paint = new Paint();
				paint.setShader(shader);
				paint.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
				Canvas c = new Canvas(circleBitmap);
				c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
				ImageView myImageView = new ImageView(this);
				myImageView.setId(Integer.parseInt(TempUser.getUserid()));
				myImageView.setPadding(0, 7, 0, 0);
				myImageView.setImageBitmap(circleBitmap);
				myImageView.setOnClickListener(ClickImageViewListener);
				Temporal2.addView(myImageView);
				android.view.ViewGroup.LayoutParams layoutParams = myImageView.getLayoutParams();
				layoutParams.width = 150;
				layoutParams .height = 160;
				myImageView.setLayoutParams(layoutParams);
				circleBitmap = null;				
				TextView Nombre = new TextView(this);
				Nombre.setTextSize(18);
				Nombre.setText("  " + TempUser.getFullName());
				Nombre.setGravity(Gravity.CENTER);	
				Nombre.setPadding(0, 20, 0, 0);
				Temporal2.addView(Nombre);
				Temporal2.setOnClickListener(clickRowListener);
				LayoutParticipants.addView(Temporal2);	
				int left = 2;
				int top = 20;
				int right = 2;
				int bottom = 2;
				TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(left, top, right, bottom);
				Temporal2.setLayoutParams(params);
			}catch(Exception ex)
			{
				continue;
			}
			
			
		}
	}
	
	
	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
		
			if(Integer.toString(v.getId()).compareTo(currentCourse.getCurrentUser().getIdMoodle())==0)
			{
				Bundle b = new Bundle();
				b.putSerializable("participantsInfo", participantes);
				b.putSerializable("currentCourse", currentCourse);
				b.putString("participant", Integer.toString(v.getId()));
				Intent main = new Intent(Participantes.this, SplashScreenPrePerfil.class);
		        main.putExtra("listparticipantsInfo", b);
		        startActivity(main);
			}else
			{
				Bundle b = new Bundle();
				b.putSerializable("MeUserInfo", findUser(currentCourse.getCurrentUser().getIdMoodle()));
				b.putSerializable("OtherParticipant", findUser(Integer.toString(v.getId())));
				b.putSerializable("CurrentCourse", currentCourse);
				Intent main = new Intent(Participantes.this, SplashScreenPreChat.class);
		        main.putExtra("getChatParticipants", b);
		        startActivity(main);
			}
			
			
			
			
		}
		
	};
	
	private perfilUserInfo findUser(String idUser)
	{
		for(int i=0; i<participantes.SizeList(); i++)
		{
			if(participantes.getElement(i).getUserid().compareTo(idUser)==0)
			{
				return participantes.getElement(i);
			}
		}
		
		return null;
	}
	
	OnClickListener ClickImageViewListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			Bundle b = new Bundle();
			b.putSerializable("participantsInfo", participantes);
			b.putSerializable("currentCourse", currentCourse);
			b.putString("participant", Integer.toString(v.getId()));
			Intent main = new Intent(Participantes.this, SplashScreenPrePerfil.class);
	        main.putExtra("listparticipantsInfo", b);
	        startActivity(main);
				
			
			
		}
		
	};

}
