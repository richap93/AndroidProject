package rpat789.softeng206.contactsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Groups extends Fragment {

	private ListView listView;
	List<String> groupOptions;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_groups, container, false);

		listView = (ListView)rootView.findViewById(R.id.groups_listView);

		setUpListView();

		return rootView;
	}

	public void setUpListView() {

		groupOptions = new ArrayList<String>();

		groupOptions.add("Emergency Contacts");
		groupOptions.add("Family");
		groupOptions.add("Friends");
		groupOptions.add("Colleages");
		groupOptions.add("Not assigned");

		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, groupOptions);

		listView.setAdapter(listAdapter);


	} 
}