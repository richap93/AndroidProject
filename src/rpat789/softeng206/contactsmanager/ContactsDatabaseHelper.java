package rpat789.softeng206.contactsmanager;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDatabaseHelper extends SQLiteOpenHelper{

	private Context context;
	private SQLiteDatabase contactsDb;
	private static ContactsDatabaseHelper dbHelper;

	//Database version
	public static final int DATABASE_VERSION =1;

	//Database name
	public static final String DATABASE_NAME = "ContactsDatabase.db";

	//Cars table name
	public static final String TABLE_CONTACTS = "ContactsTable";

	//Cars table Columns
	public static final String CONTACTS_ID = "_id";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String MOBILE_PHONE = "mobilePhone";
	public static final String HOME_PHONE = "homePhone";
	public static final String WORK_PHONE = "workPhone";
	public static final String EMAIL = "email";
	public static final String HOME_ADDRESS = "homeAddress";
	public static final String WORK_ADDRESS = "workAddress";
	public static final String DOB = "dob";
	public static final String FAVOURITES = "favourites";
	public static final String GROUPS = "groups";
	public static final String IMAGE = "image";

	//		public static final String PHOTO = "photo";

	//SQL string for cars table
	public static final String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + " ("
			+ CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ FIRST_NAME + " TEXT, " 
			+ LAST_NAME + " TEXT, " 
			+ MOBILE_PHONE + " TEXT, " 
			+ HOME_PHONE + " TEXT, " 
			+ WORK_PHONE + " TEXT, " 
			+ EMAIL + " TEXT, " 
			+ HOME_ADDRESS + " TEXT, " 
			+ WORK_ADDRESS + " TEXT, " 
			+ DOB + " TEXT, " 
			+ FAVOURITES + " INTEGER, " 
			+ GROUPS + " TEXT, " 
			+ IMAGE + " BLOB);";
	
	private List<SortListener> listeners = new ArrayList<SortListener>();
	private String sortOrder = null;

	private ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		context.deleteDatabase(DATABASE_NAME);
	}

	public static ContactsDatabaseHelper getDatabase(Context c) {
		if (dbHelper == null) {
			dbHelper = new ContactsDatabaseHelper(c);
			return dbHelper;
		} else {
			return dbHelper;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		onCreate(db);

	}


	//	public CustomAdapter(Context c) {
	//		context = c;
	//	}
	//		
	private ContactsDatabaseHelper open() {
		contactsDb = dbHelper.getWritableDatabase();
		return this;
	}

	//	public void close() {
	//		dbHelper.close();
	//	}

	public long insertContact(String first, String last, String mobile, String homePh, String workPh, String email,
			String homeAdd, String workAdd, String dob, String group, byte[] image) {
		
		dbHelper.open();
		
		ContentValues cv = new ContentValues();
		cv.put(FIRST_NAME, first);
		cv.put(LAST_NAME, last);
		cv.put(MOBILE_PHONE, mobile);
		cv.put(HOME_PHONE, homePh);
		cv.put(WORK_PHONE, workPh);
		cv.put(EMAIL, email);
		cv.put(HOME_ADDRESS, homeAdd);
		cv.put(WORK_ADDRESS, workAdd);
		cv.put(DOB, dob);
		cv.put(FAVOURITES, 0);
		cv.put(GROUPS, group);
		cv.put(IMAGE, image);
				
		Log.d("testing", "group inserted is" + group);
		return contactsDb.insert(TABLE_CONTACTS, null, cv);

	}

	public Cursor getAllData(String orderType) {
		dbHelper.open();
		String buildSQL = "SELECT * FROM "+ this.TABLE_CONTACTS;
		//		String[] columns = {CONTACTS_ID, FIRST_NAME, LAST_NAME, MOBILE_PHONE, HOME_PHONE, WORK_PHONE,
		//				EMAIL, HOME_ADDRESS, WORK_ADDRESS, DOB};
		if (contactsDb == null) {
			System.out.println("no DB!!!!!!!!!!");
		}
				return contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, orderType);
		//return dbHelper.getReadableDatabase().rawQuery(buildSQL, null);
	} 
	
	public Cursor getFavoritesData(String orderType) {
		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, FAVOURITES + "= 1", null, null, null, orderType);
	}

	public Cursor getGroupData(String groupName, String orderType) {
		// TODO Auto-generated method stub
		dbHelper.open();
		Log.d("testing", "GROUP SELECTED IS "+ groupName);
		return contactsDb.query(TABLE_CONTACTS, null, GROUPS + "= ?", new String[]{groupName}, null, null, null, null);
	}

	public int deleteAll(){

		return contactsDb.delete(TABLE_CONTACTS, null, null);

	}
	
	public Cursor getContact(String id) {
		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, CONTACTS_ID + "= ?", new String[]{id}, null, null, null);
		
	}

	public void deleteContact(String id) {
		// TODO Auto-generated method stub
		int num = this.getWritableDatabase().delete(TABLE_CONTACTS, CONTACTS_ID + " = " + id, null);
	}
	
	public boolean updateFavourite(String id) {
		
		dbHelper.open();
		
		Cursor c = getContact(id);
		c.moveToFirst();
		
		int f = c.getInt(10);
		int setAs = (f == 0 ) ? 1 : 0; 
		
	    ContentValues fav = new ContentValues();
	    fav.put(FAVOURITES, setAs);
	    
	    contactsDb.update(TABLE_CONTACTS, fav, CONTACTS_ID + "=" + id, null);
	    
	    return (setAs == 1) ? true : false;

	}

	public void updateContact(String id, String firstName, String lastName, String mobNum,
			String homePh, String workPh, String emailAddress,
			String homeAddress, String workAddress, String dateOfBirth, String groupName) {
		// TODO Auto-generated method stub
		dbHelper.open();
	    ContentValues newValues = new ContentValues();
	    newValues.put(FIRST_NAME, firstName);
	    newValues.put(LAST_NAME, lastName);
	    newValues.put(MOBILE_PHONE, mobNum);
	    newValues.put(HOME_PHONE, homePh);
	    newValues.put(WORK_PHONE, workPh);
	    newValues.put(EMAIL, emailAddress);
	    newValues.put(HOME_ADDRESS, homeAddress);
	    newValues.put(WORK_ADDRESS, workAddress);
	    newValues.put(DOB, dateOfBirth);
	    newValues.put(GROUPS, groupName);
	    
	    contactsDb.update(TABLE_CONTACTS, newValues, CONTACTS_ID + "=" + id, null);
	}

