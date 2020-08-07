package com.eventmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);

		TextView about = (TextView) findViewById(R.id.text);
		String text = "About Event Management";
		about.setText(text);
	}
}
