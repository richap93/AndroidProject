package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContact extends Activity implements OnClickListener {

	private ContactsDatabaseHelper dbHelper;
	private String fName, lName, fullName;
	private Cursor c;
	private String id, mob, home, work, emailAdd;
	private List<TextView> tvs = new ArrayList<TextView>();
	private TextView mobile, homeNum, workNum, email, homeAdd, workAdd, birthday;
	private ImageButton mobileButton, homeButton, workButton, textMobile, emailContact;
	private ImageView image;
	private boolean isFav;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_contact);

		//Enables back button in the menu bar to return to the AllContacts screen
		getActionBar().setDisplayHomeAsUpEnabled(true);

		//Gets the ID of the contact to view from intent/previous activity
		Intent i = getIntent();
		id = i.getStringExtra("ID");

		dbHelper = ContactsDatabaseHelper.getDatabase(ViewContact.this);

		//Cursor to the row of the contact information that corresponds to that id
		c = dbHelper.getContact(id);


		if (c.moveToFirst()) {
			fName = c.getString(1);
			lName = c.getString(2);
			fullName = fName + " " + lName;
			setTitle(fullName);
			populateView(c);
		}

		//Access buttons (Note we must specify the parent view to look in)
		mobileButton = (ImageButton)findViewById(R.id.call_mobile);
		homeButton = (ImageButton)findViewById(R.id.call_home);
		workButton = (ImageButton)findViewById(R.id.call_work);
		textMobile = (ImageButton)findViewById(R.id.message_mobile);
		emailContact = (ImageButton)findViewById(R.id.email_icon);

		//Get numbers and email 
		mob = mobile.getText().toString();
		home = homeNum.getText().toString();
		work = workNum.getText().toString();
		emailAdd = email.getText().toString();

		//set setOnClickListener's for all buttons
		mobileButton.setOnClickListener(this);
		homeButton.setOnClickListener(this);
		workButton.setOnClickListener(this);
		textMobile.setOnClickListener(this);
		emailContact.setOnClickListener(this);

	}

	/**
	 * Implements actions perfomed on each button click
	 */
	@Override
	public void onClick(View v) {

		Intent i = new Intent();
		i.setAction(Intent.ACTION_CALL);
		String uriString = "";

		switch(v.getId()){

		//Call button next to mobile selected
		case R.id.call_mobile:
			if(!(mob.equals(""))) {
				uriString = "tel:" + mob;
			} else {
				Toast.makeText(getApplicationContext(), "No number", Toast.LENGTH_LONG).show();
				return;
			}
			break;

			//Call button next to home selected
		case R.id.call_home:
			if(!(home.equals(""))) {
				uriString = "tel:" + home;
			} else {
				Toast.makeText(getApplicationContext(), "No number", Toast.LENGTH_LONG).show();
				return;
			}
			break;

			//Call button next to work selected
		case R.id.call_work:
			if(!(work.equals(""))) {
				uriString = "tel:" + work;
			} else {
				Toast.makeText(getApplicationContext(), "No number", Toast.LENGTH_LONG).show();
				return;
			}
			break;

			//Message button next to mobile selected
		case R.id.message_mobile:
			if(!(mob.isEmpty())){
				uriString = "sms: " + mob;
				i.setAction(Intent.ACTION_VIEW);
			} else {
				Toast.makeText(getApplicationContext(), "No number", Toast.LENGTH_LONG).show();
				return;
			}
			break;

			//Email button next to email address selected
		case R.id.email_icon:

			if(!(emailAdd.equals(""))) {
				uriString = "mailto: " + emailAdd;
				i.setAction(Intent.ACTION_VIEW);
			} else {
				Toast.makeText(getApplicationContext(), "No email", Toast.LENGTH_LONG).show();
				return;
			}
			break;

		default:
			return;
		}

		i.setData(Uri.parse(uriString)); //parses the given string
		startActivity(i);

	}

	private void populateView(Cursor c){

		//Access textview elements inside the view (Note we must specify the parent view to look in)
		mobile = (TextView)findViewById(R.id.list_item_mobile_phone);
		homeNum = (TextView)findViewById(R.id.list_item_home_phone);
		workNum = (TextView)findViewById(R.id.list_item_work_phone);
		email = (TextView)findViewById(R.id.list_item_email);
		homeAdd = (TextView)findViewById(R.id.list_home_address);
		workAdd = (TextView)findViewById(R.id.list_work_address);	
		birthday = (TextView)findViewById(R.id.list_item_DOB);
		image = (ImageView)findViewById(R.id.contact_image_view);

		//Populate list of TextViews
		tvs.add(mobile);
		tvs.add(homeNum);
		tvs.add(workNum);
		tvs.add(email);
		tvs.add(homeAdd);
		tvs.add(workAdd);
		tvs.add(birthday);

		c.moveToFirst();

		byte[] contactImage = c.getBlob(12);

		//Sets the image for the contact
		if (contactImage != null) {
			Bitmap bm = BitmapFactory.decodeByteArray(contactImage, 0, contactImage.length);
			bm = Bitmap.createBitmap(bm, 0, 20, bm.getWidth(), (int) (bm.getWidth()*0.6));
			image.setImageBitmap(bm);
		} else {
			image.setImageResource(R.drawable.view_contact_icon);
		}

		//Set text for all the TextViews
		TextView tv;
		int j;
		for (int i = 3; i < 10; i++) {
			j = i - 3;
			tv = tvs.get(j);
			tv.setText(c.getString(i));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return (super.onCreateOptionsMenu(menu));

	}

	/**
	 * Implements actions performed on buttons clicked in the action bar
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.delete_contact) {

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewContact.this);
			dialogBuilder.setTitle("Delete");
			dialogBuilder.setMessage(fullName + " will be removed from contacts");
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						dbHelper.deleteContact(id);
						finish();
						Toast.makeText(ViewContact.this, "Contact deleted", Toast.LENGTH_LONG).show();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						break; //dialog box is closed
					}
				}
			});

			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();

		} else if (item.getItemId() == R.id.favourites_icon) {

			//Contact is added or deleted from the favourites
			isFav = dbHelper.updateFavourite(id);
			String change = " has been removed from ";
			if (isFav){
				change = " has been added to ";
			}

			Toast.makeText(ViewContact.this,fullName + change + "favourites" , Toast.LENGTH_LONG).show();
			invalidateOptionsMenu();

		} else if (item.getItemId() == R.id.edit_icon) {

			//Activity changed to EditContact
			Intent i = new Intent();
			i.putExtra("ID", id); //id of the contact in the database passed to the intent
			i.setClass(ViewContact.this, EditContact.class);
			startActivity(i);

			finish();

		}

		return (super.onOptionsItemSelected(item));
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem favIcon = menu.findItem(R.id.favourites_icon);
		if (isFav) {
			favIcon.setIcon(R.drawable.favourites_icon);
		} else {
			favIcon.setIcon(R.drawable.not_favourites_icon);
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
