package rpat789.softeng206.contactsmanager;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AllContacts extends Fragment implements SortListener {


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
	cursor = dbHelper.getAllData(sortOrder);
//	}

	getActivity().startManagingCursor(cursor);

	//add stuff to listView
	from = new String[] {dbHelper.FIRST_NAME, dbHelper.LAST_NAME, dbHelper.MOBILE_PHONE, dbHelper.CONTACTS_ID};
	to = new int[]{R.id.list_item_text_contact_first, R.id.list_item_text_contact_last,  R.id.list_item_text_number, R.id.contact_id}; 

	SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.custom_list_view, cursor, from, to);

	
	listView.setAdapter(cursorAdapter);
}
}


//	public void setUpListView() {
		//		adapter.open();
		//		adapter.deleteAll();
		//		adapter.insertContact("Richa", "Patel", "0211095202", "6257766", "123456", "abc@gmail.com", "Home sweet home", "Google", "5/7/93");
		//		adapter.insertContact("Nikita", "Kabra", "021258742", "6272101", "2692686", "def@gmail.com", "Iberia g", "Microsoft", "22/09/93");


		//		contactList = new ArrayList<Contact>();
		//		
		//		contactList.add(new Contact("Alice", "Lee", "15/10/1991", "6255521", "6254477", "0211011231", "1 Alice Avenue, Mt Roskill", null, "alice@gmail.com"));
		//		contactList.add(new Contact("Abby", "Gail", null, "6245251", "6879477", "0211000231", "52 King Avenue, Remuera", null, "abby.gail@hotmail.com"));
		//		contactList.add(new Contact("Barry", null, null, "8355251", "5239477", "02255412310", "21 Oakdale Road, Hillsborough", null, "barry@gmail.com"));
		//		contactList.add(new Contact("Bob", "Bailey", "21/01/1993", "4265251", "2254477", "0276510652", null, null, null));
		//		contactList.add(new Contact("Billy", "Blob", null, "4264561", null, "021125652", null, null, null));
		//		contactList.add(new Contact("Chris", "Chan", null, "5432585", "6284561", "0274821152", "2 Clifford Road, Meadowbank", null, null));
		//		contactList.add(new Contact("Dave", "Wilson", null, "5445585", "4878562", "0212451265", "10 Lockie Ave, Mt Eden", null, "dave0221@hotmail.com"));
		//		contactList.add(new Contact("Jo", "Diver", null, "5432585", "8256561", "0224115791", null, "211 Manukau Road, Royal Oak", null));
		//		contactList.add(new Contact("Harry", "McKenzie", null, null, "6200141", null, null, null, null));
		//		contactList.add(new Contact("Nikita", "Kabra", null, "6254170", null, "0211005214", "10 Halsey Drive Road, Lynfield", null, "ilovepurple@hotmail.com"));
		//		contactList.add(new Contact("Rachel", null , null, null, null, "0274589632", null, null, null));
		//		contactList.add(new Contact("Zac", "Green", null, "5478415", "6528162", null, null, null, null));
		//		
		//		ListAdapter listAdapter = new CustomListAdapter(getActivity(), contactList);
		//		listView.setAdapter(listAdapter);


//	} 

//	private class CustomListAdapter extends ArrayAdapter<Contact> {
//
//		private Context context;
//		private List<Contact> contacts;
//
//		CustomListAdapter(Context context, List<Contact> contacts) {
//			super(context, android.R.layout.simple_list_item_1 ,contacts);
//
//			this.context = context;
//			this.contacts = contacts;
//
//		}
//	}
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			//Create a layout inflater  to inflate our xml layout for each item in the list
//			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//			//Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified
//			View listItemView = inflater.inflate(R.layout.custom_list_view, null);
//
//			//Access textview elements inside the view (Note we must specify the parent view to look in)
//			TextView contactName = (TextView)listItemView.findViewById(R.id.list_item_text_contact);
//			TextView number = (TextView)listItemView.findViewById(R.id.list_item_text_number);

			//Set the text for each textview(use the position argument to find the appropriate element in the list)
//			contactName.setText(contacts.get(position).getName());
//			number.setText(contacts.get(position).getMobNumber());

//			return listItemView;
//		}
//
//	}

