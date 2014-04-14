package com.example.moddleapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import moodleObjects.InfoUser;
import moodleObjects.user;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ShowPerfil extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_perfil);
		ActionBar bar = getActionBar();
		bar.setTitle(" Perfil de Estudiante");
		bar.setIcon(getResources().getDrawable(R.drawable.userperfil));		
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar1));
		user current = (user) InfoUser.getInstance().Participants.get(InfoUser.getInstance().currentParticipant);
		ImageView image = (ImageView) findViewById(R.id.imagePerfil);
		TextView NameFull = (TextView) findViewById(R.id.TextNameFull);
		TextView email = (TextView) findViewById(R.id.textEmail);
		TextView celular = (TextView) findViewById(R.id.textMobile);
		TextView telefono = (TextView) findViewById(R.id.textTelefono);
		TextView ciudad = (TextView) findViewById(R.id.textCiudad);
		TextView descripcion = (TextView) findViewById(R.id.textDescripcionperfil);
		
		InputStream is = null;
		try {
			is = (InputStream) new URL(current.getPicture()).getContent();
		} catch (MalformedURLException e) {
			
		} catch (IOException e) {
			
		}
		BitmapDrawable d =  (BitmapDrawable) Drawable.createFromStream(is, "src name");
		Bitmap bitmap =  Bitmap.createScaledBitmap(d.getBitmap(), 100, 100, false);
		image.setImageBitmap(bitmap);
		
		
		NameFull.setText(current.getFullName());
		if(current.getEmailAddress() != "null" || current.getEmailAddress() != null || current.getEmailAddress() != "")
		{
			email.setText("Email: El Usuario no dispone de un email publico");
		}else
		{
			email.setText("Email: " + current.getEmailAddress());
		}
		
		if(current.getMobilePhone() != "null" || current.getMobilePhone() != null || current.getMobilePhone() != "")
		{
			celular.setText("Celular: El usuario no dispone de un numero movil publico");
		}else
		{
			celular.setText("Celular: " + current.getMobilePhone());
		}
		
		if(current.getPhone() != "null" || current.getPhone() != null || current.getPhone() != "")
		{
			telefono.setText("Telefono: El usuario no dispone de un numero telefonico publico");
		}else
		{
			telefono.setText("Telefono: " + current.getPhone());
		}
		
		if(current.getCityTown() != "null" || current.getCityTown() != null || current.getCityTown() != "")
		{
			ciudad.setText("Ciudad: " + current.getCityTown());
		}else
		{
			ciudad.setText("Ciudad: El usuario no dispone de un nombre de ciudad publica");
		}
		
		if(current.getDescription() != "null" || current.getDescription() != null || current.getDescription() != "")
		{
			descripcion.setText("El usuario no dispone de una descripcion en estos momentos");
		}else
		{
			descripcion.setText(current.getDescription());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_perfil, menu);
		return true;
	}
	
	
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
