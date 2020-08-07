package com.eventmanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends Activity {

	EditText user_et,pass_et;
	private DBHelper sql = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		user_et = (EditText) findViewById(R.id.user_et);
		pass_et = (EditText) findViewById(R.id.pass_et);
		user_et.setHintTextColor(Color.BLACK);
		pass_et.setHintTextColor(Color.BLACK);
		Button login_btn = (Button) findViewById(R.id.login_btn);

		sql = new DBHelper(getApplicationContext());
		login_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(user_et.getText().toString().equals("")){
					user_et.setError("Please Enter username");
				}else if(pass_et.getText().toString().equals("")){
					pass_et.setError("Please Enter password");
				}
				else{
					ArrayList<String> details = sql.getUserPassword();
					if(user_et.getText().toString().equals(details.get(0)) && pass_et.getText().toString().equals(details.get(1)))
					{
						Intent i = new Intent(getApplicationContext(),AfterAdminLoginActivity.class);
						startActivity(i);
						finish();
					}
					else
					{
						user_et.setText("");
						pass_et.setText("");
						Toast.makeText(AdminLoginActivity.this, "Invalid username and password", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}
}
