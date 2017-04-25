package com.example.shimaashokry.mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignInPage extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getPreferredEmail(this) != null )
        {
            Intent SecondPage = new Intent(this,SearchPage.class);
            startActivity(SecondPage);
        }
        /* Google Sign In */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
               // .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
            // to find button
            SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
            // set dimention of button
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setScopes(gso.getScopeArray());
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        signIn();
                }
            });
    }
    // this variable to know if the user click on sign in button
    int RC_SIGN_IN = 10;
    private void signIn() {
        // to show the accounts of users to choose from them
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        // after choosing the email, it will be sent to the app
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
       // Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
          //  mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            /* Show the email and name of user */
            Toast.makeText(this,"Name: "+ acct.getDisplayName(), Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Email: "+ acct.getEmail(), Toast.LENGTH_LONG).show();
            // to move to another activity

            boolean firstTime = false;// is first time after sign out
            if(getPreferredEmail(this) == null)
                firstTime = true;

            acct = result.getSignInAccount();

                setPreferredEmail(SignInPage.this, acct.getEmail());
                //Utility.setPreferredId(SignInActivity.this, acct.getId());

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this,"App rejects your request ", Toast.LENGTH_LONG).show();
        }
    }
    public static SharedPreferences getPrivateSharedPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SharedPrefs", context.MODE_PRIVATE);

        return prefs;
    }

    //remove all preferences
    public static void removeAllPreferences(Context context) {
        SharedPreferences prefs = getPrivateSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static String getPreferredEmail(Context context) {
        SharedPreferences prefs = getPrivateSharedPreferences(context);
        return prefs.getString("emailKey",
                null);
    }

    public static void setPreferredEmail(Context context, String email) {
        SharedPreferences prefs = getPrivateSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("emailKey", email);
        editor.commit();
    }

}
