package com.example.forgrandmon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends Activity {
    private ArrayList<Contact> contactlist = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        try{
            Contacts contacts = new Contacts(this);
            this.contactlist = contacts.GetContacts();
            BaseAdapter baseAdapter = new ContactsAdapter(this, this.contactlist);
            ListView listView = (ListView)findViewById(R.id.contact_list);
            listView.setAdapter(baseAdapter);

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
                    new AlertDialog.Builder(ContactListActivity.this).
                            setIcon(R.drawable.ic_launcher).
                            setTitle("确认信息").
                            setView(contact_confirm).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ContactModel contactModel = new ContactModel(ContactListActivity.this);
                                    Contact contact = ContactListActivity.this.contactlist.get(ContactItemListener.this.pos);
                                    contactModel.InsertOrUpdate(contact);
                                    contactModel.close();
                                    Intent intent = getIntent();
                                    ContactListActivity.this.setResult(1, intent);
                                    ContactListActivity.this.finish();
                                }
                            }).
                            setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
            return true;
        }*/
    	finish();
        return super.onKeyDown(keyCode, event);
    }

}
