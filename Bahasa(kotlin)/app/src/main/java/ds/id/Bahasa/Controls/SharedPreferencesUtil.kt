package ds.id.Bahasa.Controls

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil {

    private val tag: String = SharedPreferencesUtil::class.java.simpleName

    private var sharedPreferences: SharedPreferences? = null

    private val FILE_NAME = "bahasaIndonesiaShareData"

    companion object {
        private var instance: SharedPreferencesUtil? = null
        fun getInstance(): SharedPreferencesUtil {
            if (instance == null) {
                instance = SharedPreferencesUtil()
            }
            return instance as SharedPreferencesUtil
        }
    }

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun setValue(sKey: String?, oData: Any) {

        val sType = oData.javaClass.simpleName
        val editor = sharedPreferences!!.edit()
        when (sType) {
            "String" -> {
                editor.putString(sKey, oData as String)
            }
            "Integer" -> {
                editor.putInt(sKey, (oData as Int))
            }
            "Boolean" -> {
                editor.putBoolean(sKey, (oData as Boolean))
            }
            "Float" -> {
                editor.putFloat(sKey, (oData as Float))
            }
            "Long" -> {
                editor.putLong(sKey, (oData as Long))
            }
        }
        editor.commit()
    }

    operator fun getValue(sKey: String?, oData: Any): Any? {

        val sType = oData.javaClass.simpleName
        when (sType) {
            "String" -> {
                return sharedPreferences!!.getString(sKey, oData as String)
            }
            "Integer" -> {
                return sharedPreferences!!.getInt(sKey, (oData as Int))
            }
            "Boolean" -> {
                return sharedPreferences!!.getBoolean(sKey, (oData as Boolean))
            }
            "Float" -> {
                return sharedPreferences!!.getFloat(sKey, (oData as Float))
            }
            "Long" -> {
                return sharedPreferences!!.getLong(sKey, (oData as Long))
            }
            else -> return null
        }
    }

    fun remove(sKey: String?) {
        val editor = sharedPreferences!!.edit()
        editor.remove(sKey)
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

    operator fun contains(sKey: String?): Boolean {
        return sharedPreferences!!.contains(sKey)
    }

    fun getAll(): Map<String?, *>? {
        return sharedPreferences!!.all
    }
}
