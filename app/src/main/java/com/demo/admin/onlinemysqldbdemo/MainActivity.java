package com.demo.admin.onlinemysqldbdemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    TextView tvName, tvEmail;
    ImageView imgProfilePic;
    Button btnAdd, btnView;
    TextView tvMsg;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btn_add_contact);
        btnView = findViewById(R.id.btn_view_contact);
        tvMsg = findViewById(R.id.tv_msg);
        tvName = findViewById(R.id.tv_user_name);
        tvEmail = findViewById(R.id.tv_user_email);
        imgProfilePic = findViewById(R.id.img_user_profile);
        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("user_name"));
        tvEmail.setText(intent.getStringExtra("user_email"));
        Glide.with(this).load(intent.getStringExtra("user_img_url")).into(imgProfilePic);


        //to check the internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            tvMsg.setVisibility(View.INVISIBLE);

        } else {
            tvMsg.setText("No Internet Connection Available !!!");
            tvMsg.setTextColor(this.getColor(R.color.errorColor));
            btnView.setEnabled(false);
            btnAdd.setEnabled(false);
        }


    }

    public void moveFunction(View view) {
        startActivity(new Intent(this, AddContactActivity.class));
    }

    public void moveToViewFunction(View view) {
        startActivity(new Intent(this, JSONActivity.class));//will redirect to JSON Activity
    }


    public void logOutFunction(View view) {
//        SignInActivity signInActivity = new SignInActivity();
//        signInActivity.signOut();

    }

    public void moveToUserImgFunction(View view) {
        startActivity(new Intent(this, VolleyExampleActivity.class));//will redirect to Recycler view Activity
    }
}
