package rpat789.softeng206.contactsmanager;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddContact extends Activity {
	
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		setUpContactButton();
		
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
		finish();
		return (super.onOptionsItemSelected(item));
	}

}
