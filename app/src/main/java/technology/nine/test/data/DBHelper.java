package technology.nine.test.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import technology.nine.test.model.Items;

import static technology.nine.test.data.BarcodeContract.barcodeEntry.*;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Barcode.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String SQL_CREATE_BARCODE_TABLE = " CREATE TABLE " + BARCODE_TABLE + "(" +
            _ID + " INTEGER PRIMARY KEY, " +
            VALUE + " Text);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BARCODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertBarcode(String value) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VALUE, value);
        return (writableDatabase.insert(BARCODE_TABLE, null, values) != -1);
    }

    public List<Items>  getAllBarcodes(){
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        List<Items> items = new ArrayList<>();
        Cursor cursor = readableDatabase.query(BARCODE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC "
        );
        try {
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndexOrThrow(VALUE));
                items.add(new Items(value));
            }
        } finally {
            cursor.close();
        }
        return items;
    }
}
