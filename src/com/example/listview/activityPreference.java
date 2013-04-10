/**
 * 
 */
package com.example.listview;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class activityPreference extends PreferenceActivity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  addPreferencesFromResource(R.xml.preferences);
 } 
}
