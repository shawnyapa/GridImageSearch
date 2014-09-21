package com.shawnyapa.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageResult {
	
	public String fullUrl;
	public String title;
	public String thumbUrl;
	public int height;
	public int width;
	
	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.title = json.getString("title");
			this.thumbUrl = json.getString("tbUrl");
			this.height = json.getInt("height");
			this.width = json.getInt("width");
			Log.i("INFO", fullUrl.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i=0; i<array.length(); i++) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		Log.i("INFO", results.toString());
		return results;
		
	}

}
