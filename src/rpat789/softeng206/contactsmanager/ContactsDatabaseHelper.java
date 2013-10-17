package rpat789.softeng206.contactsmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDatabaseHelper extends SQLiteOpenHelper {
	
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
//	public static final String PHOTO = "photo";

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
	
	
	public ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
