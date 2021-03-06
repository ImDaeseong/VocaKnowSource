package ds.id.Bahasa.Controls

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AudioPlayer {

    private val tag = AudioPlayer::class.java.name

    private var onMediaPlayerListener: OnMediaPlayerListener? = null

    companion object {

        private var mediaPlayer: MediaPlayer? = null

        private var instance: AudioPlayer? = null
        fun getInstance(context: Context): AudioPlayer {
            if (instance == null) {

                mediaPlayer = MediaPlayer()

                instance = AudioPlayer()
            }
            return instance as AudioPlayer
        }
    }

    fun init() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
    }

    fun release() {
        try {

            removeListener()

            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }
        } catch (ex: Exception) {
            Log.d(tag, ex.message.toString())
        }
    }

    fun play(sPath: String?, onMediaPlayerListener: OnMediaPlayerListener?) {
        try {

            if (mediaPlayer != null) {

                this.onMediaPlayerListener = onMediaPlayerListener
                mediaPlayer!!.setDataSource(sPath)
                mediaPlayer!!.prepare()
                mediaPlayer!!.setOnCompletionListener {
                    onMediaPlayerListener?.onCompletion(true)
                }
                mediaPlayer!!.setOnPreparedListener { mp ->
                    onMediaPlayerListener?.onPrepared(mp.duration)
                    //mp.start()
                }
                mediaPlayer!!.setOnErrorListener { mp, what, extra ->
                    onMediaPlayerListener?.onCompletion(false)
                    false
                }
            }
        } catch (ex: java.lang.Exception) {
            onMediaPlayerListener?.onCompletion(false)
            Log.d(tag, ex.message.toString())
        }
    }

    fun start() {
        if (mediaPlayer != null) {
            mediaPlayer!!.start()
        }
    }

    fun stop() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
    }

    fun pause() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        }
    }

    fun getCurrentPosition(): Int {
        return if (mediaPlayer != null) mediaPlayer!!.currentPosition else 0
    }

    fun getDuration(): Int {
        return if (mediaPlayer != null) mediaPlayer!!.duration else 0
    }

    fun isPlaying(): Boolean {
        return if (mediaPlayer != null) mediaPlayer!!.isPlaying else false
    }

    fun seekTo(position: Int) {
        if (mediaPlayer != null) {
            mediaPlayer!!.seekTo(position)
        }
    }

    private fun removeListener() {
        try {
            onMediaPlayerListener = null
        } catch (ex: java.lang.Exception) {
            Log.d(tag, ex.message.toString())
        }
    }

    interface OnMediaPlayerListener {
        fun onCompletion(bComplete: Boolean)
        fun onPrepared(mDuration: Int)
    }
}