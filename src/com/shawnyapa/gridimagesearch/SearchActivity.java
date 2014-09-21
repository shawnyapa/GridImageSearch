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


public class SearchActivity extends Activity {
	
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
				// TODO Auto-generated method stub
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				String fullUrl = result.fullUrl;
				i.putExtra("fullUrl", fullUrl);
				startActivity(i);
			}
			
		});
		
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
    	
    	String start = "1";
    	String searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start=" + start + "&q=" + etQuery.getText().toString();
    	Log.i("INFO", searchURL.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchURL, new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) {
        		JSONArray imageResultsJson = null;
        		try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					Log.i("INFO", imageResultsJson.toString());
					imageResults.clear(); // Clear only for new searches
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		//Log.i("INFO", imageResults.toString());
        		//aImageResults.notifyDataSetChanged();
        	}
        });
    	
    	
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
