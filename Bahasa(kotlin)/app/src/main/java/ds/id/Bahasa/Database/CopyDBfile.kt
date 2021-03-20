package ds.id.Bahasa.Database

import android.content.Context
import java.io.*

class CopyDBfile(context: Context) {

    private val tag = CopyDBfile::class.java.simpleName
    private val db_name  = "IndonesiaDB.db"

    init {

        val db_path  = "/data/data/" + context.applicationContext.packageName + "/databases/"

        try {

            val myinput = context.assets.open(db_name )
            val file = File(db_path  + db_name )

            if (!file.exists()) {

                val dir = File(db_path)
                dir.mkdirs()
                val myoutput: OutputStream = FileOutputStream(db_path  + db_name )
                val buffer = ByteArray(1024)
                var length: Int
                while (myinput.read(buffer).also { length = it } > 0) {
                    myoutput.write(buffer, 0, length)
                }
                myoutput.flush()
                myoutput.close()
                myinput.close()
            }
        } catch (e: IOException) {
        }
    }
}