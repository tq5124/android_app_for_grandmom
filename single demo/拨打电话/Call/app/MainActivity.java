package com.call.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private long ExitTime = 0;
    private SQLiteDatabase db;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
                    Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
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


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(this.db != null && db.isOpen())
        {
            db.close();
        }
    }

    private void RefreshList()
    {
        db = DB_Helper.DB_Open("contact");
        if (!DB_Helper.DB_TableExist(db, "contact_list"))
        {
            db.execSQL("CREATE TABLE contact_list(_id integer primary key autoincrement, name varchar(20), number varchar(50),raw_id varchar(50),photo_id varchar(50))");
        }
        Cursor cursor = db.rawQuery("SELECT * from contact_list", null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.contact_item, cursor,
                new String[]{"name", "number", "photo_id"},
                new int[] {R.id.contact_name, R.id.contact_number, R.id.contact_photo}
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((TextView) view.findViewById(R.id.contact_number)).getText()));
                startActivity(dialIntent);
            }
        });
        db.close();
    }
}
