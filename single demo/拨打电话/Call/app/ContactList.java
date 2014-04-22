package com.call.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactList extends Activity {
    private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private List<Map<String, Object>> listitems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        try{
            if( listitems == null){
                this.listitems = new ArrayList<Map<String, Object>>();
                GetPhoneContacts();
                GetSIMContacts();
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, this.listitems, R.layout.contact_item,
                    new String[]{"name", "number", "photo"},
                    new int[] {R.id.contact_name, R.id.contact_number, R.id.contact_photo});
            ListView listView = (ListView)findViewById(R.id.contact_list);
            listView.setAdapter(simpleAdapter);

            class ContactItemListener implements OnItemClickListener{
                public Context context = null;
                private int pos = 0;

                public ContactItemListener(Context ctx)
                {
                    this.context = ctx;
                }
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    this.pos = position;
                    LinearLayout contact_confirm = (LinearLayout)getLayoutInflater().inflate(R.layout.contact_confirm, null);
                    new AlertDialog.Builder(ContactList.this).
                            setIcon(R.drawable.ic_launcher).
                            setTitle("确认信息").
                            setView(contact_confirm).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SQLiteDatabase db = DB_Helper.DB_Open("contact");
                                    if (!DB_Helper.TableExist(db, "contact_list"))
                                    {
                                        db.execSQL("CREATE TABLE contact_list(_id integer primary key autoincrement, name varchar(20), number varchar(50), photo varchar(255))");
                                    }
                                    db.execSQL("INSERT INTO contact_list values(null , ?, ?, ?)", new String[]{
                                            ContactList.this.listitems.get(ContactItemListener.this.pos).get("name").toString(),
                                            ContactList.this.listitems.get(ContactItemListener.this.pos).get("number").toString(),
                                            ContactList.this.listitems.get(ContactItemListener.this.pos).get("photo").toString()
                                    });
                                    db.close();
                                    Intent intent = getIntent();
                                    ContactList.this.setResult(1, intent);
                                    ContactList.this.finish();
                                }
                            }).
                            setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = getIntent();
                                    ContactList.this.setResult(0, intent);
                                    ContactList.this.finish();
                                }
                            }).
                            create().show();
                }
            }
            listView.setOnItemClickListener(new ContactItemListener(this));
        } catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetPhoneContacts() {
        ContentResolver resolver = this.getContentResolver();
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                Bitmap contactPhoto = null;
                if(photoid > 0 ) {
                    Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                }else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }
                Map<String, Object> contact = new HashMap<String, Object>();
                contact.put("name",contactName);
                contact.put("number", phoneNumber);
                contact.put("photo", contactPhoto);
                this.listitems.add(contact);
            }
            phoneCursor.close();
        }
    }

    private void GetSIMContacts() {
        ContentResolver resolver = this.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                Map<String, Object> contact = new HashMap<String, Object>();
                contact.put("name", contactName);
                contact.put("number", phoneNumber);
                contact.put("photo", null);
                this.listitems.add(contact);
            }
            phoneCursor.close();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent(ContactList.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            //是否重新获取一遍？
            //this.listitems = null;
            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
