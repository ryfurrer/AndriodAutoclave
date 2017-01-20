package ryan.autoclave.sizing;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public ArrayList<InputInfo> pastData = new ArrayList<InputInfo>();

	private Spinner spin1, s2, s3, s4, sh;
	private EditText et1, et2, et3, et4;
	private TextView length;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// fixes the screen orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);
		// Identify on screen items
		spin1 = (Spinner) findViewById(R.id.spinner1);
		s2 = (Spinner) findViewById(R.id.spinner2);
		s3 = (Spinner) findViewById(R.id.spinner3);
		s4 = (Spinner) findViewById(R.id.spinner4);
		sh = (Spinner) findViewById(R.id.spinnerh);

		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		et4 = (EditText) findViewById(R.id.EditText5);

		length = (TextView) findViewById(R.id.textView7);
		length.setText("");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.diameter, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.rate_units, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.time_units, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
				this, R.array.heads, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
				this, R.array.distance_units,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spin1.setAdapter(adapter);
		s2.setAdapter(adapter2);
		s3.setAdapter(adapter3);
		s4.setAdapter(adapter5);
		sh.setAdapter(adapter4);

		// gets saved data
		loadPrefs();
		Intent i = getIntent();
		if (i.getParcelableArrayListExtra("allEntries") != null) {
			pastData = i.getParcelableArrayListExtra("allEntries");
		}
		setValues(i.getIntExtra("position", -1));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	private void savePrefs(String key, int value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		et1.setText(sp.getString("dr", null));
		et2.setText(sp.getString("rt", null));
		et3.setText(sp.getString("fb", null));
		et4.setText(sp.getString("gf", null));
		spin1.setSelection(sp.getInt("spin1", 0));
		s2.setSelection(sp.getInt("spin2", 0));
		s3.setSelection(sp.getInt("s3", 0));
		s4.setSelection(sp.getInt("s4", 0));
		sh.setSelection(sp.getInt("headType", 0));
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

	public void updateLength(View v) {
		if (et1.getText().toString().equals("")
				| et2.getText().toString().equals("")
				| et3.getText().toString().equals("")
				| et4.getText().toString().equals("")) {
			Toast toast = Toast.makeText(MainActivity.this,
					"Please fill all values.", Toast.LENGTH_SHORT);
			toast.show();
		} else {
			InputInfo ii = new InputInfo(Double.valueOf(et1.getText()
					.toString()) * getRateConversion(), Double.valueOf(et2
					.getText().toString()) * getTimeConversion(),
					Double.valueOf(et3.getText().toString())
							* getDistanceConversion(), Double.valueOf(et4
							.getText().toString()), getHeadTypeAsNum(),
					Double.valueOf((String) spin1.getSelectedItem()));
			length.setText("Autoclave length:"
					+ Math.round(ii.getLength() * 100) / 100.0
					+ "m.\nLength Diameter Ratio: "
					+ Math.round(ii.getLengthDiaRatio() * 10) / 10.0);
		}
	}

	public void seeResults(View v) {
		if (et1.getText().toString().equals("")
				| et2.getText().toString().equals("")
				| et3.getText().toString().equals("")
				| et4.getText().toString().equals("")) {
			Toast toast = Toast.makeText(MainActivity.this,
					"Please fill all values.", Toast.LENGTH_SHORT);
			toast.show();
		} else if(Double.valueOf(et1.getText().toString()) * getRateConversion()> 1000 || Double.valueOf(et1.getText().toString()) * getRateConversion()< 100){
			Toast toast = Toast.makeText(MainActivity.this,
					"The flowrate must be between 100 and 1000m^3/h.", Toast.LENGTH_LONG);
			toast.show();
		} else if(Double.valueOf(et2.getText().toString()) * getTimeConversion()> 180 || Double.valueOf(et2.getText().toString()) * getTimeConversion()< 30){
			Toast toast = Toast.makeText(MainActivity.this,
					"The retention time must be between 30 and 180min.", Toast.LENGTH_LONG);
			toast.show();
		} else if(Double.valueOf(et3.getText().toString()) * getDistanceConversion()> 1500 || Double.valueOf(et3.getText().toString()) * getDistanceConversion()< 300){
			Toast toast = Toast.makeText(MainActivity.this,
					"The freeboard must be between 300 and 1500.", Toast.LENGTH_LONG);
			toast.show();
		} else if(Double.valueOf(et4.getText().toString())> 1 || Double.valueOf(et4.getText().toString())< 0.8){
			Toast toast = Toast.makeText(MainActivity.this,
					"The gassing factor must be between 0.8 and 1.", Toast.LENGTH_LONG);
			toast.show();
		} else {
			InputInfo iii = new InputInfo(Double.valueOf(et1.getText()
					.toString()) * getRateConversion(), Double.valueOf(et2
					.getText().toString()) * getTimeConversion(),
					Double.valueOf(et3.getText().toString())
							* getDistanceConversion(), Double.valueOf(et4
							.getText().toString()), getHeadTypeAsNum(),
					Double.valueOf((String) spin1.getSelectedItem()));

			if(pastData.size()!=0){
				addItem(iii);
			}else{
				pastData.add(iii);
			}
			System.out.println(pastData.size());
			savePrefs("dr", et1.getText().toString());
			savePrefs("rt", et2.getText().toString());
			savePrefs("fb", et3.getText().toString());
			savePrefs("gf", et4.getText().toString());
			savePrefs("spin1", Integer.valueOf(spin1.getSelectedItemPosition()));
			savePrefs("spin2", Integer.valueOf(s2.getSelectedItemPosition()));
			savePrefs("s3", Integer.valueOf(s3.getSelectedItemPosition()));
			savePrefs("s4", Integer.valueOf(s4.getSelectedItemPosition()));
			savePrefs("headType", Integer.valueOf(sh.getSelectedItemPosition()));

			Intent i = new Intent(this, Results.class);
			i.putParcelableArrayListExtra("allEntries", pastData);
			startActivityForResult(i, 1);
		}
	}

	private void addItem(InputInfo iii) {
		String key = iii.getUnique();
		if (!pastData.get(0).getUnique().equals(key)) {// only add if the entry isn't the same as the last one
			pastData.add(0, iii);
			if (pastData.size() > 1) { //prevents index out of bounds error for first entry
				for (int i = 1; i < pastData.size(); i++) {
					if (pastData.get(i).getUnique().equals(key)) {
						pastData.remove(i);
						break; // gets out of the for loop once a copy is found.
					}
				}
			}
		}
	}

	private double getRateConversion() {
		if (s2.getSelectedItem().toString().equals("m^3/h")) {
			return 1;
		}
		return 0.0283168;
	}

	private double getTimeConversion() {
		if (s3.getSelectedItem().toString().equals("min")) {
			return 1;
		}
		return (60);
	}

	private double getDistanceConversion() {
		if (s4.getSelectedItem().toString().equals("mm")) {
			return 1.0;
		}
		return (25.4);
	}

	private double getHeadTypeAsNum() {
		if (sh.getSelectedItem().toString().equals("Hemi Spherical")) {
			return 0.0;
		}
		return 1.0;
	}

	public void setValues(int i) {
		if (i == -1) {

		} else {
			InputInfo use = pastData.get(i);
			et1.setText(String.valueOf(use.getFlowrate()));
			et3.setText(String.valueOf(use.getFreeboard()));
			et4.setText(String.valueOf(use.getGassingFactor()));
			et2.setText(String.valueOf(use.getRetentionTime()));
			spin1.setSelection((int) ((use.getDiameter() - 1) * 4));
			sh.setSelection((int) (use.getHeadType()));
		}
	}

	public void openHistory(View v) {
		if (pastData.size() > 0) {
			Intent i = new Intent(this, History.class);
			i.putParcelableArrayListExtra("allEntries", pastData);
			startActivity(i);
		} else {
			Toast toast = Toast.makeText(MainActivity.this,
					"History is Empty.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public void clearFields(View v) {
		et1.setText(null);
		et2.setText(null);
		et3.setText(null);
		et4.setText(null);
	}

}
