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
	TextView fName;
	TextView lName;
	TextView homeNum;
	TextView workNum;
	TextView mobile;
	TextView homeAdd;
	TextView workAdd;
	TextView email;
	TextView birthday;
	Spinner group;
	//	TextView contactId;
	Bitmap bmp;
	String selectedImagePath;
	ImageButton image;
	byte[] photo = null;


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
		group = (Spinner)findViewById(R.id.group_spinner);
		image = (ImageButton)findViewById(R.id.contact_image);
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

						switch (which) {
						case 0:
							Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
							startActivityForResult(intent, 0);

						case 1:

						}

					}
				});
				dialogBuilder.create().show();
			}
		});

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			
			Bundle extras = data.getExtras();
			Bitmap bmp = (Bitmap) extras.get("data");
			
//			BitmapDrawable b = (BitmapDrawable)image.getDrawable();
//			Bitmap bmp = b.getBitmap();
			Uri selectedImageUri = data.getData();

			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
			photo = stream.toByteArray();
			
			
			
			
//			selectedImagePath = getPath(selectedImageUri);
//			Log.d("testing", selectedImagePath);

			//			COMPRESSION - TAZZY'S WAY
			//			FileOutputStream v;
			//			try {
			//				v = new FileOutputStream(new File(selectedImagePath));
			//				bmp.compress(Bitmap.CompressFormat.PNG, 100, v);
			//				v.close();
			//			} catch (FileNotFoundException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
//			bmp = decodeFile(new File(selectedImagePath));
			image.setImageBitmap(bmp);


		}
	}

//	private Bitmap decodeFile(File f){
//		try {
//			//Decode image size
//			BitmapFactory.Options o = new BitmapFactory.Options();
//			o.inJustDecodeBounds = true;
//			BitmapFactory.decodeStream(new FileInputStream(f),null,o);
//
//			//The new size we want to scale to
//			final int REQUIRED_SIZE=50;
//
//			//Find the correct scale value. It should be the power of 2.
//			int scale=1;
//			while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
//				scale*=2;
//
//			//Decode with inSampleSize
//			BitmapFactory.Options o2 = new BitmapFactory.Options();
//			o2.inSampleSize=scale;
//			Log.d("testing", "YAY!");
//			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
//		} catch (FileNotFoundException e) {}
//		return null;
//	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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
			String groupName = group.getSelectedItem().toString();

			if (firstName.equals("")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Error");
				builder.setMessage("Please enter a first name");
				builder.setPositiveButton("OK", null);
				AlertDialog dialog = builder.show();
			} else {
				ContactsDatabaseHelper entry = ContactsDatabaseHelper.getDatabase(AddContact.this);
				entry.insertContact(firstName, lastName, mobNum, homePh, workPh, emailAddress, homeAddress, workAddress, dateOfBirth, groupName, photo);
				Toast.makeText(AddContact.this, firstName + " has been added g!", Toast.LENGTH_LONG).show();
				AddContact.this.finish();
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
