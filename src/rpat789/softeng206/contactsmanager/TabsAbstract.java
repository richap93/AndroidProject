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

	abstract void refresh();

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh();

	}
	
	@Override
	public void OrderChanged(SortEvent se) {
		// TODO Auto-generated method stub
		sortOrder = se.getOrder();
		if (getActivity() != null) {
			refresh();
		}
	}
}
