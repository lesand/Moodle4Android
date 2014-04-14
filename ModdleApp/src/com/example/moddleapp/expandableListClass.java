package com.example.moddleapp;



import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class expandableListClass extends BaseExpandableListAdapter{
		

	Context contexto;
	String[]Titles;
	boolean[]isButton;
	WebView[][]SubTiles;
	int []idsButtons;
	
	
	public  expandableListClass(Context contex, String[]titles, WebView[][]subtiles, boolean[] isbutton, int[]btnid)
	{
		contexto = contex;
		this.Titles = titles;
		this.SubTiles = subtiles;
		this.isButton = isbutton;
		this.idsButtons = btnid;
		
		
	}
	
	@Override
	public Object getChild(int arg0, int arg1) {
		return SubTiles[arg0][arg1];
	}
	
	public void setChild(int arg0, int arg1, WebView text)
	{
		SubTiles[arg0][arg1] = text;
	}
	
	public void setParent(int arg0, String text)
	{
		Titles[arg0]=text;
	}
	

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
		
	}

	@SuppressLint("NewApi")
	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		
				/*
				if(isButton[arg0])
				{
					Button btn = new Button(contexto);
					btn.setText("View More");
					btn.setId(idsButtons[arg0]);
					btn.setTextColor(Color.WHITE);
					btn.setOnClickListener(myhandler1);
					btn.setBackground(contexto.getResources().getDrawable(R.drawable.buttostyle2));
					
					
					return btn;
				}
				*/
				WebView webview = new WebView(contexto);
				webview.getSettings().setJavaScriptEnabled(true);
				webview = SubTiles[arg0][arg1];
				return webview;
		
	}

	@Override
	public int getChildrenCount(int arg0) {
		return SubTiles[arg0].length;
	}

	@Override
	public Object getGroup(int arg0) {
		return SubTiles[arg0];
	}

	@Override
	public int getGroupCount() {
		
		return Titles.length;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(contexto);
		tv.setText(this.Titles[arg0]);
		tv.setTextSize(12);
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	  View.OnClickListener myhandler1 = new View.OnClickListener() {
		    public void onClick(View v) {
		      // it was the 1st button
		    }
	  };


	public void clear() {
		this.Titles = null;
		this.SubTiles = null;
		
	}
	
}
