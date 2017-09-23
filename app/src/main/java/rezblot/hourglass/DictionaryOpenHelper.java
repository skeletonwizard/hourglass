package rezblot.hourglass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rezblot on 9/23/17.
 */

//See https://developer.android.com/guide/topics/data/data-storage.html#db

//Won't need this until I need to save entire objects.

public class DictionaryOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "dictionary";
    private static final String DICTIONARY_TABLE_CREATE = "";
        //"CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
        //        KEY_WORD + " TEXT, " +
        //        KEY_DEFINITION + " TEXT);";

    public DictionaryOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //DictionaryOpenHelper(Context context) {
    //    //super(context, DATABASE_NAME, null, DATABASE_VERSION);
    //}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
