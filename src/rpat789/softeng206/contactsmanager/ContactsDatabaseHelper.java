package rpat789.softeng206.contactsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

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
			+ DOB + " TEXT);";

	private ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
			String homeAdd, String workAdd, String dob) {
		
//		this.open();
		
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

		return contactsDb.insert(TABLE_CONTACTS, null, cv);

	}

	public Cursor getAllData() {
		String buildSQL = "SELECT * FROM "+ this.TABLE_CONTACTS;
		//		String[] columns = {CONTACTS_ID, FIRST_NAME, LAST_NAME, MOBILE_PHONE, HOME_PHONE, WORK_PHONE,
		//				EMAIL, HOME_ADDRESS, WORK_ADDRESS, DOB};
		//		Cursor c = contactsDb.query(TABLE_CONTACTS, columns, null, null, null, null, null);
		return dbHelper.getReadableDatabase().rawQuery(buildSQL, null);
	} 

	public int deleteAll(){

		return contactsDb.delete(TABLE_CONTACTS, null, null);

	}
	
	public Cursor getContact(String id) {
		dbHelper.open();
		return contactsDb.query(TABLE_CONTACTS, null, CONTACTS_ID + "= ?", new String[]{id}, null, null, null);
		
	}
	
//
//	public Cursor queueAll() {
//		String[] columns = {CONTACTS_ID, FIRST_NAME, LAST_NAME, MOBILE_PHONE, HOME_PHONE, WORK_PHONE,
//				EMAIL, HOME_ADDRESS, WORK_ADDRESS, DOB};
//		Cursor c = contactsDb.query(TABLE_CONTACTS, columns, null, null, null, null, null);
//		return c;
//	}

}
