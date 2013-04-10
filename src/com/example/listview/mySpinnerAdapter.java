package com.example.listview;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class mySpinnerAdapter implements SpinnerAdapter {
	List<String> myList;
	Context myContext;
	
	public mySpinnerAdapter(Context context, List< String > sortList) {
		myList = sortList;
		myContext = context;
	}
	
	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem( int position ) {
		return myList.get( position );
	}

	@Override
	public long getItemId( int position ) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType( int position ) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		TextView row;
		
		LayoutInflater inflater = LayoutInflater.from( myContext );
    row = (TextView) inflater.inflate(R.layout.spinner_layout, parent, false);
		
    row.setText( myList.get( position ) );
    
		return row;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver( DataSetObserver observer ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver( DataSetObserver observer ) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getDropDownView( int position, View convertView, ViewGroup parent ) {
		TextView row;
		
		LayoutInflater inflater = LayoutInflater.from( myContext );
    row = (TextView) inflater.inflate(R.layout.spinner_layout, parent, false);
		
    row.setText( myList.get( position ) );
    
		return row;
	}

}
