package com.eventmanagement;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

import com.eventmanagement.entity.DataEntity;

public class AddDataActivity extends Activity {

	EditText name_et,address_et,contact_et;
	RatingBar ratingBar;
	private DBHelper sql = null;
	String category;
	String ratingValue="0";
	ImageView preview;
	String realPath="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_data);
		name_et = (EditText) findViewById(R.id.name_et);
		address_et = (EditText) findViewById(R.id.address_et);
		contact_et = (EditText) findViewById(R.id.contact_et);
		ratingBar = (RatingBar)findViewById(R.id.ratingBar);
		preview = (ImageView) findViewById(R.id.prev);
		Button add_btn = (Button) findViewById(R.id.add_btn);
		Button cancel_btn = (Button) findViewById(R.id.cancel_btn);
		Button attach_btn = (Button) findViewById(R.id.attach_btn);
		
		name_et.setHintTextColor(Color.BLACK);
		address_et.setHintTextColor(Color.BLACK);
		contact_et.setHintTextColor(Color.BLACK);
		Intent intent = getIntent();
		category = intent.getStringExtra("category");

		attach_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int reqcode = 1;
				Intent action = new Intent(Intent.ACTION_GET_CONTENT);
				action = action.setType("*/*").addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(action, reqcode);
			}
		});
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratingValue = String.valueOf(rating);
			}
		}); 

		sql = new DBHelper(getApplicationContext());
		add_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(name_et.getText().toString().equals("")){
					name_et.setError("Please Enter name");
				}
				else if(address_et.getText().toString().equals("")){
					address_et.setError("Please Enter address");
				}
				else if(contact_et.getText().toString().equals("")){
					contact_et.setError("Please Enter contact");
				}
				else if(String.valueOf(ratingBar.getRating()).equals("0")){
					contact_et.setError("Please rate");
				}
				else{
					DataEntity de = new DataEntity(0,category,name_et.getText().toString(),address_et.getText().toString(),
							contact_et.getText().toString(),ratingValue,realPath);
					if(sql.insertData(de))
					{
						Toast.makeText(AddDataActivity.this, "Information added successfully", Toast.LENGTH_LONG).show();
						finish();
					}
					else
					{
						Toast.makeText(AddDataActivity.this, "Fail to add Information, please try again!", Toast.LENGTH_LONG).show();
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
	
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		if(resCode == Activity.RESULT_OK && data != null){
			// SDK < API11
			if (Build.VERSION.SDK_INT < 11)
				realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

			// SDK >= 11 && SDK < 19
			else if (Build.VERSION.SDK_INT < 19)
				realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

			// SDK > 19 (Android 4.4)
			else
				realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

			Uri uriFromPath = Uri.fromFile(new File(realPath));

			//display selected image

			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
				if(bitmap!=null)
					preview.setImageBitmap(bitmap);
				else
					preview.setBackgroundResource(R.drawable.file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
