package ds.id.Bahasa.Controls;

import android.view.Window;
import android.view.WindowManager;

public class ScreenLockutil {

    public static void Lock_Flag(Window window, boolean bScreenLock)
    {
        if(bScreenLock) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
