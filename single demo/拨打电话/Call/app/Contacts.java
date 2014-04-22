package com.call.app;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wjbnys on 14-4-22.
 */
public class Contacts {
    private Context context = null;
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private static final String[] PHONES_PROJECTION = new String[]
            {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            };
    private final int PHOTO_DEFAULT = R.drawable.ic_launcher;

    private ArrayList<Contact> ContactList= new ArrayList<Contact>();

    public Contacts(Context context)
    {
        this.context = context;
        this.GetPhoneContacts();
        this.GetSimContacts();
    }

    public ArrayList<Contact> GetContacts()
    {
        return this.ContactList;
    }

    private void GetPhoneContacts()
    {
        ContentResolver resolver = this.context.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String number = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(number))
                    continue;
                String name = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                Long id = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                Bitmap photo = null;
                if(photoid > 0 ) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    photo = BitmapFactory.decodeStream(input);
                }else {
                    photo = BitmapFactory.decodeResource(this.context.getResources(), PHOTO_DEFAULT);
                }
                Contact contact = new Contact();
                contact.id = id;
                contact.name = name;
                contact.number = number;
                contact.photo = photo;
                contact.photoid = photoid;
                ContactList.add(contact);
            }
            phoneCursor.close();
        }
    }

    private void GetSimContacts()
    {
        ContentResolver resolver = this.context.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String number = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(number))
                    continue;
                String name = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                Bitmap photo = BitmapFactory.decodeResource(this.context.getResources(), PHOTO_DEFAULT);
                Contact contact = new Contact();
                contact.id = 0;
                contact.name = name;
                contact.number = number;
                contact.photo = photo;
                contact.photoid = 0;
                ContactList.add(contact);
            }
            phoneCursor.close();
        }
    }
}

