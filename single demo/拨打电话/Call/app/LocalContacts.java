package com.call.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by wjbnys on 14-4-23.
 */
public class LocalContacts {
    private Context context;
    private Contacts contacts;
    private ArrayList<Contact> LocalContactList = new ArrayList<Contact>();
    public LocalContacts(Context context)
    {
        this.context = context;
        contacts = new Contacts(this.context);
        ArrayList<Contact> ContactList = contacts.GetContacts();
    }
    /*
    private ArrayList<Contact> DBContactList()
    {
        SQLiteDatabase db = DB_Helper.DB_Open("contact");
        if (!DB_Helper.DB_TableExist(db, "contact_list"))
        {
            db.execSQL("CREATE TABLE contact_list(_id integer primary key autoincrement, name varchar(20), number varchar(50),raw_id varchar(50),photo_id varchar(50))");
        }
        Cursor cursor = db.rawQuery("SELECT * from contact_list", null);
    }
    */
}
