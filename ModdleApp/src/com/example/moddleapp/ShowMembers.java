package com.example.moddleapp;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import moodleObjects.HomeWork;
import moodleObjects.InfoUser;
import moodleObjects.user;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.drawable.BitmapDrawable;

@SuppressLint("NewApi")
public class ShowMembers extends Activity {

	TableLayout TableMembers;
	LinearLayout LayoutParticipants;
	View CurrentRow;
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_members);
		ActionBar bar = getActionBar();
		bar.setTitle(" Participantes");
		bar.setIcon(getResources().getDrawable(R.drawable.miembros));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));		
		//TableMembers = (TableLayout)findViewById(R.id.TablaDeMiembros);
		LayoutParticipants = (LinearLayout)findViewById(R.id.linearParticipants);
		
		try {
			InfoUser.getInstance().UpdateParticipants();
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
		
		try {
			fillParticipants();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_members, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	public void fillParticipants() throws MalformedURLException, IOException
	{
		
		
		for(int i=0; i<InfoUser.getInstance().Participants.size(); i++)
		{
			user TempUser = (user) InfoUser.getInstance().Participants.get(i);
			LinearLayout Temporal2 = new LinearLayout(this);
			Temporal2.setOrientation(LinearLayout.HORIZONTAL);
			Temporal2.setId(i);
			Temporal2.setBackground(getResources().getDrawable(R.drawable.backgroundwithshadow));
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
			myImageView.setId(i);
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
			
		}
		
				
	
		
		
		
		

	}
	
	
	OnClickListener clickRowListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			user tempUser = (user)InfoUser.getInstance().Participants.get(v.getId());
			if(tempUser.getUserid().compareTo(Integer.toString(InfoUser.getInstance().userId)) == 0)
			{
				InfoUser.getInstance().currentParticipant = v.getId();
				Intent intent = new Intent(getApplicationContext(), ShowPerfil.class);
				startActivity(intent);
			}else
			{
				InfoUser.getInstance().currentIdChat = Integer.parseInt(tempUser.getUserid());
				Intent intent = new Intent(getApplicationContext(), Chat.class);
				startActivity(intent);
				
			}
			
		}
		
	};
	
	OnClickListener ClickImageViewListener = new OnClickListener()
	{
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			InfoUser.getInstance().currentParticipant = v.getId();
			Intent intent = new Intent(getApplicationContext(), ShowPerfil.class);
			startActivity(intent);
			
		}
		
	};
	
		public static Bitmap decodeSampleBitmapFromStream(InputStream stream, int reqWidth, int reqHeight)
		{
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Rect outPadding = null;
			
			
			BitmapFactory.decodeStream(stream, outPadding, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(stream, outPadding, options);
			
			 
		}
	
		public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, resId, options);
			

	    // Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(res, resId, options);
		}
		
		public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

			//Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}

			return inSampleSize;
		}
	
	

}
