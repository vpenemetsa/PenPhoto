package com.vijay.penphoto;

import com.kinvey.KCSClient;
import com.kinvey.KinveySettings;
import com.kinvey.KinveyUser;
import com.kinvey.util.KinveyCallback;
import com.vijay.Constants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	
	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private KCSClient mKinveyClient;
	
	private String TAG = LoginActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
		mKinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
		
		mKinveyClient.pingService(new KinveyCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean r) {
				Log.d(TAG, "Kinvey succesfully pinged!!!!!!!");
			}
			
			public void onFailure(Throwable t) {
		        Log.d(TAG, "Kinvey Ping Failed", t);
		    }
		});
		
		SharedPreferences userdetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
		boolean hasSignedIn = userdetails.getBoolean(Constants.SIGNED_IN_PREF, false);
		if (hasSignedIn){
		    String username = userdetails.getString(Constants.USERNAME_PREF, "unknown");
		    String pass = userdetails.getString(Constants.PASS_PREF, "unknown");
		    mKinveyClient.loginWithUsername(username, pass, new KinveyCallback<KinveyUser>(){
		        @Override
		        public void onFailure(Throwable t) {
		            Log.e(TAG, "failed to log in to kinvey", t);
		        }
		        @Override
		        public void onSuccess(KinveyUser u) {
		            Log.d(TAG, "logged into kinvey");
		            Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
		            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(i);
		            finish();
		        }
		    });
		} else {
			
			setContentView(R.layout.activity_login);
			
			// Set up the login form.
			mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
			mEmailView = (EditText) findViewById(R.id.email);
			mEmailView.setText(mEmail);
			
			mPasswordView = (EditText) findViewById(R.id.password);
			mPasswordView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
			
			mLoginFormView = findViewById(R.id.login_form);
			mLoginStatusView = findViewById(R.id.login_status);
			mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
			
			findViewById(R.id.sign_in_button).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							attemptLogin();
						}
					});
			
			findViewById(R.id.register_button).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
							finish();
						}
					});
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}
		
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		
		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);
			
			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
			
			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			
			mKinveyClient.loginWithUsername(mEmail, mPassword, new KinveyCallback<KinveyUser>() {

				public void onFailure(Throwable t) {
					mAuthTask = null;
					showProgress(false);
					mPasswordView
						.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
		        }
				
				@Override
				public void onSuccess(KinveyUser u) {
					mAuthTask = null;
					showProgress(false);
					CharSequence text = "Welcome back," + u.getUsername() + ".";
		            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
		            saveKinveyLoginDetails(u);
		            Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
		            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(i);
		            finish();
				}
			});
			return true;
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			
			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}
		
		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		
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