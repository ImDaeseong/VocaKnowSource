package ds.id.Bahasa.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException

class KataManager {

    private val tag = KataManager::class.java.simpleName

    private val TABLE_KATAS = "Katas"

    companion object {

        private var databaseHelper: DatabaseHelper? = null

        private var instance: KataManager? = null
        fun getInstance(context: Context): KataManager {
            if (instance == null) {

                databaseHelper = DatabaseHelper(context)

                instance = KataManager()
            }
            return instance as KataManager
        }
    }

    fun GetItems(): ArrayList<KataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<KataItems> = ArrayList()
        val query = "select * from Katas"
        var cursor: Cursor? = database.rawQuery(query, null)

        try {
            if (cursor!!.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex: Int = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val Part1: String = cursor.getString(cursor.getColumnIndex("Part1"))
                    val Part2: String = cursor.getString(cursor.getColumnIndex("Part2"))
                    val KataKor: String = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo: String = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val lembutlidah: String = cursor.getString(cursor.getColumnIndex("lembutlidah"))
                    val items = KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun GetItemsPart(sPart: String): ArrayList<KataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<KataItems> = ArrayList()
        val query = "select * from Katas where Part1  =  \"$sPart\""
        var cursor = database.rawQuery(query, null)

        try {
            if (cursor!!.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val Part1 = cursor.getString(cursor.getColumnIndex("Part1"))
                    val Part2 = cursor.getString(cursor.getColumnIndex("Part2"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"))
                    val items = KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun GetItemsPart(sPart1: String, sPart2: String): ArrayList<KataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<KataItems> = ArrayList()
        val query = "select * from Katas where Part1 = \"$sPart1\" and Part2 = \"$sPart2\""
        var cursor = database.rawQuery(query, null)

        try {
            if (cursor!!.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val Part1 = cursor.getString(cursor.getColumnIndex("Part1"))
                    val Part2 = cursor.getString(cursor.getColumnIndex("Part2"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"))
                    val items = KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun GetSearchKata(sSearch: String): ArrayList<KataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<KataItems> = ArrayList()
        val query = "SELECT * from Katas where KataIndo LIKE '$sSearch%'"
        var cursor = database.rawQuery(query, null)

        try {
            if (cursor!!.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val Part1 = cursor.getString(cursor.getColumnIndex("Part1"))
                    val Part2 = cursor.getString(cursor.getColumnIndex("Part2"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val lembutlidah = cursor.getString(cursor.getColumnIndex("lembutlidah"))
                    val items = KataItems(rIndex, Part1, Part2, KataKor, KataIndo, lembutlidah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun UpdateKalimat(sKataIndo: String?, sKataIndoTambah: String?, sSavedKataIndo: String) {

        if (databaseHelper == null) {
            return
        }

        val database = databaseHelper!!.readableDatabase ?: return

        try {
            val values = ContentValues()
            values.put("KataIndo", sKataIndo)
            values.put("lembutlidah", sKataIndoTambah)
            database.update("Katas", values, "KataIndo" + " = ?", arrayOf(sSavedKataIndo))
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            database?.close()
        }
    }


    fun GetRegItems(): ArrayList<regkataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<regkataItems> = ArrayList()
        val query = "select * from RegKatas"
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"))
                    val items = regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun GetRegSearchKata(sSearch: String): ArrayList<regkataItems>? {

        if (databaseHelper == null) {
            return null
        }

        val database = databaseHelper!!.readableDatabase ?: return null
        val list: ArrayList<regkataItems> = ArrayList()
        var cursor: Cursor? = null

        try {

            var query = "SELECT * from RegKatas where KataIndo LIKE '$sSearch%'"
            cursor = database.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"))
                    val items = regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }

            query = "SELECT * from RegKatas where KataIndoTambah LIKE '$sSearch%'"
            cursor = database.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"))
                    val items = regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }

            query = "SELECT * from RegKatas where KataKor LIKE '$sSearch%'"
            cursor = database.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    val rIndex = cursor.getInt(cursor.getColumnIndex("rIndex"))
                    val KataKor = cursor.getString(cursor.getColumnIndex("KataKor"))
                    val KataIndo = cursor.getString(cursor.getColumnIndex("KataIndo"))
                    val KataIndoTambah = cursor.getString(cursor.getColumnIndex("KataIndoTambah"))
                    val items = regkataItems(rIndex, KataKor, KataIndo, KataIndoTambah)
                    list.add(items)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return list
    }

    fun isExistData(sKataIndo: String): Boolean {

        var bFindData = false
        if (databaseHelper == null) {
            return bFindData
        }

        val database = databaseHelper!!.readableDatabase ?: return bFindData
        val query = "select * from RegKatas where KataIndo  =  \"$sKataIndo\""
        var cursor = database.rawQuery(query, null)

        try {
            if (cursor!!.moveToFirst()) {
                bFindData = true
            }
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
            database?.close()
        }
        return bFindData
    }

    fun InsertRegKata(sKataIndo: String?, sKataIndoTambah: String?, sKataKor: String?) {

        if (databaseHelper == null) {
            return
        }

        val database = databaseHelper!!.readableDatabase ?: return

        try {
            val values = ContentValues()
            values.put("KataIndo", sKataIndo)
            values.put("KataIndoTambah", sKataIndoTambah)
            values.put("KataKor", sKataKor)
            database.insert("RegKatas", null, values)
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            database?.close()
        }
    }

    fun UpdateRegKata(
        sSavedKataIndo: String,
        sKataIndo: String?,
        sKataIndoTambah: String?,
        sKataKor: String?
    ) {
        if (databaseHelper == null) {
            return
        }
        val database = databaseHelper!!.readableDatabase ?: return
        try {
            val values = ContentValues()
            values.put("KataIndo", sKataIndo)
            values.put("KataIndoTambah", sKataIndoTambah)
            values.put("KataKor", sKataKor)
            database.update("RegKatas", values, "KataIndo" + " = ?", arrayOf(sSavedKataIndo))
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            database?.close()
        }
    }

    fun DeleteRegKata(sKataIndo: String) {
        if (databaseHelper == null) {
            return
        }
        val database = databaseHelper!!.readableDatabase ?: return
        try {
            val values = ContentValues()
            database.delete("RegKatas", "KataIndo" + " = ?", arrayOf(sKataIndo))
        } catch (e: SQLiteException) {
        } catch (e: java.lang.Exception) {
        } finally {
            database?.close()
        }
    }


}