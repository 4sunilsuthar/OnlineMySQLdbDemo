package com.demo.admin.onlinemysqldbdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONActivity extends AppCompatActivity {

    String JSON_String;
    private ProgressBar progressBar;
    int count = 1;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);
        progressBar.setMax(10);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getNotification(View view) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // only for Oreo and newer versions

            // The id of the channel.
            String id = "my_channel_01";
            // The user-visible name of the channel.
            CharSequence name = getString(R.string.channel_name);
            // The user-visible description of the channel.
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(this, id);

        }
        else{
            // only for Nougat and older versions

            builder = new NotificationCompat.Builder(this);

        }

        builder.setSmallIcon(R.drawable.my_app_icon);
        builder.setContentTitle("My App Notification");
        builder.setContentText("Tap to return to Main Acticity");
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        mNotificationManager.notify(0, builder.build());
    }

    public void parseJSON(View view) {
        new BackgroundTask().execute(); //executing background task
        txt.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);


    }


    class BackgroundTask extends AsyncTask<String, Integer, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://androidserverin.000webhostapp.com/json_get_data.php";
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //code to test progress bar
                for (; count <= 6; count++) {
                    try {
                        Thread.sleep(150);
                        publishProgress(count * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                count++;
                publishProgress(count);

                while ((JSON_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_String + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            txt.setText("Running..." + values[0]);
            progressBar.setProgress(values[0]);
//            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
//            TextView textView = (TextView) findViewById(R.id.tv_json_data);
//            textView.setText(result);
            JSON_String = result;
            txt.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            if (JSON_String != null) {
                Intent intent = new Intent(getApplicationContext(), DisplayJSONActivity.class);
                intent.putExtra("json_data", JSON_String);
                startActivity(intent);
            } else {
                Log.i("ABCErr", "Corrupted JSON data");
            }
        }
    }
}
