package com.example.listview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.helper.JSONHelper;

public class Activity_ListView extends ListActivity implements
		OnSharedPreferenceChangeListener {

	private static final String				BIKEJSON				= "bikes.json";
	private static final String				URL_JSON				= "URLPref";
	private static final String				TAG							= "Activity_ListView";
	private static final String				BAD_CONNECTION	= "Can not Connect";
	private static final String				BAD_URL					= "Malformed URL";

	OnSharedPreferenceChangeListener	listener;
	OnItemSelectedListener						mySpinnerListener;

	private AlertDialog.Builder				errAlert;
	private String										errString;
	private String										bikeURL;
	private List< BikeData >					bikeList;
	private SharedPreferences					myPreference;
	private CustomAdapter							myAdapter;
	private AsyncJSONGetter						jsonGetter;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		// listen for a change to the URL_JSON key,
		// its the key part of the key value pair that holds the URL to load
		// when this key changes then the URL has changed, so reload it
		// make this listener an instance var so it is not GCed due to it being
		// saved as a weak reference
		myPreference = PreferenceManager.getDefaultSharedPreferences( this );
		myPreference.registerOnSharedPreferenceChangeListener( this );

		// listen for a spinner change
		mySpinnerListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected( AdapterView< ? > arg0, View selectedItemView,
					int position, long id ) {
				Log.d( TAG, "Spinner changed " + selectedItemView.toString() );
				// TODO resort List
			}

			@Override
			public void onNothingSelected( AdapterView< ? > arg0 ) {}
		};


		bikeURL = getString( R.string.tetonURL ) + BIKEJSON;
		bikeList = new ArrayList< BikeData >();

		jsonGetter = new AsyncJSONGetter();
		
		myAdapter = new CustomAdapter( this, R.layout.listview_row_layout,
				bikeList, bikeURL );

		setListAdapter( myAdapter );
		
		jsonGetter.execute( bikeURL );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.menu, menu );

		// get a reference to the action bar spinner
		Spinner s = (Spinner) menu.findItem( R.id.spinner ).getActionView();
		
		// TODO create a SpinnerAdapter for the spinner and bind it to the
		// spinner
		SpinnerAdapter mSpinnerAdapter = new mySpinnerAdapter( this, BikeData.getComparatorNames() );
		s.setAdapter(mSpinnerAdapter);

		s.setOnItemSelectedListener( mySpinnerListener );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
			case R.id.action_settings:
				Intent myIntent = new Intent( this, activityPreference.class );
				startActivity( myIntent );
			default:
				break;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ListActivity#onContentChanged()
	 */
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		// if changed serialize the data to disk
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 * android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick( ListView l, View v, int position, long id ) {
		super.onListItemClick( l, v, position, id );
		Toast.makeText( this, l.getItemAtPosition( position ).toString(),
				Toast.LENGTH_LONG ).show();
	}

	public class AsyncJSONGetter extends AsyncTask< String, Void, String > {

		@Override
		protected String doInBackground( String... arg0 ) {
			BufferedReader in = null;
			URL url = null;
			HttpURLConnection connection = null;
			errString = "";

			try {
				url = new URL( arg0[0] );
				try {
					connection = (HttpURLConnection) url.openConnection();
					connection.setDoInput( true );
					connection.connect();
					in = new BufferedReader( new InputStreamReader(
							connection.getInputStream() ) );

					StringBuffer sb = new StringBuffer( "" );
					String line = "";

					while ( ( line = in.readLine() ) != null ) {
						sb.append( line );
					}

					in.close();

					String page = sb.toString();
					// System.out.println(page);
					return page;
				}
				catch ( IOException e ) {
					e.printStackTrace();
					// System.err.println("IO Exception on HTTP connection");
					errString = BAD_CONNECTION;
				}
			}
			catch ( MalformedURLException e ) {
				e.printStackTrace();
				// System.err.println(BAD_URL);
				errString = BAD_URL;
			}
			return "";
		}

		protected void onPostExecute( String result ) {

			bikeList = JSONHelper.parseAll( result );
			myAdapter.clear();
			myAdapter.addAll( bikeList );
			
			if ( !errString.isEmpty() ) {
				errAlert.setMessage( errString );
				errAlert.create().show();
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
			String key ) {
		Log.d( TAG, "Preference key =" + key );

		if ( key.equals( URL_JSON ) ) {
			bikeURL = myPreference.getString( key, getString( R.string.tetonURL ) )
					+ BIKEJSON;
			if ( bikeURL.equals( BIKEJSON ) ) {

			} else {
				jsonGetter.execute( bikeURL );
			}
		}
	}
}
