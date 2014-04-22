package com.call.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by wjbnys on 14-4-22.
 */
public class ContactsAdapter extends BaseAdapter {
    private ArrayList<Contact> contacts = null;
    private Context context = null;
    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }
    @Override
    public int getCount() {
        return this.contacts.size();
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.contact_item, null);
        ImageView imageview = (ImageView) view.findViewById(R.id.contact_photo);
        TextView nameview = (TextView) view.findViewById(R.id.contact_name);
        TextView numberview = (TextView) view.findViewById(R.id.contact_number);
        Contact contact = this.contacts.get(position);
        imageview.setImageBitmap(contact.photo);
        nameview.setText(contact.name);
        numberview.setText(contact.number);
        return view;
    }
}

