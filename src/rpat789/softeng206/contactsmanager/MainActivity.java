package rpat789.softeng206.contactsmanager;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	ActionBar.Tab Tab1, Tab2, Tab3;
	Fragment fragmentTab1 = new AllContacts();
	Fragment fragmentTab3 = new Groups();
	Fragment fragmentTab2 = new Favourites();
	AlertDialog levelDialog;
	ContactsDatabaseHelper dbHelper;
	Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
 
        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("All Contacts");
        Tab3 = actionBar.newTab().setText("Groups");
        Tab2 = actionBar.newTab().setText("Favourites");
        
 
        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(fragmentTab1));
        Tab2.setTabListener(new TabListener(fragmentTab2));
        Tab3.setTabListener(new TabListener(fragmentTab3));
 
        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);
        actionBar.addTab(Tab3);
        
        dbHelper = ContactsDatabaseHelper.getDatabase(MainActivity.this);
		
	}
	
	
	class TabListener implements ActionBar.TabListener {
		 
	    Fragment fragment;
	 
	    public TabListener(Fragment fragment) {
	        // TODO Auto-generated constructor stub
	        this.fragment = fragment;
	    }
	 
	    @Override
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	        ft.replace(R.id.fragment_container, fragment);
	    }
	 
	    @Override
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	        ft.remove(fragment);
	    }

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.add_contact) {
			
			Intent intent = new Intent(MainActivity.this, AddContact.class);
			startActivity(intent);
			
		} else if (item.getItemId() == R.id.sort_button) {
			
			//Radio button dialog box for contact sort options
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Sort");
    		Log.d("testing", "HI");

			dialogBuilder.setSingleChoiceItems(R.array.sort_options, -1, new DialogInterface.OnClickListener() {
			    
				@Override
			    public void onClick(DialogInterface dialog, int which) {
			        // the user clicked on sort_options[which]
					 dialog.dismiss();
		             int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
					 switch (selectedPosition){
				        case 0:
				            //Sort by first name selected
				        	dbHelper.sortContacts("firstName");
			        		Log.d("testing", "SORT ORDER IS FIRST");

				            break;

				        case 1:
				        	//Sort by last name selected
				        	if (dbHelper == null) {
				        		Log.d("testing", "DB is null");
				        	}
				        	dbHelper.sortContacts("lastName");
			        		Log.d("testing", "SORT ORDER IS LAST");

				            break;
				            
				        case 2:
				        	//Sort by number selected
				        	dbHelper.sortContacts("mobilePhone");
			        		Log.d("testing", "SORT ORDER IS NUMBER");

				        	break;
				      }
				}
				
			});

//			dialogBuilder.setNegativeButton("Cancel", null);
//			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					 switch (which){
//				        case DialogInterface.BUTTON_POSITIVE:
//				            //Yes button clicked
//				            break;
//
//				        case DialogInterface.BUTTON_NEGATIVE:
//				            //No button clicked
//				            break;
//				      }
//				}
//			});
//			
			dialogBuilder.setCancelable(true);
            levelDialog = dialogBuilder.create();
            levelDialog.show();
		}

		return (super.onOptionsItemSelected(item));
	}
	
}

