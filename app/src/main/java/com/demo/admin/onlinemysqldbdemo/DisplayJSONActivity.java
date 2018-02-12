package com.demo.admin.onlinemysqldbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayJSONActivity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactsAdapter contactsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_json);
        contactsAdapter = new ContactsAdapter(this, R.layout.row_layout);
        listView = (ListView) findViewById(R.id.list_view_result);
        listView.setAdapter(contactsAdapter);
        json_string = getIntent().getStringExtra("json_data");
//        Log.e("ABCErr","Latest JSON received is : "+json_string);
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name, email, mobile;
            while (count < jsonArray.length()) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(count);
                name = jsonObject2.getString("name");
                email = jsonObject2.getString("email");
                mobile = jsonObject2.getString("mobile");
                Contacts contacts = new Contacts(name, email, mobile);
                contactsAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
