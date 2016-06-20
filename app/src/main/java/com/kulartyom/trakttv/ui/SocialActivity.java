package com.kulartyom.trakttv.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.facebook.Facebook;
import com.kulartyom.trakttv.database.RealmManager;

import org.json.JSONObject;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = SocialActivity.class.getSimpleName();


    // ===========================================================
    // Fields
    // ===========================================================

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button btnNext;
    private RealmManager realmManager;
    private LoginResult loginResult;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        realmManager = new RealmManager();
        requestProfileTracker();
        requestAccessToken();
        setContentView(R.layout.activity_social);
        findViews();

        if (isNetworkConnected() && AccessToken.getCurrentAccessToken() != null) {
            btnNext.setBackgroundResource(R.drawable.ic_navigate);
        } else {
            btnNext.setBackgroundResource(R.drawable.ic_navigate_red);
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.facebook_login_successfully), Toast.LENGTH_SHORT).show();
                    graphRequest();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), getString(R.string.facebook_login_cancel), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), getString(R.string.facebook_login_error), Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.getMessage());

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (isNetworkConnected() && AccessToken.getCurrentAccessToken() != null) {
                    requestAccessToken();
                    requestProfileTracker();
                    graphRequest();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.login_facebook, Toast.LENGTH_LONG).show();
                }
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void findViews() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        btnNext = (Button) findViewById(R.id.btn_next);
        assert btnNext != null;
        btnNext.setOnClickListener(this);
    }

    private void getInformationAboutProfile(Facebook facebook) {
        Bundle bundle = new Bundle();
        bundle.putString(Constans.ARG_NAME, facebook.getName());
        bundle.putString(Constans.ARG_EMAIL, facebook.getEmail());
        bundle.putString(Constans.ARG_LINK, facebook.getUrl());
        Intent in = new Intent(this, MainActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }

    public void graphRequest() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        String name = object.optString(Constans.ARG_NAME);
                        String id = object.optString(Constans.ARG_ID);
                        String email = object.optString(Constans.ARG_EMAIL);
                        long user_id = Long.parseLong(id);
                        String urlphoto = Constans.ARG_GRAPH_URL + String.valueOf(user_id) + Constans.ARG_GRAPH;
                        realmManager.saveFacebookInformation(id, name, email, urlphoto);
                        getInformationAboutProfile(realmManager.getProfileInformation());

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(Constans.ARG_FIELDS, Constans.ARG_VALUE);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void requestAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    btnNext.setBackgroundResource(R.drawable.ic_navigate);
                } else {
                    btnNext.setBackgroundResource(R.drawable.ic_navigate_red);
                }
            }
        };
    }

    private void requestProfileTracker() {
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (Profile.getCurrentProfile() != null) {
                    btnNext.setBackgroundResource(R.drawable.ic_navigate);

                } else {
                    btnNext.setBackgroundResource(R.drawable.ic_navigate_red);
                }
            }
        };
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
