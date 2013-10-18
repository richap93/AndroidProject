package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditContact extends Activity {
	
	ImageButton imageButton;
	TextView fName;
	TextView lName;
	TextView homeNum;
	TextView workNum;
	TextView mobile;
	TextView homeAdd;
	TextView workAdd;
	TextView email;
	TextView birthday;
	Cursor c;
	String id;
	private ContactsDatabaseHelper dbHelper;
	private String firstName, lastName, fullName;
	List<TextView> tvs = new ArrayList<TextView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		setUpContactButton();
		setTextViews();
		
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		
		dbHelper = ContactsDatabaseHelper.getDatabase(EditContact.this);
		c = dbHelper.getContact(id);

		if (c.moveToFirst()) {
			firstName = c.getString(1);
			lastName = c.getString(2);
			fullName = firstName + " " + lastName;
			setTitle(fullName);
			populateView(c);
		}

	}
	
	private void populateView(Cursor c){
		
		//Access textview elements inside the view (Note we must specify the parent view to look in)
		fName = (TextView)findViewById(R.id.first_name);
		lName = (TextView)findViewById(R.id.last_name);
		mobile = (TextView)findViewById(R.id.item_mobile_number);
		homeNum = (TextView)findViewById(R.id.item_home_number);
		workNum = (TextView)findViewById(R.id.item_work_number);
		email = (TextView)findViewById(R.id.item_email_addr);
		homeAdd = (TextView)findViewById(R.id.item_home_addr);
		workAdd = (TextView)findViewById(R.id.item_work_addr);	
		birthday = (TextView)findViewById(R.id.birthday);
		
		//Populate list of TextViews
		tvs.add(fName);
		tvs.add(lName);
		tvs.add(mobile);
		tvs.add(homeNum);
		tvs.add(workNum);
		tvs.add(email);
		tvs.add(homeAdd);
		tvs.add(workAdd);
		tvs.add(birthday);
		
		//Set text for all the TextViews
		TextView tv;
		int j;
		for (int i = 1; i < 10; i++) {
			j = i - 1;
			tv = tvs.get(j);
			tv.setText(c.getString(i));
		}
	}
	
	private void setTextViews() {
		
		fName = (TextView)findViewById(R.id.first_name);
		lName = (TextView)findViewById(R.id.last_name);
		homeNum = (TextView)findViewById(R.id.item_home_number);
		workNum = (TextView)findViewById(R.id.item_work_number);
		mobile = (TextView)findViewById(R.id.item_mobile_number);
		homeAdd = (TextView)findViewById(R.id.item_home_addr);
		workAdd = (TextView)findViewById(R.id.item_work_addr);	
		email = (TextView)findViewById(R.id.item_email_addr);
		birthday = (TextView)findViewById(R.id.birthday);	
//			contactId = (TextView)findViewById(R.id.contact_id);
	}
	
	private void setUpContactButton() {
		
		imageButton = (ImageButton)findViewById(R.id.contact_image);
		
		imageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
				dialogBuilder.setTitle("Contact photo");
				dialogBuilder.setItems(R.array.camera_options, new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        // the user clicked on sort_options[which]
				    }
				});
				dialogBuilder.create().show();
			}
		});
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		//Finish activity if save or cancel pressed
		switch (item.getItemId()) {
		case R.id.save_contact:
			
			String firstName = fName.getText().toString();
			String lastName = lName.getText().toString();
			String mobNum = mobile.getText().toString();
			String homePh = homeNum.getText().toString();
			String workPh = workNum.getText().toString();
			String homeAddress = homeAdd.getText().toString();
			String workAddress = workAdd.getText().toString();
			String emailAddress = email.getText().toString();
			String dateOfBirth = birthday.getText().toString();
			
			if (firstName.equals("")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Error");
				builder.setMessage("Please enter a first name");
				builder.setPositiveButton("OK", null);
				AlertDialog dialog = builder.show();
			} else {
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(EditContact.this);
//				entry.open();
				entry.updateContact(id, firstName, lastName, mobNum, homePh, workPh, emailAddress, homeAddress, workAddress, dateOfBirth);
//				entry.
				EditContact.this.finish();
			}
			break;
			
		case R.id.cancel_add:
			
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
			dialogBuilder.setTitle("Warning");
			dialogBuilder.setMessage("Changes will be discarded");
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				    //OK button clicked
		        	Toast.makeText(EditContact.this, "Contact deleted", Toast.LENGTH_LONG).show();
					finish(); 
				}
			});
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			break;
		}
		
		return (super.onOptionsItemSelected(item));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}