//	public Cursor sortByFirstName() {
//		// TODO Auto-generated method stub
//		dbHelper.open();
//		Cursor cursor = contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, FIRST_NAME);		
//		return cursor;
//	}
//	
//	public Cursor sortByLastName() {
//		// TODO Auto-generated method stub
//		dbHelper.open();
//		Cursor cursor = contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, LAST_NAME);		
//		return cursor;
//	}
//	
//	public Cursor sortByNumber() {
//		// TODO Auto-generated method stub
//		dbHelper.open();
//		Cursor cursor = contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, MOBILE_PHONE);		
//		return cursor;
//	}
	
	public void sortContacts(String order) {
		// TODO Auto-generated method stub
		dbHelper.open();
		sortOrder = order;
		contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, sortOrder);	
		fireSortEvent();
	}
	
	public void addSortListener(SortListener l){
		listeners.add(l);
	}
	
	public void removeSortListener(SortListener l){
		listeners.remove(l);
	}
	private void fireSortEvent() {
		SortEvent event = new SortEvent(sortOrder);
		
		for(SortListener l : listeners){
			l.OrderChanged(event);
		}
	}




	
//
//	public Cursor queueAll() {
//		String[] columns = {CONTACTS_ID, FIRST_NAME, LAST_NAME, MOBILE_PHONE, HOME_PHONE, WORK_PHONE,
//				EMAIL, HOME_ADDRESS, WORK_ADDRESS, DOB};
//		Cursor c = contactsDb.query(TABLE_CONTACTS, columns, null, null, null, null, null);
//		return c;
//	}

}
