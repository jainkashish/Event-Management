package com.eventmanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddCategoryActivity extends Activity {

	EditText category_et;
	private DBHelper sql = null;
	private ListView listDetails;
	private ArrayList<String> categoryList =null;
	private ListViewAdapter2 adapter =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_category);
		category_et = (EditText) findViewById(R.id.category_et);
		listDetails =(ListView) findViewById(R.id.listDetails);
		Button add_btn = (Button) findViewById(R.id.add_btn);
		Button cancel_btn = (Button) findViewById(R.id.cancel_btn);

		category_et.setHintTextColor(Color.BLACK);

		sql = new DBHelper(getApplicationContext());
		add_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(category_et.getText().toString().equals("")){
					category_et.setError("Please Enter category");
				}
				else{
					if(sql.insertCategory(category_et.getText().toString().trim()))
					{
						Toast.makeText(AddCategoryActivity.this, "Category added successfully", Toast.LENGTH_LONG).show();
						finish();
						//						category_et.setText("");
						//						listDetails.setVisibility(View.VISIBLE);
						//						new LoadDataTask(AddCategoryActivity.this).execute(null,null,null);
					}
					else
					{
						Toast.makeText(AddCategoryActivity.this, "Fail to add Faculty, please try again!", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		cancel_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

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
				adapter = new ListViewAdapter2(AddCategoryActivity.this, categoryList);
				adapter.isDelete=true;
				listDetails.setAdapter(adapter);
				Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
			}
		}
	}
}
