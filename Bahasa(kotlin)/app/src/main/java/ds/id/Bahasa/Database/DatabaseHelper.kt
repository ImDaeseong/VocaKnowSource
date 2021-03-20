package ds.id.Bahasa.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :  SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {

        private val tag = DatabaseHelper::class.java.simpleName

        //데이타베이스 버전
        private const val DATABASE_VERSION = 2

        //데이타베이스 이름
        private const val DATABASE_NAME = "IndonesiaDB.db"

        private var database: SQLiteDatabase? = null

        private var instance: DatabaseHelper? = null
        fun getInstance(context: Context): DatabaseHelper? {
            if (instance == null) {
                instance = DatabaseHelper(context)
            }
            return instance
        }
    }

    init {

        try {
            database = SQLiteDatabase.openDatabase(
                context.getDatabasePath(DATABASE_NAME).toString(),
                null,
                SQLiteDatabase.OPEN_READWRITE
            )
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        //Log.e(tag, "onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Log.e(tag, "onUpgrade")

        /*
        try {
            if (oldVersion === 1 && newVersion === 2) {
                db.execSQL("DROP TABLE IF EXISTS " + "RegKatas")
                val createTable = ("CREATE TABLE " +
                        "RegKatas" + "("
                        + "rIndex" + " INTEGER PRIMARY KEY,"
                        + "KataKor" + " TEXT NOT NULL, "
                        + "KataIndo" + " TEXT NOT NULL, "
                        + "KataIndoTambah" + " TEXT NOT NULL);")
                db.execSQL(createTable)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        */
    }

    @Synchronized
    override fun close() {
        if (database != null) {
            database!!.close()
        }
        super.close()
    }
}