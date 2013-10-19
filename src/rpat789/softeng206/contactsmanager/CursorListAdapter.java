package rpat789.softeng206.contactsmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CursorListAdapter extends CursorAdapter {

	private Context context;
	LayoutInflater inflater;
	
	public CursorListAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		TextView firstName = (TextView) view.findViewById(R.id.list_item_text_contact_first);
		TextView lastName = (TextView) view.findViewById(R.id.list_item_text_contact_last);
		TextView id = (TextView) view.findViewById(R.id.contact_id);
		TextView number = (TextView) view.findViewById(R.id.list_item_text_number);
		ImageView image = (ImageView) view.findViewById(R.id.contact_image);
		
		firstName.setTextColor(Color.BLACK);
		lastName.setTextColor(Color.BLACK);
		
		id.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
		firstName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		lastName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
		number.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));

		byte[] contactImage = cursor.getBlob(12);
		
		Bitmap bm = BitmapFactory.decodeByteArray(contactImage, 0, contactImage.length);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth());
		if (contactImage != null) {
			image.setImageBitmap(bm);
			
		} else {
			image.setImageResource(R.drawable.contact_photo);
		}
		
//		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.xyz);
//
//		resizedbitmap1=Bitmap.createBitmap(bmp, 0,0,yourwidth, yourheight);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItemView = inf.inflate(R.layout.custom_list_view, null);
				
		return listItemView;
	}

}
