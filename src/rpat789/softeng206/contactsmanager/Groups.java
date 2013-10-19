package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Groups extends Fragment implements SortListener {

	private ListView lvGroups;
	List<String> groupOptions;
	private String sortOrder = "firstName";
	private String group = null;
	Cursor cursor;
	private ContactsDatabaseHelper dbHelper;
	private String[] from;
	private int[] to;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_groups, container, false);
		
		lvGroups = (ListView)rootView.findViewById(R.id.groups_listView);
		
		dbHelper = ContactsDatabaseHelper.getDatabase(getActivity());
		dbHelper.addSortListener(this);

		setUpListView();
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				// TODO Auto-generated method stub

				String groupSelected = groupOptions.get(clickedViewPosition);
				group = groupSelected;
//				
				refresh();
			}
		});

		//populate list view and set an adapter to it
		return rootView;
	}

	public void setUpListView() {

		groupOptions = new ArrayList<String>();

		groupOptions.add("Not Assigned");
		groupOptions.add("Emergency Contacts");
		groupOptions.add("Family");
		groupOptions.add("Friends");
		groupOptions.add("Colleages");

		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, groupOptions);
		lvGroups.setAdapter(listAdapter);
	}

	@Override
	public void OrderChanged(SortEvent se) {
		// TODO Auto-generated method stub
		sortOrder = se.getOrder();
		refresh();
	} 
	

	private void refresh() {
		// TODO Auto-generated method stub

		cursor = dbHelper.getGroupData(group, sortOrder);
		
		cursor.moveToFirst();
		Log.d("testing", "Group is " + group);
		Log.d("testing", "Count is " + cursor.getCount());

		getActivity().startManagingCursor(cursor);

		//add stuff to listView
		from = new String[] {dbHelper.FIRST_NAME, dbHelper.LAST_NAME, dbHelper.MOBILE_PHONE, dbHelper.CONTACTS_ID};
		to = new int[]{R.id.list_item_text_contact_first, R.id.list_item_text_contact_last,  R.id.list_item_text_number, R.id.contact_id}; 
 
		
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.custom_list_view, cursor, from, to);

		lvGroups.setAdapter(cursorAdapter);

	}
}