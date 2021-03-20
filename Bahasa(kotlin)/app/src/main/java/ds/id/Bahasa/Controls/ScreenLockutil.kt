package ds.id.Bahasa.Controls

import android.view.Window
import android.view.WindowManager

object ScreenLockutil {

    fun Lock_Flag(window: Window, bScreenLock: Boolean) {

        if (bScreenLock) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

}