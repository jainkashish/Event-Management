package com.eventmanagement;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GuestActivity extends Activity { 

	private GridView gv;
	EditText inputSearch;
	private DBHelper sql = null;
	private ArrayList<String> categoryList =null;
	private ArrayList<Integer> catogryImages =null;
	private GridViewAdapter adapter =null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest);
		sql = new DBHelper(getApplicationContext());

		//intialize catogry List
		categoryList = new ArrayList<>(Arrays.asList("Choregrapher", "DJ & Lightening", "Caterers","Boutique","Mehendi","Decoraters","Photographer"
				,"Bakery","Invitation Cards","Varmala","Band","Venue","Dancers","Tour And Travels","Grooming"
				,"Anchor","Pandit","Jewellers"));
		catogryImages = new ArrayList<>(Arrays.asList(R.drawable.choreographer,R.drawable.dj,R.drawable.caterers,R.drawable.boutique
				,R.drawable.mehendi,R.drawable.decoraters,R.drawable.photographer,R.drawable.bakery,R.drawable.invitation
				,R.drawable.varmala,R.drawable.band,R.drawable.venue,R.drawable.dancers,R.drawable.tour,R.drawable.grooming
				,R.drawable.anchor,R.drawable.pandit,R.drawable.jewellers));

		gv = (GridView) findViewById(R.id.grid_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				GuestActivity.this.adapter.getFilter().filter(cs);   
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
		
		adapter = new GridViewAdapter(GuestActivity.this, categoryList ,catogryImages);
		gv.setAdapter(adapter);
		Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();

		
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String category = ((TextView) view.findViewById(R.id.product_name)).getText().toString();
				Intent intent = new Intent(GuestActivity.this, DisplayDataActivity.class);
				intent.putExtra("category", category);
				intent.putExtra("menu", "hide");
				startActivity(intent);

			}
		});
	}
}
