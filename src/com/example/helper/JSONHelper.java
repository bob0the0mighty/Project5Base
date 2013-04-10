package com.example.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.listview.BikeData;

/**
 * @author lynn static helper class for accessing JSON data
 * 
 *         see:http://prativas.wordpress.com/category/android/part-1-retrieving-
 *         a-json-file/
 */
public class JSONHelper {

	private static final String BIKES = "Bikes";
	
	// no need to instantiate this
	private JSONHelper() {}

	/**
	 * @param jsonString
	 * @return List<UserData> takes a json string and parses it into json objects
	 *         You must know what is in the data and what to parse out of it
	 */
	public static List<BikeData> parseAll( String jsonString ) {
		List<BikeData> bikeList = new ArrayList< BikeData >();

		try {
			// create a json object with the JSON string passed in
			JSONObject jAll = new JSONObject( jsonString );
			JSONArray bikeArray = (JSONArray) jAll.get(BIKES);
			
			
			for(int counter = 0; counter < bikeArray.length(); counter++) {
				BikeData bike = new BikeData( (JSONObject) bikeArray.get( counter ) );
				bikeList.add( bike );
			}

		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		
		return bikeList;
	}

}
