package rpat789.softeng206.contactsmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContact extends Activity {
	
	private Contact contact;
	private ContactsDatabaseHelper dbHelper;
	Cursor c;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_contact);
//		contact = (Contact) getIntent().getSerializableExtra("selectedContact");
//		setView();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		String id = i.getStringExtra("ID");
		
		dbHelper = ContactsDatabaseHelper.getDatabase(ViewContact.this);
		c = dbHelper.getContact(id);

		if (c.moveToFirst()) {
			String fName = c.getString(1);
			System.out.println(fName);
			setTitle(fName);
		}

		
	}

	private void setView(){
		
		//Access textview elements inside the view (Note we must specify the parent view to look in)
		TextView homeNum = (TextView)findViewById(R.id.list_item_home_phone);
		TextView workNum = (TextView)findViewById(R.id.list_item_work_phone);
		TextView mobile = (TextView)findViewById(R.id.list_item_mobile_phone);
		TextView homeAdd = (TextView)findViewById(R.id.list_home_address);
		TextView workAdd = (TextView)findViewById(R.id.list_work_address);	
		TextView email = (TextView)findViewById(R.id.list_item_email);
		TextView birthday = (TextView)findViewById(R.id.list_item_DOB);
		
		//Set the text for each textview(use the position argument to find the appropriate element in the list)
		workNum.setText(contact.getWorkNumber());
		mobile.setText(contact.getMobNumber());
		homeAdd.setText(contact.getHomeAddress());
		workAdd.setText(contact.getWorkAddress());
		email.setText(contact.getEmail());
		birthday.setText(contact.getDOB());
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return (super.onCreateOptionsMenu(menu));
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.delete_contact) {
			
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewContact.this);
			dialogBuilder.setTitle("Delete");
			dialogBuilder.setMessage(contact.getName() + " will be removed from contacts");
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	finish();
				        	Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_LONG).show();
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				}
			});
			
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			
		} else if (item.getItemId() == R.id.favourites_icon) {
			
			Toast.makeText(this,contact.getName() + " saved to favourites" , Toast.LENGTH_LONG).show();
			finish();
			
		}

		return (super.onOptionsItemSelected(item));
	}

}
