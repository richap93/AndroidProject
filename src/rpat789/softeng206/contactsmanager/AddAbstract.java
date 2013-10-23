package rpat789.softeng206.contactsmanager;

import java.io.ByteArrayOutputStream;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class AddAbstract extends Activity {
	
	protected TextView fName, lName, homeNum, workNum, mobile, homeAdd, workAdd, email, birthday;
	protected Spinner group;
	protected byte[] photo = null;
	protected ImageButton image;
	protected String firstName, lastName, mobNum, homePh, workPh,
	homeAddress, workAddress, emailAddress, dateOfBirth, groupName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//Set the layout of the view (note, add and edit have the same layout)
		setContentView(R.layout.add_contact);
		
		setTextViews();
		setUpContactButton();
		
		//Go back to previous activity on back button
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	/**
	 * Initialise TextView fields
	 */
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

	/**
	 * Access image button and open camera application on button click
	 */
	private void setUpContactButton() {

		//Access image button on the view
		image = (ImageButton)findViewById(R.id.contact_image);

		//Set a listener that opens camera application and allows user to take photo
		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, 0);
				
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
		
	}
	
	/**
	 * When a button on camera application is pressed, the image on the view is set 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0 && resultCode == RESULT_OK) {

			Bundle extras = data.getExtras();
			Bitmap bmp = (Bitmap) extras.get("data");

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress image
			photo = stream.toByteArray();

			image.setImageBitmap(bmp); 

		}

	}
	
	/**
	 * Retrieve information from the entered input
	 */
	protected void getInfo() {
		
		firstName = fName.getText().toString();
		lastName = lName.getText().toString();
		mobNum = mobile.getText().toString();
		homePh = homeNum.getText().toString();
		workPh = workNum.getText().toString();
		homeAddress = homeAdd.getText().toString();
		workAddress = workAdd.getText().toString();
		emailAddress = email.getText().toString();
		dateOfBirth = birthday.getText().toString();
		groupName = group.getSelectedItem().toString();
		
	}
	
	/**
	 * Create dialog box if no first name entered by user
	 */
	protected void noFNameDialog() {
	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error");
		builder.setMessage("Please enter a first name");
		builder.setPositiveButton("OK", null);
		builder.show();
		
	}
	
}
