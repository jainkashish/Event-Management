package com.eventmanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AfterAdminLoginActivity2 extends Activity {

	private ListView lv;

	// Search EditText
	EditText inputSearch;
	private DBHelper sql = null;

	private ArrayList<String> categoryList =null;
	private ListViewAdapter2 adapter =null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest);

		lv = (ListView) findViewById(R.id.list_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				AfterAdminLoginActivity2.this.adapter.getFilter().filter(cs);   
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub                          
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String category = ((TextView) view.findViewById(R.id.txtName)).getText().toString();
				Intent intent = new Intent(AfterAdminLoginActivity2.this, DisplayDataActivity.class);
				intent.putExtra("category", category);

				startActivity(intent);

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sql = new DBHelper(getApplicationContext());


		new LoadDataTask(AfterAdminLoginActivity2.this).execute(null,null,null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_add, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.add:
			Intent i = new Intent(AfterAdminLoginActivity2.this, AddCategoryActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog =null;

		public LoadDataTask(Context context) 
		{
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Fetching posts...");
			this.dialog.setCancelable(false);
			this.dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			categoryList= (ArrayList<String>) sql.getAllCategory();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if(categoryList.size()>0)
			{
				adapter = new ListViewAdapter2(AfterAdminLoginActivity2.this, categoryList);
				adapter.isDelete=true;
				lv.setAdapter(adapter);
				Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
			}
		}
	}
}