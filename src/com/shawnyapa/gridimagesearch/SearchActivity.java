package com.shawnyapa.gridimagesearch;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


public class SearchActivity extends Activity {
	
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private int offset; // number of images already pulled and in the ListArray
	private int rsz=8; // number of images to pull per query
	private int request_Code_Filter=1;
	public FilterSettings filterSetting;
	private String searchURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filterSetting = new FilterSettings();
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }


    private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				String fullUrl = result.fullUrl;
				i.putExtra("fullUrl", fullUrl);
				startActivity(i);
			}
			
		});
        gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
	        customLoadMoreDataFromApi(); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }
        });
		
	}

    // Append more data into the adapter
    public void customLoadMoreDataFromApi() {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	makeGoogleApiCall();
    	
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    public void onImageSearch(View v) {
    	// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=
    	// responseData =>results =>[x] =>tbUrl, title, url, width, height
    	offset = 1;
		imageResults.clear();
		aImageResults.clear();
    	makeGoogleApiCall();
    }
    
    private void checkFilter() {
    	Boolean filterActive = false;
    	searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz="+ rsz +"&start=" + offset + "&q=" + etQuery.getText().toString();
		if (filterSetting.size.equals("Small")) {
			searchURL = searchURL + "&imgsz=icon";
			filterActive = true;
		}
		if (filterSetting.size.equals("Medium")) {
			searchURL = searchURL + "&imgsz=medium";
			filterActive = true;
		}
		if (filterSetting.size.equals("Large")) {
			searchURL = searchURL + "&imgsz=xxlarge";
			filterActive = true;
		}
		if (filterSetting.size.equals("Huge")) {
			searchURL = searchURL + "&imgsz=huge";
			filterActive = true;
		}
		
		
		
		if (filterActive == true) {
			Toast.makeText(this, "Filter is ACTIVE", Toast.LENGTH_LONG).show();
		}
	}


	public void makeGoogleApiCall() {
    	  
		checkFilter();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchURL, new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) {
        		JSONArray imageResultsJson = null;
        		try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					e.printStackTrace();
				}
        	}
        });  	
    	offset=offset+rsz; // Increment the Offset by the number of pulled images
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
    	setfilterImages();
        return super.onOptionsItemSelected(item);
    }


	private void setfilterImages() {
        //---Create Bundle Object and attach to the Intent object---
		Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("filterSetting", filterSetting);
        startActivityForResult(i, request_Code_Filter);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == request_Code_Filter) {
	     // Extract name value from result extras
	     FilterSettings filterReturn = (FilterSettings) data.getSerializableExtra("filterSetting");

	     // If the Filter has been updated, notify the user with a Toast
	     if (!((filterSetting.size.equals(filterReturn.size)) && (filterSetting.type.equals(filterReturn.type))
	    		 && (filterSetting.color.equals(filterReturn.color)) && (filterSetting.site.equals(filterReturn.site)))) {
	    	Toast.makeText(this, "Filter has been Updated", Toast.LENGTH_SHORT).show(); 
	     } 
	     
	     filterSetting.size = filterReturn.size;
	     filterSetting.type = filterReturn.type;
	     filterSetting.color = filterReturn.color;
	     filterSetting.site = filterReturn.site;
	     
	  }
	}
}
