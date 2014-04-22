package com.call.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

public class DB_Helper {
    public static SQLiteDatabase DB_Open(String name)
    {
        if(name == null)
        {
            return null;
        }
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
           return null;
        }
        String appdir_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Call";
        File appdir = new File(appdir_path);
        if(!appdir.exists()){
            appdir.mkdir();
        }
        String dbdir_path = appdir_path + File.separator + "databases";
        File dbdir = new File(dbdir_path);
        if(!dbdir.exists()){
            dbdir.mkdir();
        }
        String dbfile_path = dbdir_path + File.separator + name + ".db3";
        File dbfile = new File(dbfile_path);
        if(!dbfile.exists()){
            try{
                if(!dbfile.createNewFile()){
                    return null;
                }
                return SQLiteDatabase.openOrCreateDatabase(dbfile_path, null);
            }
            catch(IOException ioex){
                return null;
            }
        }
        else{
            return SQLiteDatabase.openOrCreateDatabase(dbfile_path, null);
        }
    }

    public static boolean DB_TableExist(SQLiteDatabase db, String table){
        if(table == null)
        {
            return false;
        }
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+table.trim()+"' ";
                Cursor cursor = db.rawQuery(sql, null);
                if(cursor.moveToNext()){
                    int count = cursor.getInt(0);
                    if(count>0){
                        return true;
                    }
                }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
