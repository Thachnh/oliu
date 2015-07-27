package com.vhackclub.oliu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends ActionBarActivity {

    public Dialog progressDialog;
    private String currentEventId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            // from http
            final Uri uri = getIntent().getData();
            currentEventId = uri.getLastPathSegment();
        }

        if (ParseUser.getCurrentUser().getUsername() != null) {
            onSignedIn();
            return;
        }

        final Button login = (Button) findViewById(R.id.loginfb);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void login() {
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);

        List<String> permissions = Arrays.asList("public_profile", "email", "user_friends");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                progressDialog.dismiss();
                if (user == null) {
                    Log.d("LoginActivity", "Uh oh. The user cancelled the Facebook login. " + Log.getStackTraceString(err));
                } else {
                    Log.d("LoginActivity", "User logged in through Facebook!");
                    onSignedIn();
                }
            }
        });
    }

    private void onSignedIn() {
        if (currentEventId != null) {
            Intent intent = new Intent(LoginActivity.this, EventPlannerActivity.class);
            Bundle b = new Bundle();
            b.putString("event_id", currentEventId);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(LoginActivity.this, CreateEventActivity.class));
            finish();
        }
    }
}
