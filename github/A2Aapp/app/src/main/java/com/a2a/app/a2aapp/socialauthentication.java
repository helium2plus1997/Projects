package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

/**
 * Created by Mounika on 4/26/2016.
 */
public class socialauthentication extends Activity {

    private TextView info;
    private LoginButton loginButton;
    private SignInButton btnSignIn;

    private CallbackManager callbackManager;

    private TextView info1;
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_signin);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        info1 = (TextView) findViewById(R.id.info1);

        btnSignIn = (SignInButton) findViewById(R.id.btngplus);
        // loginButton.setReadPermissions("profile");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Gpluslogin.class);
                startActivity(i);
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                info1.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

                Intent i = new Intent(socialauthentication.this, MapView2.class);
                startActivity(i);

            }

            @Override
            public void onCancel() {
                info1.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                info1.setText("Login attempt failed.");
            }
        });


    }

    }
