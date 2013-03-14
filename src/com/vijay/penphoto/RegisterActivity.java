package com.vijay.penphoto;

import com.kinvey.KCSClient;
import com.kinvey.KinveySettings;
import com.kinvey.KinveyUser;
import com.kinvey.util.KinveyCallback;
import com.vijay.Constants;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private KCSClient mKinveyClient;
	EditText etEmail;
	EditText etPassword;
	EditText etConfirmPassword;
	Button bRegister;
	Button bCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login_register);
		
		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
		mKinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
		
		mKinveyClient.pingService(new KinveyCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean r) {
				Log.d("***************", "Kinvey succesfully pinged!!!!!!!");
			}
			
			public void onFailure(Throwable t) {
		        Log.d("****************", "Kinvey Ping Failed", t);
		    }
		});
		
		etEmail = (EditText) findViewById(R.id.email);
		etPassword = (EditText) findViewById(R.id.password);
		etConfirmPassword = (EditText) findViewById(R.id.confirm_password);
		bRegister = (Button) findViewById(R.id.register_button);
		bCancel = (Button) findViewById(R.id.cancel_button);
		
		bRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean hasError = false;
				
				final String email = etEmail.getText().toString();
				String password = etPassword.getText().toString();
				String confirmPassword = etConfirmPassword.getText().toString();
				
				if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
					hasError = true;
					etEmail.setError("Enter a valid email id");
				}
				
				if (password.length() < 4) {
					hasError = true;
					etPassword.setError("Enter a password of minimum 4 characters");
				}
				
				if (!password.equals(confirmPassword)) {
					hasError = true;
					etConfirmPassword.setError("The passwords do not match");
				}
				
				if (hasError == true) {
					return;
				}
				
				mKinveyClient.createUserWithUsername(email, confirmPassword, new KinveyCallback<KinveyUser>() {
			        public void onFailure(Throwable t) {
			            CharSequence text = "An account is already associated with " + email + ". Please a different email address.";
			            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			            etEmail.setText(null);
			            etPassword.setText(null);
			            etConfirmPassword.setText(null);
			        }
			        public void onSuccess(KinveyUser u) {
			            CharSequence text = "Welcome back," + u.getUsername() + ".";
			            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			            saveKinveyLoginDetails(u);
			            Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
			            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			            startActivity(i);
			            finish();
			        }
			    });
				
			}
		});
		
		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		
	}
	
	private void saveKinveyLoginDetails(KinveyUser u) {
	    SharedPreferences userdetails = getSharedPreferences(Constants.USER_DETAILS, Application.MODE_PRIVATE);
	    SharedPreferences.Editor userDetailsEdit = userdetails.edit();
	    userDetailsEdit.putBoolean(Constants.SIGNED_IN_PREF, true);
	    userDetailsEdit.putString(Constants.PASS_PREF, u.getPassword());
	    userDetailsEdit.putString(Constants.USERNAME_PREF, u.getUsername());
	    userDetailsEdit.commit();
	}
}
