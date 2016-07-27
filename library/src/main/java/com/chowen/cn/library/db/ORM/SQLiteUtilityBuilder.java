package com.chowen.cn.library.db.ORM;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chowen.cn.library.log.Logger;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
public class SQLiteUtilityBuilder {
    public static final String TAG = "SQLiteUtilityBuilder";

    static final String DEFAULT_DB = "com_m_default_db";// 默认DB名称

    private String path;// DB的SD卡路径

    private String dbName = DEFAULT_DB;

    private int version = 1;// DB的Version，每次升级DB都默认先清库

    private boolean sdcardDb = false;

    public SQLiteUtilityBuilder configDBName(String dbName) {
        this.dbName = dbName;

        return this;
    }

    public SQLiteUtilityBuilder configVersion(int version) {
        this.version = version;

        return this;
    }

    public SQLiteUtilityBuilder configSdcardPath(String path) {
        this.path = path;
        sdcardDb = true;

        return this;
    }

    public SQLiteUtility build(Context context) {
        SQLiteDatabase db;

        if (sdcardDb) {
            db = openSdcardDb(path, dbName, version);
        } else {
            db = new SQLiteDbHelper(context, dbName, version).getWritableDatabase();
        }

        return new SQLiteUtility(dbName, db);
    }

    static SQLiteDatabase openSdcardDb(String path, String dbName, int version) {
        SQLiteDatabase db = null;
        File dbf = new File(path + File.separator + dbName + ".db");

        if (dbf.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        } else {
            dbf.getParentFile().mkdirs();

            try {
                if (dbf.createNewFile()) {
                    Logger.d(TAG, "新建一个库在sd卡, 库名 = %s, 路径 = %s", dbName, dbf.getAbsolutePath());
                    db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
                }
            } catch (IOException e) {
                throw new RuntimeException("新建库失败, 库名 = " + dbName + ", 路径 = " + path, e);
            }
        }

        if (db != null) {
            int dbVersion = db.getVersion();
            Logger.d(TAG, "表 %s 的version = %d, newVersion = %d", dbName, dbVersion, version);

            if (dbVersion < version) {
                dropDb(db);

                // 更新DB的版本信息
                db.beginTransaction();
                try {
                    db.setVersion(version);
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Logger.w(TAG, "更新DB的版本信息异常");
                } finally {
                    db.endTransaction();
                }
            }

            return db;
        }

        throw new RuntimeException("打开库失败, 库名 = " + dbName + ", 路径 = " + path);
    }

    static class SQLiteDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;

        SQLiteDbHelper(Context context, String dbName, int dbVersion) {
            super(context, dbName, null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            dropDb(db);
            onCreate(db);
        }
    }

    static void dropDb(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name != 'sqlite_sequence'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                db.execSQL("DROP TABLE " + cursor.getString(0));
                Logger.d(TAG, "删除表 = " + cursor.getString(0));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
