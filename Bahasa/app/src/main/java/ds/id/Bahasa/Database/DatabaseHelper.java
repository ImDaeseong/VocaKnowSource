package ds.id.Bahasa.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    static final String DATABASE_NAME = "IndonesiaDB.db";
    static final int DATABASE_VERSION = 2;//static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    private static DatabaseHelper instance;
    public static DatabaseHelper getInstance(Context context ) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            database = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).toString(), null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Log.e(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Log.e(TAG, "onUpgrade");

        try {

            if(oldVersion == 1 && newVersion == 2){

                db.execSQL("DROP TABLE IF EXISTS " + "RegKatas");

                String createTable = "CREATE TABLE " +
                        "RegKatas" + "("
                        + "rIndex" + " INTEGER PRIMARY KEY,"
                        + "KataKor" + " TEXT NOT NULL, "
                        + "KataIndo" + " TEXT NOT NULL, "
                        + "KataIndoTambah" + " TEXT NOT NULL);";
                db.execSQL(createTable);
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {

        if (database != null) {
            database.close();
        }
        super.close();
    }
}
