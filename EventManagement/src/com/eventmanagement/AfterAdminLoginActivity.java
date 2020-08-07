package com.eventmanagement;

import java.util.ArrayList;
import java.util.Arrays;

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

public class AfterAdminLoginActivity extends Activity {

	private ListView lv;
	//	int [] catogryImages ;
	// Search EditText
	EditText inputSearch;
	private DBHelper sql = null;

	private ArrayList<String> categoryList =null;
	private ArrayList<Integer> catogryImages =null;
	private ListViewAdapter adapter =null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_after_login);

		//intialize catogry List
		categoryList = new ArrayList<>(Arrays.asList("Choregrapher", "DJ & Lightening", "Caterers","Boutique","Mehendi","Decoraters","Photographer"
				,"Bakery","Invitation Cards","Varmala","Band","Venue","Dancers","Tour And Travels","Grooming"
				,"Anchor","Pandit","Jewellers"));
		catogryImages = new ArrayList<>(Arrays.asList(R.drawable.choreographer,R.drawable.dj,R.drawable.caterers,R.drawable.boutique
				,R.drawable.mehendi,R.drawable.decoraters,R.drawable.photographer,R.drawable.bakery,R.drawable.invitation
				,R.drawable.varmala,R.drawable.band,R.drawable.venue,R.drawable.dancers,R.drawable.tour,R.drawable.grooming
				,R.drawable.anchor,R.drawable.pandit,R.drawable.jewellers));
		lv = (ListView) findViewById(R.id.list_view);




		inputSearch = (EditText) findViewById(R.id.inputSearch);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				AfterAdminLoginActivity.this.adapter.getFilter().filter(cs);   
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

		adapter = new ListViewAdapter(AfterAdminLoginActivity.this, categoryList ,catogryImages);
		lv.setAdapter(adapter);
		Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String category = ((TextView) view.findViewById(R.id.txtName)).getText().toString();
				Intent intent = new Intent(AfterAdminLoginActivity.this, DisplayDataActivity.class);
				intent.putExtra("category", category);

				startActivity(intent);

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		sql = new DBHelper(getApplicationContext());
		//
		//
		//		new LoadDataTask(AfterAdminLoginActivity.this).execute(null,null,null);
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
			Intent i = new Intent(AfterAdminLoginActivity.this, AddCategoryActivity.class);
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
				adapter = new ListViewAdapter(AfterAdminLoginActivity.this, categoryList , catogryImages);
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