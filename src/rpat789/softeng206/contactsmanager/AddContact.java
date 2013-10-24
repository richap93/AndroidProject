package rpat789.softeng206.contactsmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class AddContact extends AddAbstract {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}

	/**
	 * Saves contact information or displays a dialog if cancel selected in menu bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Finish activity if save or cancel pressed
		
		switch (item.getItemId()) {
		case R.id.save_contact:

			getInfo();

			if (firstName.isEmpty()) {
				
				//A dialog box displayed if the user has not entered a first name
				noFNameDialog();
				
			} else {
				//Contact information is saved to the database
				
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(AddContact.this);
				entry.insertContact(firstName, lastName, mobNum, homePh, workPh, emailAddress, homeAddress, 
						workAddress, dateOfBirth, groupName, photo);
				Toast.makeText(AddContact.this, firstName + " has been added to contacts", Toast.LENGTH_LONG).show();
				AddContact.this.finish(); //returns to the previous screen
				
			}
			
			break;

		case R.id.cancel_add:
			
			//Dialog box displayed to the user to prompt them to confirm their decision
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
			dialogBuilder.setTitle("Warning");
			dialogBuilder.setMessage("Information will be discarded");
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
