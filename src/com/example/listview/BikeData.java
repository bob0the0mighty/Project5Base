package com.example.listview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

// TODO fill this in
public class BikeData {

	private static final String	NL	= "\n";
	String											company;
	String											model;
	String											location;
	double											price;
	String											priceString;
	String											date;
	String											description;
	String											picture;
	String											link;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company: " + company + BikeData.NL + 
				"Model: " + model + BikeData.NL + 
				"Location: " + location + BikeData.NL + 
				"Price: $" + priceString + BikeData.NL + 
				"Date: " + date + BikeData.NL + 
				"Description: " + description	+ BikeData.NL + 
				"Link: " + link;
	}

	public BikeData( JSONObject bike ) {
		try {
			company = bike.getString( "Company" );
			model = bike.getString( "Model" );
			location = bike.getString( "Location" );
			price = bike.getDouble( "Price" );
			priceString = String.format( "%1.2f", price );
			date = bike.getString( "Date" );
			description = bike.getString( "Description" );
			picture = bike.getString( "Picture" );
			link = bike.getString( "Link" );
		}
		catch ( JSONException e ) {
			e.printStackTrace();
		}
	}

	public static List< String > getComparatorNames() {
		String[] comparator_names = { "company", "model", "price", "date" };
		List< String > comparators = new ArrayList< String >(
				comparator_names.length );
		for ( String comp : comparator_names ) {
			comparators.add( comp );
		}
		Collections.sort( comparators );
		return comparators;
	}

	public static Comparator< BikeData >	CompanyComparator			= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.company
																																			.compareToIgnoreCase( rhs.company );
																																}
																															};

	public static Comparator< BikeData >	ModelComparator				= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.model
																																			.compareToIgnoreCase( rhs.model );
																																}
																															};

	public static Comparator< BikeData >	LocationComparator		= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.location
																																			.compareToIgnoreCase( rhs.location );
																																}
																															};

	public static Comparator< BikeData >	PriceComparator				= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	Double lhsPrice = lhs.price;
																																	Double rhsPrice = rhs.price;
																																	return lhsPrice
																																			.compareTo( rhsPrice );
																																}
																															};

	public static Comparator< BikeData >	DateComparator				= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.date
																																			.compareToIgnoreCase( rhs.date );
																																}
																															};

	public static Comparator< BikeData >	DescriptionComparator	= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.description
																																			.compareToIgnoreCase( rhs.description );
																																}
																															};

	public static Comparator< BikeData >	LinkComparator				= new Comparator< BikeData >() {

																																@Override
																																public int compare(
																																		BikeData lhs,
																																		BikeData rhs ) {
																																	return lhs.link
																																			.compareToIgnoreCase( rhs.link );
																																}
																															};
}
