package ryan.autoclave.sizing;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History extends ListActivity{

	ArrayList<InputInfo> data;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		data = i.getExtras().getParcelableArrayList("allEntries");
		ArrayList<String> dataNames = new ArrayList<String>();
		for(InputInfo ii : data){
			dataNames.add(ii.getValuesAsString());
		}
		
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataNames));
	}

	protected void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		//use = pastData.get(position);
		Intent i = new Intent(this, MainActivity.class);
		i.putParcelableArrayListExtra("allEntries", data);
		i.putExtra("position", position);
		startActivity(i);
	}
}

