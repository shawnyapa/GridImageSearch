package com.shawnyapa.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends Activity {
	
	public FilterSettings filterSetting; 
	
	private Spinner spSize;
	private Spinner spType;
	private Spinner spColor;
	private EditText etSite;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		filterSetting = (FilterSettings) getIntent().getSerializableExtra("filterSetting");
		
		spSize = (Spinner) findViewById(R.id.spSize);
		spType = (Spinner) findViewById(R.id.spType);
		spColor = (Spinner) findViewById(R.id.spColor);
		etSite = (EditText) findViewById(R.id.etSite);
		
		setSpinnerToValue(spSize, filterSetting.Size);
		setSpinnerToValue(spType, filterSetting.Type);
		setSpinnerToValue(spColor, filterSetting.Color);
		etSite.setText(filterSetting.Site);
		
		String temp = spSize.getSelectedItem().toString();
		//Log.i("INFO", temp);
		
		return true;
	}
	
	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
				break; // terminate loop
			}
		}
		spinner.setSelection(index);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
		//}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveSettings() {
		String Size = spSize.getSelectedItem().toString();
		String Color = spColor.getSelectedItem().toString();
		String Type = spType.getSelectedItem().toString();
		String site = etSite.toString();
		
		// Create Intent
		// Create FilterSettings
		// Set Values
		// Add Extra to Intent (Serializable)
		// start Intent
		// finish activity
		
	}
}
