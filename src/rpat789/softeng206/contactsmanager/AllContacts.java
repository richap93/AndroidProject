package rpat789.softeng206.contactsmanager;

/**
 * All Contacts tab that displays all the contacts added to the database
 * @author Richa Patel
 */
public class AllContacts extends TabsAbstract {

	public AllContacts() {
		super();
	}

	//Refreshes view when the database is updated
	protected void refresh() {
		
		cursor = dbHelper.getAllData(sortOrder);
		cursor.moveToFirst();
		clAdapter = new CursorListAdapter(getActivity(), cursor);
		listView.setAdapter(clAdapter);
		
	}

}

