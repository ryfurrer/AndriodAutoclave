package ryan.autoclave.sizing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Results extends ActionBarActivity {

	private InputInfo inputs;
	TextView tV01, tV02, tV03, tV04, tV05, tV06, tV07, tV08, tV09, tV010;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getIntentInfo();
		setContentView(R.layout.results);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tV01 = (TextView) findViewById(R.id.TextView01);
		tV02 = (TextView) findViewById(R.id.TextView02);
		tV03 = (TextView) findViewById(R.id.TextView03);
		tV04 = (TextView) findViewById(R.id.TextView04);
		tV05 = (TextView) findViewById(R.id.TextView05);
		tV06 = (TextView) findViewById(R.id.TextView06);
		tV07 = (TextView) findViewById(R.id.TextView07);
		tV08 = (TextView) findViewById(R.id.TextView08);
		tV09 = (TextView) findViewById(R.id.TextView09);
		tV010 = (TextView) findViewById(R.id.TextView010);
		tV01.setText(String.valueOf((Math.round(inputs.getVolume() * 100)) / 100.0)
				+ "m^3");
		tV02.setText(String.valueOf(Math.round(inputs.getCylinderVolume() * 100) / 100.0)
				+ "m^3");
		tV03.setText(String.valueOf(Math.round(inputs.getHeadVolume() * 100) / 100.0)
				+ "m^3");
		tV04.setText(String.valueOf(Math.round(inputs.total_static_volume() * 100) / 100.0)
				+ "m^3");
		tV05.setText(String.valueOf(Math.round(inputs.get_Head_static_volume() * 100) / 100.0)
				+ "m^3");
		tV06.setText(String.valueOf(Math.round(inputs.getDyVolume() * 100) / 100.0)
				+ "m^3");
		tV07.setText(String.valueOf(Math.round(inputs.getLength() * 100) / 100.0)
				+ "m");
		tV08.setText(String.valueOf(Math.round(inputs.getFluidHeight() * 100) / 100.0)
				+ "mm");
		tV09.setText(String.valueOf(inputs.get_compart_num()));
		tV010.setText(String.valueOf(Math.round(inputs.getLengthDiaRatio() * 10) / 10.0));
	}

	private void getIntentInfo() {
		Intent i = getIntent();
		inputs = (InputInfo) i.getParcelableArrayListExtra("allEntries").get(0);
		Log.v("IMPT", "It works!");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar
		// automatically handles clicks on the Home/Up button
		int id = item.getItemId();
		if (id == R.id.action_about) {
			openAbout(null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openAbout(View v) {
		Intent intent = new Intent(this, Information.class);
		startActivity(intent);
	}

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#getSupportParentActivityIntent()
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		Bundle data = getIntent().getExtras();
		Intent i = super.getSupportParentActivityIntent();
		i.putParcelableArrayListExtra("allEntries", data.getParcelableArrayList("allEntries"));
		i.putExtra("sort", true);
		return i;
	}
}
