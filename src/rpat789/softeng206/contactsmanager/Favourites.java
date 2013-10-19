package rpat789.softeng206.contactsmanager;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Favourites extends Fragment implements SortListener {

	private ListView listView;
	List<Contact> contactList;
	Cursor cursor;
	private ContactsDatabaseHelper dbHelper;
	private String[] from;
	private int[] to;
	private String sortOrder = "firstName";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_main, container, false);
		super.onCreate(savedInstanceState);

		dbHelper = ContactsDatabaseHelper.getDatabase(getActivity());
		dbHelper.addSortListener(this);

		listView = (ListView)rootView.findViewById(R.id.main_listView);
		refresh();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				// TODO Auto-generated method stub
				//				Intent intent = new Intent(getActivity(), ViewContact.class);
				//				intent.putExtra("selectedContact", contactList.get(clickedViewPosition));

				//get the whole view of the item in list
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

		//populate list view and set an adapter to it
		return rootView;
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	//	private void refreshAdapter() {
	//		Cursor data = cdHelper.getAllData(sortOrder);
	//		adapter = new ContactsListAdapter(context, data);
	//		lv_CList.setAdapter(adapter);
	//	}

	@Override
	public void OrderChanged(SortEvent se) {
		// TODO Auto-generated method stub
		sortOrder = se.getOrder();
		refresh();

	}

	private void refresh() {
		// TODO Auto-generated method stub
		cursor = dbHelper.getFavoritesData(sortOrder);
		//	}

		getActivity().startManagingCursor(cursor);

		//add stuff to listView
		from = new String[] {dbHelper.FIRST_NAME, dbHelper.LAST_NAME, dbHelper.MOBILE_PHONE, dbHelper.CONTACTS_ID};
		to = new int[]{R.id.list_item_text_contact_first, R.id.list_item_text_contact_last,  R.id.list_item_text_number, R.id.contact_id}; 

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.custom_list_view, cursor, from, to);

		listView.setAdapter(cursorAdapter);
	}
}