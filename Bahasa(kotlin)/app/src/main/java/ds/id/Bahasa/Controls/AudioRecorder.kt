package ds.id.Bahasa.Controls

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException

class AudioRecorder {

    private val tag = AudioRecorder::class.java.name

    companion object {

        private var mediaRecorder: MediaRecorder? = null
        private val AudioFilePath: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path
        private var recordFile: File? = null
        var isRecording = false

        private var instance: AudioRecorder? = null
        fun getInstance(context: Context): AudioRecorder {
            if (instance == null) {
                instance = AudioRecorder()
            }
            return instance as AudioRecorder
        }
    }

    fun getFilePath(Filename: String): String {
        return "$AudioFilePath/$Filename.amr"
    }

    @Throws(IOException::class)
    fun StartRecord(Filename: String) {

        if (!isRecording) {

            try {

                if (mediaRecorder != null) {
                    mediaRecorder!!.reset()
                    mediaRecorder!!.release()
                    mediaRecorder = null
                }

                mediaRecorder = MediaRecorder()
                mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                mediaRecorder!!.setAudioSamplingRate(16000)

                val sPath = "$AudioFilePath/$Filename.amr"
                recordFile = File(sPath)
                mediaRecorder!!.setOutputFile(sPath)

                if (mediaRecorder != null) {
                    mediaRecorder!!.prepare()
                    mediaRecorder!!.start()
                }
                isRecording = true

            } catch (ignore: IOException) {
                Log.e(tag, ignore.message.toString())
                isRecording = false
            }
        }
    }

    fun StopRecord() {
        try {
            if (isRecording) {
                if (mediaRecorder != null) {
                    mediaRecorder!!.stop()
                    mediaRecorder!!.reset()
                    mediaRecorder!!.release()
                    mediaRecorder = null
                }
                isRecording = false
            }
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    fun deleteFile() {
        try {
            if (recordFile != null) {
                if (recordFile!!.exists()) {
                    recordFile!!.delete()
                }
                isRecording = false
                recordFile = null
            }
        } catch (ex: java.lang.Exception) {
            ex.message.toString()
        }
    }

    fun isFileExist(): Boolean {
        var bFile = false
        try {
            if (recordFile != null) {
                if (recordFile!!.exists()) {
                    bFile = true
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.message.toString()
        }
        return bFile
    }

    fun isRecording(): Boolean? {
        return isRecording
    }
}