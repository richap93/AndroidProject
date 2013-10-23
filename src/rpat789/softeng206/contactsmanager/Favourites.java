package rpat789.softeng206.contactsmanager;

public class Favourites extends TabsAbstract {
	
	public Favourites() {
		super();
	}

	protected void refresh() {
		
		cursor = dbHelper.getFavoritesData(sortOrder);
		cursor.moveToFirst();
		clAdapter = new CursorListAdapter(getActivity(), cursor);
		listView.setAdapter(clAdapter);
		
	}

}