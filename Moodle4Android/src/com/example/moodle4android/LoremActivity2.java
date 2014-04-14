package com.example.moodle4android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class LoremActivity2 extends Activity {
  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    
    String word=getIntent().getStringExtra(WidgetProvider2.EXTRA_WORD);
    
    if (word==null) {
      word="We did not get a word!";
    }
    
    Toast.makeText(this, word, Toast.LENGTH_LONG).show();
    
    finish();
  }
}
