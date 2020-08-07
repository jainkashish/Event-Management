package com.eventmanagement;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eventmanagement.entity.DataEntity;
import com.google.gson.Gson;

public class DisplayDataActivity extends Activity { // List view
	private ListView lv;
	DataListViewAdapter adapter;
	private DBHelper sql = null;
	ArrayList<HashMap<String, String>> productList;
	String category;
	ArrayList<DataEntity> dataList;
	String menuValue="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_data);
		sql = new DBHelper(getApplicationContext());

		Intent intent = getIntent();

		category = intent.getStringExtra("category");
		menuValue = intent.getStringExtra("menu");
	
		lv = (ListView) findViewById(R.id.list_view);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DataEntity selectedItem =(DataEntity) (lv.getItemAtPosition(arg2));

				showPopup(selectedItem);
				
			}
		});
		registerForContextMenu(lv);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.list_view) {
//	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//	    menu.setHeaderTitle(dataList.get(info.position).getName());
	    String[] menuItems = getResources().getStringArray(R.array.menu);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
//	  String[] menuItems = getResources().getStringArray(R.array.menu);
//	  String menuItemName = menuItems[menuItemIndex];
	  if(menuItemIndex==0){
		  Intent i = new Intent(DisplayDataActivity.this, EditDataActivity.class);
		  i.putExtra("data", new Gson().toJson(dataList.get(info.position)));
		  startActivity(i);
	  }
	  else
	  {
		  sql.deleteData(dataList.get(info.position));
		  dataList= sql.getAllData(category);
		  adapter.content=dataList;
		  adapter.notifyDataSetChanged();
	  }
//	  String listItemName = dataList.get(info.position).getName();
	  return true;
	}
	
	private void showPopup(DataEntity obj) {
		
		final Dialog dialog = new Dialog(DisplayDataActivity.this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		// Set dialog title
//		dialog.setTitle("Custom Dialog");

		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		TextView name = (TextView) dialog.findViewById(R.id.name);
		TextView address = (TextView) dialog.findViewById(R.id.address);
		final TextView phone = (TextView) dialog.findViewById(R.id.phone);
		RatingBar rating  = (RatingBar) dialog.findViewById(R.id.ratingBar);
		name.setText(obj.getName());
		address.setText(obj.getAddress());
		phone.setText(obj.getContact());
		rating.setRating(Float.parseFloat(obj.getRates()));
		if(!obj.getImagePath().equals("")){
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			Bitmap bitmap = BitmapFactory.decodeFile(obj.getImagePath(),bmOptions);
			if(bitmap!=null)
			{
				bitmap = Bitmap.createBitmap(bitmap);
				image.setImageBitmap(bitmap);
			}
			else
			{
				image.setBackgroundResource(R.drawable.file);
			}
		}
		else {
			image.setBackgroundResource(R.drawable.no_image);
		}
		
		phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+phone.getText().toString()));
				startActivity(callIntent);
			}
		});

		dialog.show();

		Button declineButton = (Button) dialog.findViewById(R.id.close);
		// if decline button is clicked, close the custom dialog
		declineButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Close dialog
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dataList= sql.getAllData(category);
		adapter = new DataListViewAdapter(DisplayDataActivity.this, dataList);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if(menuValue!=null && menuValue.equals("hide"))
		{
			return false;
		}
		inflater.inflate(R.menu.activity_add, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.add:
			Intent i = new Intent(DisplayDataActivity.this, AddDataActivity.class);
			i.putExtra("category", category);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
