package rpat789.softeng206.contactsmanager;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Frame Activity with the three tabs - All contacts, Favourites and Groups
 * @author Richa Patel
 */
public class MainActivity extends Activity {

	private ActionBar.Tab Tab1, Tab2, Tab3;

	//Set fragments
	private Fragment fragmentTab1 = new AllContacts();
	private Fragment fragmentTab3 = new Groups();
	private Fragment fragmentTab2 = new Favourites();

	private AlertDialog levelDialog;
	private ContactsDatabaseHelper dbHelper;

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

	/**
	 * Tab listener class to allow switching between the tabs
	 * @author richa
	 *
	 */
	class TabListener implements ActionBar.TabListener {

		Fragment fragment;

		public TabListener(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.replace(R.id.fragment_container, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		//Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	/**
	 * Handles the menu bar buttons add/sort
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.add_contact) {
			//starts the AddContact activity - UI Screens are changed 

			Intent intent = new Intent(MainActivity.this, AddContact.class);
			startActivity(intent);

		} else if (item.getItemId() == R.id.sort_button) {

			//Radio button dialog box for contact sort options
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Sort");

			//Sets choices - First name, Last name, Number
			dialogBuilder.setSingleChoiceItems(R.array.sort_options, -1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					//the user clicked on sort_options[which]

					dialog.dismiss();

					//Selected position of the option selected by the user
					int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();

					//Calls sortContacts and passes the sort order of contacts as parameters according to the 
					//selection of sort order made by the user
					switch (selectedPosition){
					case 0:
						//Sort by first name selected
						dbHelper.sortContacts("firstName");
						break;

					case 1:
						//Sort by last name selected
						dbHelper.sortContacts("lastName");
						break;

					case 2:
						//Sort by number selected
						dbHelper.sortContacts("mobilePhone");
						break;
					}
				}

			});

			dialogBuilder.setCancelable(true);
			levelDialog = dialogBuilder.create();
			levelDialog.show();
		}

		return (super.onOptionsItemSelected(item));
	}

}

