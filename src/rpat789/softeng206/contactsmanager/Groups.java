package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Groups extends Fragment implements SortListener {

	private ListView lvGroups;
	private List<String> groupOptions;
	private String sortOrder = "firstName";
	private String group = null;
	private Cursor cursor;
	private ContactsDatabaseHelper dbHelper;
	boolean isGroupList;
	private CursorListAdapter clAdapter;
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_main, container, false);
		
		isGroupList = true;
		
		//Initialize groups list
		lvGroups = (ListView)rootView.findViewById(R.id.main_listView);
		
		//Opens database and register class as a listener
		dbHelper = ContactsDatabaseHelper.getDatabase(getActivity());
		dbHelper.addSortListener(this);

		setUpListView();
	    
		//If a group is selected the view is refreshed by changing its adapter
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				// TODO Auto-generated method stub

				//Retrieve the group name by its position from the list groupOptions
				if (isGroupList) {
					group = groupOptions.get(clickedViewPosition);
					refresh();
					isGroupList = false;
				}
			}
		});

		return rootView;
	}

	public void setUpListView() {

		groupOptions = new ArrayList<String>();

		//Add these to the list - same order as the dialog box so  group can 
		//be retrieved by the clicked view position
		groupOptions.add("Not Assigned");
		groupOptions.add("Emergency Contacts");
		groupOptions.add("Family");
		groupOptions.add("Friends");
		groupOptions.add("Colleages");

		//Set a list adapter when activity initially created so user can select a group
		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, groupOptions);
		lvGroups.setAdapter(listAdapter);
		
	}

	/**
	 * The view is refreshed if new items are added dynamically
	 */
	@Override
	public void OrderChanged(SortEvent se) {

		//View is only refreshed if it is the current tab open
		if (!(isGroupList) && (getActivity() != null)) {
			sortOrder = se.getOrder();
			refresh();
		}
		
	} 
	
	/**
	 * Changes the adapter for the list view to show the list of contacts in a 
	 * particular group. Listener is added to the list view so when a contact is 
	 * selected, ViewContact screen is displayed
	 */
	private void refresh() {
		
		//Retrieve data to be displayed by the listview
		cursor = dbHelper.getGroupData(group, sortOrder);
		
		cursor.moveToFirst();
		clAdapter = new CursorListAdapter(getActivity(), cursor); 
		
		//change adapter for the listView so layout is custom list view
		lvGroups.setAdapter(clAdapter); 
		
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {

				ViewGroup group = (ViewGroup)clickedView;

				//get the first name in that view
				View contactId = group.findViewById(R.id.contact_id);

				//Access contact id text view
				TextView fId = (TextView)contactId;
				
				//Get the id of the contact selected from the view and pass it to the 
				//ViewContact activity
				String idNum = fId.getText().toString();
				Intent i = new Intent();
				i.putExtra("ID", idNum);
				i.setClass(getActivity(), ViewContact.class);
				startActivity(i);

			}
		});

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!(isGroupList)) {
			refresh();
		}
	}
}