package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class EditContact extends AddAbstract {

	private Cursor c;
	private String firstName, lastName, fullName, id;
	private List<TextView> tvs = new ArrayList<TextView>();
	private ContactsDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//gets intent and id that is passed to the intent
		Intent i = getIntent();
		id = i.getStringExtra("ID");

		dbHelper = ContactsDatabaseHelper.getDatabase(EditContact.this);
		c = dbHelper.getContact(id); //cursor to the contact specified by the id

		//get the contact name and populate the rest of the view
		if (c.moveToFirst()) {
			firstName = c.getString(1);
			lastName = c.getString(2);
			fullName = firstName + " " + lastName;
			setTitle(fullName);
			populateView(c);
		}
		
		//Goes back to the previous activity on back button click (next to launcher icon)
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	/**
	 * Populates the text views with what is currently in the database
	 * @param c
	 */
	private void populateView(Cursor c){

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

		//Access the image in the database
		c.moveToFirst();
		photo = c.getBlob(12);

		//sets an image to the image button if the user has taken a photo 
		if (photo != null) {
			Bitmap bm = BitmapFactory.decodeByteArray(photo, 0, photo.length);
			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth()); //crops image
			image.setImageBitmap(bm); 
		}

		//Set text for all the TextViews
		TextView tv;
		int j;
		for (int i = 1; i < 10; i++) {
			j = i - 1;
			tv = tvs.get(j);
			tv.setText(c.getString(i));
		}
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		//Finish activity if save or cancel pressed
		
		switch (item.getItemId()) {
		case R.id.save_contact:

			getInfo();

			if (firstName.isEmpty()) {
				
				//A dialog box displayed if the user has not entered a first name
				 noFNameDialog();
				 
			} else {
				
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(EditContact.this);
				entry.updateContact(id, firstName, lastName, mobNum, homePh, workPh, emailAddress, 
						homeAddress, workAddress, dateOfBirth, groupName, photo);
				EditContact.this.finish();
				
			}
			break;

		case R.id.cancel_add:

			//Show dialog box to prompt user to confirm their decision
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
			dialogBuilder.setTitle("Warning");
			dialogBuilder.setMessage("Changes will be discarded");
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					//OK button clicked
					finish(); 
				}
			});
			
			dialogBuilder.setCancelable(true);
			dialogBuilder.show();
			
			break;
		}

		return (super.onOptionsItemSelected(item));
	}

}
