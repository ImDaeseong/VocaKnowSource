package ds.id.Bahasa.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;

public class KataManager {

    private static final String TAG = KataManager.class.getSimpleName();

    private DatabaseHelper databaseHelper = null;

    private static final String TABLE_KATAS = "Katas";

    private static KataManager instance = null;
    public static KataManager getInstance(Context context) {
        if (instance == null) {
            instance = new KataManager(context);
        }
        return instance;
    }

    private KataManager(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public ArrayList<KataItems> GetItems(){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<KataItems> list = new ArrayList<>();

        String query = "select * from Katas";
        Cursor cursor = database.rawQuery(query, null);

        try{

            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String Part1 = cursor.getString(cursor.getColumnIndex("Part1"));
                    String Part2 = cursor.getString(cursor.getColumnIndex("Part2"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"));
                    KataItems items = new KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return list;
    }

    public ArrayList<KataItems> GetItemsPart(String sPart){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<KataItems> list = new ArrayList<>();

        String query = "select * from Katas where Part1 " + " =  \"" + sPart + "\"";
        Cursor cursor = database.rawQuery(query, null);

        try{

            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String Part1 = cursor.getString(cursor.getColumnIndex("Part1"));
                    String Part2 = cursor.getString(cursor.getColumnIndex("Part2"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"));
                    KataItems items = new KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return list;
    }

    public ArrayList<KataItems> GetItemsPart(String sPart1, String sPart2){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<KataItems> list = new ArrayList<>();

        String query = "select * from Katas where Part1 " + "= \"" + sPart1 + "\" and Part2 = \"" + sPart2 + "\"";
        Cursor cursor = database.rawQuery(query, null);

        try{

            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String Part1 = cursor.getString(cursor.getColumnIndex("Part1"));
                    String Part2 = cursor.getString(cursor.getColumnIndex("Part2"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"));
                    KataItems items = new KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return list;
    }

    public ArrayList<KataItems> GetSearchKata(String sSearch){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<KataItems> list = new ArrayList<>();

        String query = "SELECT * from Katas where KataIndo LIKE '" + sSearch + "%'";
        Cursor cursor = database.rawQuery(query, null);

        try{

            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String Part1 = cursor.getString(cursor.getColumnIndex("Part1"));
                    String Part2 = cursor.getString(cursor.getColumnIndex("Part2"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"));
                    KataItems items = new KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return list;
    }

    public void UpdateKalimat(String sKataIndo, String sKataIndoTambah, String sSavedKataIndo){

        if(databaseHelper == null){
            return;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return;
        }

        try {

            ContentValues values = new ContentValues();
            values.put("KataIndo", sKataIndo);
            values.put("lembutlidah", sKataIndoTambah);
            database.update("Katas", values, "KataIndo" + " = ?",  new String[]{sSavedKataIndo});

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {
            if(database != null){
                database.close();
            }
        }
    }



    public ArrayList<regkataItems> GetRegItems(){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<regkataItems> list = new ArrayList<>();

        String query = "select * from RegKatas";
        Cursor cursor = null;

        try{

            cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"));
                    regkataItems items = new regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return list;
    }

    public ArrayList<regkataItems> GetRegSearchKata(String sSearch){

        if(databaseHelper == null){
            return null;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return null;
        }

        ArrayList<regkataItems> list = new ArrayList<>();
        Cursor cursor = null;

        try{

            String query = "SELECT * from RegKatas where KataIndo LIKE '" + sSearch + "%'";
            cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"));
                    regkataItems items = new regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

            query = "SELECT * from RegKatas where KataIndoTambah LIKE '" + sSearch + "%'";
            cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"));
                    regkataItems items = new regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

            query = "SELECT * from RegKatas where KataKor LIKE '" + sSearch + "%'";
            cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {

                for (int i=0; i < cursor.getCount(); i++){
                    int rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"));
                    String KataKor = cursor.getString(cursor.getColumnIndex("KataKor"));
                    String KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"));
                    String KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"));
                    regkataItems items = new regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah);
                    list.add(items);
                    cursor.moveToNext();
                }
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }

        return list;
    }

    public boolean isExistData(String sKataIndo){

        boolean bFindData = false;

        if(databaseHelper == null){
            return bFindData;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return bFindData;
        }

        String query = "select * from RegKatas where KataIndo " + " =  \"" + sKataIndo + "\"";
        Cursor cursor = database.rawQuery(query, null);

        try {

            if (cursor.moveToFirst()) {
                bFindData = true;
            }

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {

            if(cursor != null){
                cursor.close();
                cursor = null;
            }

            if(database != null){
                database.close();
            }
        }
        return bFindData;
    }

    public void InsertRegKata(String sKataIndo, String sKataIndoTambah, String sKataKor){

        if(databaseHelper == null){
            return;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return;
        }

        try {

            ContentValues values = new ContentValues();
            values.put("KataIndo", sKataIndo);
            values.put("KataIndoTambah", sKataIndoTambah);
            values.put("KataKor", sKataKor);
            database.insert("RegKatas", null, values);

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {
            if(database != null){
                database.close();
            }
        }
    }

    public void UpdateRegKata(String sSavedKataIndo, String sKataIndo, String sKataIndoTambah, String sKataKor){

        if(databaseHelper == null){
            return;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return;
        }

        try {

            ContentValues values = new ContentValues();
            values.put("KataIndo", sKataIndo);
            values.put("KataIndoTambah", sKataIndoTambah);
            values.put("KataKor", sKataKor);
            database.update("RegKatas", values, "KataIndo" + " = ?",  new String[]{sSavedKataIndo});

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {
            if(database != null){
                database.close();
            }
        }
    }

    public void DeleteRegKata(String sKataIndo){

        if(databaseHelper == null){
            return;
        }

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        if(database == null){
            return;
        }

        try {

            ContentValues values = new ContentValues();
            database.delete("RegKatas", "KataIndo" + " = ?",  new String[]{sKataIndo});

        } catch (SQLiteException e){
        } catch (Exception e){
        } finally {
            if(database != null){
                database.close();
            }
        }
    }

}