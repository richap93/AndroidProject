package rpat789.softeng206.contactsmanager;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Generic class that contains common implementation for the All contacts and 
 * Favourites Tab
 * @author Richa Patel
 *
 */
public abstract class TabsAbstract extends Fragment implements SortListener {
	
	protected Cursor cursor;
	protected ContactsDatabaseHelper dbHelper;
	protected String sortOrder = "firstName";
	protected ListView listView;
	protected CursorListAdapter clAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_main, container, false);
		super.onCreate(savedInstanceState);

		//Open database and register a listener to the current activity
		dbHelper = ContactsDatabaseHelper.getDatabase(getActivity());
		dbHelper.addSortListener(this);

		listView = (ListView)rootView.findViewById(R.id.main_listView);
		refresh();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {

				//get the whole view of the item in list
				ViewGroup group = (ViewGroup)clickedView;

				//get the first name in that view
				View contactId = group.findViewById(R.id.contact_id);

				//Get the Id of the contact in the listview 
				TextView fId = (TextView)contactId;

				//Gets Id as a string as passes it to the intent that opens ViewContact
				String idNum = fId.getText().toString();
				Intent i = new Intent();
				i.putExtra("ID", idNum);
				i.setClass(getActivity(), ViewContact.class);
				startActivity(i);

			}
		});

		//populate list view and set an adapter to it
		return rootView;
	}

	protected abstract void refresh();

	@Override
	public void onResume() {
		
		super.onResume();
		refresh();

	}
	
	@Override
	public void OrderChanged(SortEvent se) {
		
		//Get the new order of contacts from the sortevent fired
		sortOrder = se.getOrder();
		if (getActivity() != null) {
			refresh();
		}
		
	}
	
}
