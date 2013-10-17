package rpat789.softeng206.contactsmanager;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddContact extends Activity {
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		setUpContactButton();
		setTextViews();

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
		
	}
	
	private void setUpContactButton() {
		
		imageButton = (ImageButton)findViewById(R.id.contact_image);
		
		imageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
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
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
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
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(AddContact.this);
//				entry.open();
				entry.insertContact(firstName, lastName, mobNum, homePh, workPh, emailAddress, homeAddress, workAddress, dateOfBirth);
				entry.close();
				Toast.makeText(AddContact.this, firstName + " has been added g!", Toast.LENGTH_LONG).show();
				finish();
			}
			break;
		case R.id.cancel_add:
			
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
			dialogBuilder.setTitle("Warning");
			dialogBuilder.setMessage("Changes will be discarded");
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				    //OK button clicked
		        	Toast.makeText(AddContact.this, "Contact deleted", Toast.LENGTH_LONG).show();
					finish(); 
				}
			});
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			break;
		}
		
		return (super.onOptionsItemSelected(item));
	}

}
