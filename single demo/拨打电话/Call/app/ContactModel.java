package com.call.app;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wjbnys on 14-4-23.
 */
public class ContactModel {
    public SQLiteDatabase db = null;
    private Context context = null;
    private final int PHOTO_DEFAULT = R.drawable.ic_launcher;

    public ContactModel(Context context)
    {
        this.context = context;
        this.initialize();
    }

    private void initialize()
    {
        if(db == null || !this.db.isOpen())
        {
            this.db = DB_Helper.DB_Open("contact");
            if (!DB_Helper.DB_TableExist(db, "contact_list"))
            {
                db.execSQL("CREATE TABLE contact_list(_id integer primary key autoincrement, name varchar(20), number varchar(50),raw_id varchar(50),photo_id varchar(50),source varchar(4))");
            }
        }
    }

    public void InsertOrUpdate (Contact contact)
    {
        this.initialize();
        db.execSQL("INSERT INTO contact_list values(null , ?, ?, ?, ?, ?)", new String[]{
            contact.name,
            contact.number,
            String.valueOf(contact.raw_id),
            String.valueOf(contact.photoid),
            contact.source
        });
    }

    public ArrayList<Contact> GetDBContacts ()
    {
        this.initialize();
        Cursor cursor = db.rawQuery("SELECT * from contact_list", null);
        ArrayList<Contact> result = new ArrayList<Contact>();
        if (cursor.moveToFirst() == false)
        {
            cursor.close();
            return result;
        }
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            Contact contact = new Contact();
            contact.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
            contact.raw_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("raw_id")));
            contact.name = cursor.getString(cursor.getColumnIndex("name"));
            contact.source = cursor.getString(cursor.getColumnIndex("source"));
            contact.photoid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("photo_id")));
            contact.number = cursor.getString(cursor.getColumnIndex("number"));
            ContentResolver resolver = this.context.getContentResolver();
            if(contact.photoid > 0 ) {
                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact.raw_id);
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                contact.photo = BitmapFactory.decodeStream(input);
            }else {
                contact.photo = BitmapFactory.decodeResource(this.context.getResources(), PHOTO_DEFAULT);
            }
            result.add(contact);
        }
        cursor.close();
        return result;
    }

    public void close()
    {
        if(this.db != null && this.db.isOpen())
        {
            this.db.close();
        }
    }
}
