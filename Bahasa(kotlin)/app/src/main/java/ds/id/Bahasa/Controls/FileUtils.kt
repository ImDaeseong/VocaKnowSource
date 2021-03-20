package ds.id.Bahasa.Controls

import java.io.*

object FileUtils {

    fun copy(src: File?, outPut: File?) {

        try {
            var fis: BufferedInputStream? = null
            var fos: BufferedOutputStream? = null
            try {
                fis = BufferedInputStream(FileInputStream(src))
                fos = BufferedOutputStream(FileOutputStream(outPut))
                var len: Int
                val buffer = ByteArray(8 * 1024)
                while (fis.read().also { len = it } != -1) {
                    fos.write(buffer, 0, len)
                }
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } finally {
                        fos?.close()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun copy(inputStream: InputStream, outPutFile: File?) {

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(outPutFile)
            val buffer = ByteArray(1024 * 8)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                fos.write(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    inputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun FileExists(sPath: String?): Boolean {
        var bFind = false
        try {
            val file = File(sPath)
            if (file.exists()) {
                bFind = true
            }
        } catch (ex: java.lang.Exception) {
            ex.message.toString()
        }
        return bFind
    }

    fun FileDelete(sPath: String?) {
        try {
            val file = File(sPath)
            if (file.exists()) {
                file.delete()
            }
        } catch (ex: java.lang.Exception) {
            ex.message.toString()
        }
    }

}
