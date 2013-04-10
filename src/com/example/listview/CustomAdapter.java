package com.example.listview;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter< BikeData > {

	// TODO create your cache if you choose
	private HashMap< String, Bitmap >	bikeImageMap;
	
	private static final String	TAG								= "CustomAdapter";
	private static final String	DOLLARSIGN				= "$ ";
	private List< BikeData >		data;
	Context											context;
	private String							URL_of_JSON_host	= null;
	private LayoutInflater li;
	
	/**
	 * @author lynn class that holds pointers to all the views in
	 *         listview_row_layout.xml you can hold additional data as well, for
	 *         instance I held a copy of the the images pictureID (its filename)
	 *         so that I could match the picture to the bike when sorting so
	 */
	private static class ViewHolder {

		ImageView	imageView1;
		TextView	Model;
		TextView	Price;
		TextView	Description;
		String		pictureID;
	}

	// TODO define your custom adapter, pass in your collection of bikedata
	public CustomAdapter(
			Context context,
			int textViewResourceId,
			List< BikeData > data,
			String URL_of_JSON_host ) {
		super( context, textViewResourceId, data );

		// yes thats a reference to same object
		// but I dont want to allocate too much memory
		this.data = data;
		this.context = context;
		this.URL_of_JSON_host = URL_of_JSON_host;
		li = LayoutInflater.from(context);
		bikeImageMap = new HashMap< String, Bitmap >();
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		View v = convertView;
		ViewHolder holder = new ViewHolder();
		
		if ( v == null ) {
			v = li.inflate( R.layout.listview_row_layout, null );

			holder.imageView1 = (ImageView) v
					.findViewById( R.id.imageView1 );
			holder.Model = (TextView) v.findViewById( R.id.Model );
			holder.Price = (TextView) v.findViewById( R.id.Price );
			holder.Description = (TextView) v
					.findViewById( R.id.Description );
			holder.pictureID = data.get( position ).picture;
			
			v.setTag( holder );
		} 
		
		BikeData bike = data.get( position );
		if(bike != null){
			holder = (ViewHolder)v.getTag();
			holder.Model.setText( bike.model );
			holder.Price.setText( "$" + bike.price );
			holder.Description.setText( bike.description );
			holder.pictureID = bike.picture;
		}
		
		
		// TODO optional check to see if image is in cache first
		Bitmap image = bikeImageMap.get( URL_of_JSON_host + holder.pictureID );
		if (image != null){
			holder.imageView1.setImageBitmap( image );
		} else {
			new AsyncImageGetter().execute(  holder );
		}
		// give it to listview for display
		return v;
	}

	/**
	 * @param string
	 */
	public void setNewURL( String bikeURL ) {
		URL_of_JSON_host = bikeURL;
		bikeImageMap = new HashMap< String, Bitmap >();
	}

	/**
	 * @param string
	 */
	public void sortList( int pos ) {
		switch( pos ) {
			case 0:
				sort( BikeData.CompanyComparator );
				break;
			case 1:
				sort( BikeData.DateComparator );
				break;
			case 2:
				sort( BikeData.ModelComparator);
				break;
			default:
				sort(BikeData.PriceComparator);
		}
	}
	
	private class AsyncImageGetter extends AsyncTask<ViewHolder, Void, Bitmap> {
		ViewHolder holder;
		
		@Override
		protected Bitmap doInBackground(ViewHolder... arg0) {
			holder = arg0[0];
			Bitmap myBitmap = null;
			try {
				URL url = new URL( URL_of_JSON_host + arg0[0].pictureID );
		    try {
		    	HttpURLConnection connection;
		     	connection = (HttpURLConnection) url.openConnection();
		     	connection.setDoInput(true);
			    connection.connect();
			    InputStream input = connection.getInputStream();
			    myBitmap = BitmapFactory.decodeStream(input);
		     } catch (IOException e) {
		     	e.printStackTrace();
		     	System.err.println("IO Exception on HTTP connection");
		     	//return BitmapFactory.decodeResource( context.getResources(), R.drawable.ic_launcher );
		    } 
			} catch (MalformedURLException e) {
				e.printStackTrace();
				//return BitmapFactory.decodeResource( context.getResources(), R.drawable.ic_launcher );
			}
			return myBitmap;
		}

		protected void onPostExecute(Bitmap result) {
			//Bitmap image = new BitmapDrawable( context.getResources() ,result);
			if( result != null || result.getWidth() > 0) {
				ImageView iv = holder.imageView1;
				iv.setImageBitmap( result );
				bikeImageMap.put( URL_of_JSON_host + holder.pictureID, result );
			}
		}
	}
}