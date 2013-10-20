package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
	List<String> groupOptions;
	private String sortOrder = "firstName";
	private String group = null;
	Cursor cursor;
	private ContactsDatabaseHelper dbHelper;
	private String[] from;
	private int[] to;
	boolean isGroupList;
	private CursorListAdapter clAdapter;
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_groups, container, false);
		isGroupList = true;
		lvGroups = (ListView)rootView.findViewById(R.id.groups_listView);
		
		dbHelper = ContactsDatabaseHelper.getDatabase(getActivity());
		dbHelper.addSortListener(this);

		setUpListView();
	    
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				// TODO Auto-generated method stub

				if (isGroupList) {
					String groupSelected = groupOptions.get(clickedViewPosition);
					group = groupSelected;
					refresh();
					isGroupList = false;
				}
			}
		});

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
		if (!(isGroupList) && (getActivity() != null)) {
			sortOrder = se.getOrder();
			refresh();
		}
	} 
	

	private void refresh() {
		// TODO Auto-generated method stub
		

		cursor = dbHelper.getGroupData(group, sortOrder);
		
		cursor.moveToFirst();

		clAdapter = new CursorListAdapter(getActivity(), cursor);
		
		lvGroups.setAdapter(clAdapter);
		
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				// TODO Auto-generated method stub
				ViewGroup group = (ViewGroup)clickedView;

				//get the first name in that view
				View contactId = group.findViewById(R.id.contact_id);

				TextView fId = (TextView)contactId;

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