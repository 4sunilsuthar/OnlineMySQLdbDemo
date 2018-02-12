package com.demo.admin.onlinemysqldbdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    LinearLayout profileSection;
    Button btnLogout;
    SignInButton btnLogin;
    TextView tvName, tvEmail;
    ImageView imgProfilePic;
    GoogleApiClient googleApiClient;
    static final int REQ_CODE = 9001;
    String name, email, imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        profileSection = findViewById(R.id.profile_section);
        btnLogout = findViewById(R.id.btn_sign_out);
        btnLogin = findViewById(R.id.btn_sign_in);
        tvName = findViewById(R.id.tv_user_name);
        tvEmail = findViewById(R.id.tv_user_email);
        imgProfilePic = findViewById(R.id.img_user_profile);
        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        profileSection.setVisibility(View.GONE);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_sign_in:
                signIn();
                break;
            case R.id.btn_sign_out:
                signOut();
                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    public void signOut() {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            name = account.getDisplayName(); // for Full Name
//            String serverCode = account.getServerAuthCode(); // Returns a one-time server auth code to send to your web server which can be exchanged for access token and sometimes refresh token if requestServerAuthCode(String) is configured; null otherwise.

//             account.getGivenName(); for First Name the Given name
//             account.getFamilyName(); for Surname
//             account.getAccount(); A convenient wrapper for getEmail() which returns an android.accounts.Account object.
//             Set<Scope> scopes = account.getGrantedScopes(); Returns all scopes that have been authorized to your application.
//             Log.i("ABCErr",scopes.toString());
//             Uri uri = account.getPhotoUrl(); to get user Photo url
//             Log.i("ABCErr",uri.toString());
//             Log.i("ABCErr", dob);
            email = account.getEmail();
            try {
//                Log.i("ABCErr", "Default Image URL is :" + account.getPhotoUrl());

                if (account.getPhotoUrl() != null) {
                    imgUrl = account.getPhotoUrl().toString();
                    Glide.with(this).load(imgUrl).into(imgProfilePic);
//                    Log.i("ABCErr", "Image URL is :" + imgUrl);
                } else {

                    imgUrl = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg";
                    Glide.with(this).load(imgUrl).into(imgProfilePic);
//                    Log.i("ABCErr", "Changed Image URL is :" + imgUrl);
                }
            } catch (Exception e) {
                Log.i("ABCErr", "The Error is :" + e);
            }

            tvName.setText(name);
            tvEmail.setText(email);

            updateUI(true);

        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin) {

        if (isLogin) {
            profileSection.setVisibility(View.VISIBLE);
            Intent signInToMain = new Intent(this, MainActivity.class);
            signInToMain.putExtra("user_name", name);
            signInToMain.putExtra("user_email", email);
            signInToMain.putExtra("user_img_url", imgUrl);
            startActivity(signInToMain);
            btnLogin.setVisibility(View.GONE);
        } else {
            profileSection.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }

    }
}
