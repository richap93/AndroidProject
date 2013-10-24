package rpat789.softeng206.contactsmanager;

/**
 * favourites tab that displays all the contacts added to the database that are 
 * favourite contacts
 * @author Richa Patel
 */
public class Favourites extends TabsAbstract {
	
	public Favourites() {
		super();
	}

	//Refreshes view when the database is updated
	protected void refresh() {
		
		cursor = dbHelper.getFavoritesData(sortOrder);
		cursor.moveToFirst();
		clAdapter = new CursorListAdapter(getActivity(), cursor);
		listView.setAdapter(clAdapter);
		
	}

}