package com.example.moodle4android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

@SuppressLint("NewApi")
public class WidgetService2 extends RemoteViewsService {
  @SuppressLint("NewApi")
@Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return(new LoremViewsFactory2(this.getApplicationContext(),
                                 intent));
  }
}