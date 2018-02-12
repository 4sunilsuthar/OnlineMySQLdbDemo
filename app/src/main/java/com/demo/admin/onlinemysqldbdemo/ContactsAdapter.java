package com.demo.admin.onlinemysqldbdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 02-02-2018.
 */

public class ContactsAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ContactsAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    ContactHolder contactHolder;
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            contactHolder = new ContactHolder();
            contactHolder.tv_name = (TextView) row.findViewById(R.id.tv_name);
            contactHolder.tv_email = (TextView) row.findViewById(R.id.tv_email);
            contactHolder.tv_mobile = (TextView) row.findViewById(R.id.tv_phone);
            row.setTag(contactHolder);

        }
        else {
            contactHolder = (ContactHolder) row.getTag();

        }
        Contacts contacts = (Contacts) this.getItem(position);
        contactHolder.tv_name.setText(contacts.getName());
        contactHolder.tv_email.setText(contacts.getEmail());
        contactHolder.tv_mobile.setText(contacts.getMobile());
        return row;
    }

    static class ContactHolder {
        TextView tv_name, tv_email, tv_mobile;
    }
}
