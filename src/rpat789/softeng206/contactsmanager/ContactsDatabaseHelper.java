package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Help manage interaction with the database 
 * @author Richa Patel
 */

public class ContactsDatabaseHelper extends SQLiteOpenHelper{

	private SQLiteDatabase contactsDb;
	private static ContactsDatabaseHelper dbHelper;

	//Database version
	public static final int DATABASE_VERSION =1;

	//Database name
	public static final String DATABASE_NAME = "ContactsDatabase.db";

	//Contacts table name
	public static final String TABLE_CONTACTS = "ContactsTable";

	//Contacts table Columns
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

	//SQL string for contacts table
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

	//Array of sort listeners to update the contacts list
	private List<SortListener> listeners = new ArrayList<SortListener>();

	private String sortOrder = null;

	private ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * method to return the singleton object of ContactsDatabaseHelper
	 * @param c context that uses the database
	 * @return ContactsDatabaseHelper if it already exists or a new object of ContactsDatabaseHelper class
	 */
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

		db.execSQL(CREATE_CONTACTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		onCreate(db);

	}

	/**
	 * Open the database for writing
	 */
	private void open() {

		contactsDb = dbHelper.getWritableDatabase();

	}

	/**Insert contact information into the database 
	 * @param first 
	 * @param last
	 * @param mobile
	 * @param homePh
	 * @param workPh
	 * @param email
	 * @param homeAdd
	 * @param workAdd
	 * @param dob
	 * @param group one of the five groups 
	 * @param image as a byte array
	 * @return 
	 */
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

	/**
	 * Returns all rows in the database
	 * @param orderType ordering type of the contacts (First name/last name/mobile number)
	 * @return cursor object to all rows
	 */
	public Cursor getAllData(String orderType) {

		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, orderType);

	} 

	/**
	 * Returns all rows of contacts that are favourites, ie. value 1 in the database 
	 * @param orderType ordering type of the contacts (First name/last name/mobile number)
	 * @return cursor object to favourite contacts
	 */
	public Cursor getFavoritesData(String orderType) {

		dbHelper.open();
		Log.d("testing", "orderType is "+orderType);
		return contactsDb.query(TABLE_CONTACTS, null, FAVOURITES + "= 1", null, null, null, orderType);

	}

	/**
	 * Returns all rows of contacts that are in a particular group 
	 * @param orderType ordering type of the contacts (First name/last name/mobile number)
	 * @param groupName name of the group contacts are filtered by
	 * @return cursor object to group data of a specific group
	 */
	public Cursor getGroupData(String groupName, String orderType) {

		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, GROUPS + "= ?", new String[]{groupName}, null, null, orderType);

	}

	/**
	 * Deletes all contacts
	 * @return
	 */
	public int deleteAll(){

		return contactsDb.delete(TABLE_CONTACTS, null, null);

	}

	/**
	 * Returns the row with the contact information specified by an id
	 * @param id the of the contact in the database
	 * @return
	 */
	public Cursor getContact(String id) {

		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, CONTACTS_ID + "= ?", new String[]{id}, null, null, null);

	}

	/**
	 * Deletes a contact from the database with if specified as a parameter
	 * @param id to the row of the contact information in the database
	 */
	public void deleteContact(String id) {

		this.getWritableDatabase().delete(TABLE_CONTACTS, CONTACTS_ID + " = " + id, null);

	}

	/**
	 * Sets/unsets a favourite contact from what it was previously (1 if it was 0, 0 if it was 1)
	 * @param id of the contact/row, whose favourite column is updated in the database
	 * @return boolean variable that indicates if a contact is a favourite or not after updating
	 */
	public boolean updateFavourite(String id) {

		dbHelper.open();

		Cursor c = getContact(id);
		c.moveToFirst();

		int f = c.getInt(10);
		int setAs = (f == 0 ) ? 1 : 0;  //sets f as 1 if it is 0, or 0 if it is 1

		ContentValues fav = new ContentValues();
		fav.put(FAVOURITES, setAs); 

		//updates the favourites column for that contact
		contactsDb.update(TABLE_CONTACTS, fav, CONTACTS_ID + "=" + id, null); 

		return (setAs == 1) ? true : false;

	}

	/**
	 * Updates a row representing a contact in the database
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param mobNum
	 * @param homePh
	 * @param workPh
	 * @param emailAddress
	 * @param homeAddress
	 * @param workAddress
	 * @param dateOfBirth
	 * @param group one of the five groups 
	 * @param image as a byte array
	 */
	public void updateContact(String id, String firstName, String lastName, String mobNum,
			String homePh, String workPh, String emailAddress,
			String homeAddress, String workAddress, String dateOfBirth, String groupName, byte[] image) {

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
		newValues.put(IMAGE, image);

		contactsDb.update(TABLE_CONTACTS, newValues, CONTACTS_ID + "=" + id, null);

	}

	/**
	 * Sorts contacts in the database according to the sort order specified
	 * @param order of the contacts required
	 */
	public void sortContacts(String order) {

		dbHelper.open();
		sortOrder = order;
		contactsDb.query(TABLE_CONTACTS, null, null, null, null, null, sortOrder);	

		fireSortEvent(); //listeners are informed of the change

	}

	/**
	 * Adds a sort listener to the list of listeners
	 * @param l listener to be added
	 */
	public void addSortListener(SortListener l) {

		listeners.add(l);

	}

	/**
	 * Removes a listener from the list of listeners
	 * @param l listener to be removed
	 */
	public void removeSortListener(SortListener l) {

		listeners.remove(l);

	}

	/**
	 * Informs all listeners that the sort order has changed 
	 */
	private void fireSortEvent() {

		SortEvent event = new SortEvent(sortOrder);

		for(SortListener l : listeners){
			l.OrderChanged(event);
		}

	}
}