package com.example.listview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter< BikeData > {

	// TODO create your cache if you choose

	private static final String	TAG								= "CustomAdapter";
	private static final String	DOLLARSIGN				= "$ ";
	private List< BikeData >		data;
	Context											context;
	private String							URL_of_JSON_host	= null;

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
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		View v = convertView;
		// TODO fill this out, use both Viewholder and convertview

		// TODO set your holders data
		if ( v == null ) {
			// set the position in the list this image will belong to
			LayoutInflater li = LayoutInflater.from(context);
			v = li.inflate( R.layout.listview_row_layout, null );

			ViewHolder holder = new ViewHolder();
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
			ViewHolder holder = (ViewHolder)v.getTag();
			holder.Model.setText( bike.model );
			holder.Price.setText( "$" + bike.price );
			holder.Description.setText( bike.description );
			holder.pictureID = bike.picture;
		}
		
		// TODO optional check to see if image is in cache first
		// Bitmap image = .. your code here

		// if (image != null){
		// //TODO found it in cache set the image in the row in the listview
		// }
		// else {
		// //TODO at this point you launch an async task to go and grab the image,
		// make sure you pass along a pointer to the
		// //exact imageview that is supposed to be modified (its the one in this
		// row). When the async task enters onPostexecute
		// //with a bitmap it will have a pointer to the place where the picture
		// should be displayed. Sorta like a forwarding
		// //address
		// }
		// give it to listview for display
		return v;
	}

	/**
	 * @param string
	 */
	public void setNewURL( String bikeURL ) {
		
	}

	/**
	 * @param string
	 */
	public void sortList( int pos ) {
		// TODO pos defines field to sort on
		// TODO based on that sort your dataset and then reload

	}
	
}