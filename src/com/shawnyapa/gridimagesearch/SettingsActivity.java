package com.shawnyapa.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends Activity {
	
	public FilterSettings filterSetting; 
	
	private Spinner spSize;
	private Spinner spType;
	private Spinner spColor;
	private EditText etSite;
	private int request_Code_Filter=1;
	

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
		
		setSpinnerToValue(spSize, filterSetting.size);
		setSpinnerToValue(spType, filterSetting.type);
		setSpinnerToValue(spColor, filterSetting.color);
		etSite.setText(filterSetting.site);
		
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
	
	public void saveSettings(View v) {
		
		String size = spSize.getSelectedItem().toString();
		String color = spColor.getSelectedItem().toString();
		String type = spType.getSelectedItem().toString();
		String site = etSite.getText().toString();
		
		filterSetting.size = size;
		filterSetting.color = color;
		filterSetting.type = type;
		filterSetting.site = site;
		
		Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("filterSetting", filterSetting);
        setResult(RESULT_OK, i);
        finish();
		
	}
}
