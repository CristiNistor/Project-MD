package com.techblogon.loginexample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity 
{
	Button btnSignIn,btnSignUp,cPass;
	LoginDataBaseAdapter loginDataBaseAdapter;
    String storedPassword;
    final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
         cPass = (Button)findViewById(R.id.chagePasswordButton);

			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");


		    // get the Refferences of views
		      final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		      final EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
              final EditText editTextOldPassword=(EditText)dialog.findViewById(R.id.editTextOldPassword);
              final EditText editTextNewPassword=(EditText)dialog.findViewById(R.id.editTextNewPassword);


		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
				final String userName = editTextUserName.getText().toString();
				final String password = editTextPassword.getText().toString();
					
					// fetch the Password form database for respective user name
					storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();


                        // custom dialog
                        final Dialog dialog1 = new Dialog(context);
                        dialog1.setContentView(R.layout.custom);
                        dialog1.setTitle("Homepage");

                        // set the custom dialog components - text, image and button
                        TextView text = (TextView) dialog1.findViewById(R.id.text);
                        text.setText("Welcome " + userName);
                        ImageView image = (ImageView) dialog1.findViewById(R.id.image);
                        image.setImageResource(R.drawable.ic_launcher);



                        Button dialogButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);

                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final Dialog dialog2 = new Dialog(context);
                                dialog2.setContentView(R.layout.change);
                                dialog2.setTitle("Change password");
                                dialog2.show();




                               //final String oldPassword=editTextOldPassword.getText().toString();
                               //final String newPassword=editTextNewPassword.getText().toString();

                                // fetch the Password form database for respective user name
                                //loginDataBaseAdapter.updateEntry(storedPassword,newPassword);





                                dialog1.dismiss();
                            }
                        });

                        dialog1.show();


						dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});

			dialog.show();
















          /* cPass.setOnClickListener(new View.OnClickListener() {

               public void onClick(View v) {
                   // get The User name and Password

                  EditText editTextOldPassword=(EditText)dialog.findViewById(R.id.editTextOldPassword);
                  EditText editTextNewPassword=(EditText)dialog.findViewById(R.id.editTextNewPassword);

                  final String oldPassword=editTextOldPassword.getText().toString();
                  final String newPassword=editTextNewPassword.getText().toString();

                   // fetch the Password form database for respective user name
                   loginDataBaseAdapter.updateEntry(storedPassword,newPassword);


                   // check if the Stored password matches with  Password entered by user


               }
           });      */



















	}









	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
}
