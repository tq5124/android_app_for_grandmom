package com.example.forgrandmon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactActivity extends Activity {
    private long ExitTime = 0;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        //无SD卡
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Toast.makeText(this, "无法获取SD卡", Toast.LENGTH_LONG).show();
            finish();
        }
        try{
            Button add_contact = (Button)findViewById(R.id.add_contact);
            add_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ContactActivity.this, ContactListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityForResult(intent, 0);
                }
            });
            listView = (ListView)findViewById(R.id.store_contact_list);

            this.RefreshList();
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode ==0 && resultCode == 1)
        {
            this.RefreshList();
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
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-ExitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次返回键，退出程序！", Toast.LENGTH_SHORT).show();
                ExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void RefreshList()
    {
        ContactModel contactModel = new ContactModel(this);
        ArrayList<Contact> dbcontacts = contactModel.GetDBContacts();
        BaseAdapter baseAdapter = new ContactsAdapter(this, dbcontacts);
        ListView listView = (ListView)findViewById(R.id.store_contact_list);
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((TextView) view.findViewById(R.id.contact_number)).getText()));
                startActivity(dialIntent);
            }
        });
        contactModel.close();
    }
}
