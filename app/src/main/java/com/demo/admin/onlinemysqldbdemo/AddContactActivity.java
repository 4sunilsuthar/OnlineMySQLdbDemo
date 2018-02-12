package com.demo.admin.onlinemysqldbdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddContactActivity extends AppCompatActivity {
    EditText edName, edEmail, edMobile;
    String name, email, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        edName = (EditText) findViewById(R.id.ed_name);
        edEmail = (EditText) findViewById(R.id.ed_email);
        edMobile = (EditText) findViewById(R.id.ed_phone);

    }

    public void saveInfo(View view) {
        name = edName.getText().toString();
        email = edEmail.getText().toString();
        mobile = edMobile.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(name, email, mobile);
        finish();
    }


    class BackgroundTask extends AsyncTask<String, Void, String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "https://androidserverin.000webhostapp.com/add_info.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String name, email, mobile;
            name = args[0];
            email = args[1];
            mobile = args[2];
            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "One Row of Data Inserted!!!!";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                e.getCause();//232323
            }
            return "Something went wrong Try Again Never say Never!!!!";

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }
    }

}