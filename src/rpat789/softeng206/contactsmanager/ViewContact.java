package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContact extends Activity {
	
	private Contact contact;
	private ContactsDatabaseHelper dbHelper;
	private String fName, lName, fullName;
	Cursor c;
	String id;
	List<TextView> tvs = new ArrayList<TextView>();
	TextView mobile, homeNum, workNum, email, homeAdd, workAdd, birthday;
	ImageButton mobileButton, homeButton, workButton, textMobile;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_contact);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		
		dbHelper = ContactsDatabaseHelper.getDatabase(ViewContact.this);
		c = dbHelper.getContact(id);

		if (c.moveToFirst()) {
			fName = c.getString(1);
			lName = c.getString(2);
			fullName = fName + " " + lName;
			setTitle(fullName);
			populateView(c);
		}
		
		mobileButton = (ImageButton)findViewById(R.id.call_mobile);
		homeButton = (ImageButton)findViewById(R.id.call_home);
		workButton = (ImageButton)findViewById(R.id.call_work);
		textMobile = (ImageButton)findViewById(R.id.message_mobile);
		
		mobileButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				String mob = mobile.getText().toString();
			    callIntent.setData(Uri.parse("tel:"+mob));
			    startActivity(callIntent);
			}
		});
		
		homeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				String mob = homeNum.getText().toString();
			    callIntent.setData(Uri.parse("tel:"+homeNum));
			    startActivity(callIntent);
			}
		});
		
		workButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				String mob = workNum.getText().toString();
			    callIntent.setData(Uri.parse("tel:"+workNum));
			    startActivity(callIntent);
			}
		});

		textMobile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mob = mobile.getText().toString();
				Intent toSend = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mob));
				startActivity(toSend);
			}
		});

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
		
		//Populate list of TextViews
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
				            break;
				        }
				}
			});
			
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			
		} else if (item.getItemId() == R.id.favourites_icon) {
			
			boolean isFav = dbHelper.updateFavourite(id);
			String change = " REMOVED from ";
			if (isFav){
				change = " ADDED to ";
			}
			
			Toast.makeText(ViewContact.this,fullName + change + "favourites" , Toast.LENGTH_LONG).show();
			finish();
			
			
		} else if (item.getItemId() == R.id.edit_icon) {
			
			Intent i = new Intent();
			i.putExtra("ID", id);
			i.setClass(ViewContact.this, EditContact.class);
			startActivity(i);
			
			finish();
			
		}

		return (super.onOptionsItemSelected(item));
	}

}
