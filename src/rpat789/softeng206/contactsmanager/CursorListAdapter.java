package rpat789.softeng206.contactsmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CursorListAdapter extends CursorAdapter {

	LayoutInflater inflater;
	
	public CursorListAdapter(Context context, Cursor c) {
		
		super(context, c);
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		//Take data from the cursor and put it in the views
		
		//Access elements in the list view (Note we must specify the parent view to look in)
		TextView firstName = (TextView) view.findViewById(R.id.list_item_text_contact_first);
		TextView lastName = (TextView) view.findViewById(R.id.list_item_text_contact_last);
		TextView id = (TextView) view.findViewById(R.id.contact_id);
		TextView number = (TextView) view.findViewById(R.id.list_item_text_number);
		ImageView image = (ImageView) view.findViewById(R.id.contact_image);
		
		firstName.setTextColor(Color.BLACK);
		lastName.setTextColor(Color.BLACK);
		
		//Set the text for each textview (use the position argument to find the appropriate element in the list) 
		id.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
		firstName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		lastName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
		number.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));

		byte[] contactImage = cursor.getBlob(12);
		
		//Set the image for the ImageView
		if (contactImage != null) {
			Bitmap bm = BitmapFactory.decodeByteArray(contactImage, 0, contactImage.length);
			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth()); //Crop image if from camera
			image.setImageBitmap(bm);
		} else {
			image.setImageResource(R.drawable.contact_photo); //Or use the default image in drawable 
		}
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		//When the view is created for the first time, need to tell adapters how each each item will look
		
		//Create a layout inflater to inflate xml layout(custom_list_view) for each item in the list
		LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//Inflate the list item layout . Keep a reference to the inflated view.
		View listItemView = inf.inflate(R.layout.custom_list_view, null);
				
		return listItemView;
	}

}
