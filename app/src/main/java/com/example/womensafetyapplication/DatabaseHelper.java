package com.example.womensafetyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_MOBILE_1 = "mobile_no_1";
    private static final String COLUMN_USER_MOBILE_2 = "mobile_no_2";
    private static final String COLUMN_USER_MOBILE_3 = "mobile_no_3";
    private static final String COLUMN_USER_MOBILE_4 = "mobile_no_4";
    private static final String COLUMN_USER_MOBILE_5 = "mobile_no_5";
    private static final String COLUMN_USER_MOBILE_ID = "mobile_no_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String DATABASE_NAME = "UserManager.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_MOBILE_REGISTER = "Mobile_Reg_no";
    private static final String TABLE_USER = "user";
    private String CREATE_USER_TABLE = "CREATE TABLE user(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,user_email TEXT,user_password TEXT)";
    private String CREATE_USER_TABLE_MOBILE = " CREATE TABLE Mobile_Reg_no ( mobile_no_id INTEGER PRIMARY KEY AUTOINCREMENT,mobile_no_1 TEXT NULL,mobile_no_2 TEXT NULL,mobile_no_3 TEXT NULL,mobile_no_4 Text null,mobile_no_5 Text null )";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";
    private String Drop_User_Table_Mobile = " Drop Table if EXISTS Mobile_Reg_no ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.CREATE_USER_TABLE);
        db.execSQL(this.CREATE_USER_TABLE_MOBILE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(this.DROP_USER_TABLE);
        db.execSQL(this.Drop_User_Table_Mobile);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addMobile(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_MOBILE_1, user.getMobile_1());
        contentValues.put(COLUMN_USER_MOBILE_2, user.getMobile_2());
        contentValues.put(COLUMN_USER_MOBILE_3, user.getMobile_3());
        contentValues.put(COLUMN_USER_MOBILE_4, user.getMobile_4());
        contentValues.put(COLUMN_USER_MOBILE_5, user.getMobile_5());
        long insert = db.insert(TABLE_NAME_MOBILE_REGISTER, null, contentValues);
    }

    public List<User> getAllUser() {
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD};
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, "user_name ASC");
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.update(TABLE_USER, values, "user_id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, "user_id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, columns, "user_email = ?", selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, "user_name = ? AND user_password = ?", selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUserMo(int id) {
        String[] columns = {COLUMN_USER_MOBILE_ID};
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {id + ""};
        Cursor cursor = db.query(TABLE_NAME_MOBILE_REGISTER, columns, "mobile_no_id = ?", selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public Cursor getAllMobileNo() {
        return getWritableDatabase().rawQuery("SELECT * FROM Mobile_Reg_no", null);
    }

    public Cursor getAllMobileNo1() {
        return getWritableDatabase().rawQuery("SELECT * FROM Mobile_Reg_no", null);
    }

    public Cursor getAllMobileNo2() {
        return getWritableDatabase().rawQuery("SELECT * FROM Mobile_Reg_no", null);
    }

    public Cursor getAllMobileNo3() {
        return getWritableDatabase().rawQuery("SELECT * FROM Mobile_Reg_no", null);
    }

    public Cursor getAllMobileNo4() {
        return getWritableDatabase().rawQuery("SELECT * FROM Mobile_Reg_no", null);
    }

    public Cursor getdata(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

}
