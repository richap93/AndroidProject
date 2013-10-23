package rpat789.softeng206.contactsmanager;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddContact extends Activity {

	ImageButton imageButton;
	TextView fName, lName, homeNum, workNum, mobile, homeAdd, workAdd, email, birthday;
	Spinner group;
	Bitmap bmp;
	String selectedImagePath;
	byte[] photo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		setUpContactButton();
		setTextViews();

	}

	private void setTextViews() {

		//Access elements in the view
		fName = (TextView)findViewById(R.id.first_name);
		lName = (TextView)findViewById(R.id.last_name);
		homeNum = (TextView)findViewById(R.id.item_home_number);
		workNum = (TextView)findViewById(R.id.item_work_number);
		mobile = (TextView)findViewById(R.id.item_mobile_number);
		homeAdd = (TextView)findViewById(R.id.item_home_addr);
		workAdd = (TextView)findViewById(R.id.item_work_addr);	
		email = (TextView)findViewById(R.id.item_email_addr);
		birthday = (TextView)findViewById(R.id.birthday);
		group = (Spinner)findViewById(R.id.group_spinner);
		
	}

	private void setUpContactButton() {

		imageButton = (ImageButton)findViewById(R.id.contact_image);

		imageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
				dialogBuilder.setTitle("Contact photo");
				dialogBuilder.setItems(R.array.camera_options, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// the user clicked on sort_options[which]

						if (which == 0) {
							
							Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
							startActivityForResult(i, 2);
							
						} else if (which == 1) {
							
							Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
							startActivityForResult(intent, 0);
						}

					}
				});
				dialogBuilder.create().show();
			}
		});

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	/**
	 * Adds the selected image to the view and database
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 0) {

			Bundle extras = data.getExtras();
			Bitmap bmp = (Bitmap) extras.get("data");

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
			photo = stream.toByteArray();
			imageButton.setImageBitmap(bmp);


		} else if (requestCode == 2 && resultCode == RESULT_OK && null != data){

			Log.d("testing", "Selected gallery");
			Uri selectedImage = data.getData();
			String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePath[0]);
			String picturePath = c.getString(columnIndex);
			c.close();
			Bitmap bmp = (BitmapFactory.decodeFile(picturePath));

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
			photo = stream.toByteArray();

			imageButton.setImageBitmap(bmp);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
		
	}

	/**
	 * Saves contact information or displays a dialog if cancel selected in menu bar
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		//Finish activity if save or cancel pressed
		
		switch (item.getItemId()) {
		case R.id.save_contact:

			//Get the information entered by the user
			String firstName = fName.getText().toString();
			String lastName = lName.getText().toString();
			String mobNum = mobile.getText().toString();
			String homePh = homeNum.getText().toString();
			String workPh = workNum.getText().toString();
			String homeAddress = homeAdd.getText().toString();
			String workAddress = workAdd.getText().toString();
			String emailAddress = email.getText().toString();
			String dateOfBirth = birthday.getText().toString();
			String groupName = group.getSelectedItem().toString();

			if (firstName.equals("")) {
				//A dialog box displayed if the user has not entered a first name
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Error");
				builder.setMessage("Please enter a first name");
				builder.setPositiveButton("OK", null);
				AlertDialog dialog = builder.show();
				
			} else {
				//Contact information is saved to the database
				
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(AddContact.this);
				entry.insertContact(firstName, lastName, mobNum, homePh, workPh, emailAddress, homeAddress, workAddress, dateOfBirth, groupName, photo);
				Toast.makeText(AddContact.this, firstName + " has been added to contacts", Toast.LENGTH_LONG).show();
				AddContact.this.finish(); //returns to the previous screen
				
			}
			
			break;

		case R.id.cancel_add:
			//Dialog box displayed to the user to prompt them to confirm their decision
			
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
