package rpat789.softeng206.contactsmanager;

public class AllContacts extends TabsAbstract {

	public AllContacts() {
		super();
	}

	protected void refresh() {
		
		cursor = dbHelper.getAllData(sortOrder);
		cursor.moveToFirst();
		clAdapter = new CursorListAdapter(getActivity(), cursor);
		listView.setAdapter(clAdapter);
		
	}

}

