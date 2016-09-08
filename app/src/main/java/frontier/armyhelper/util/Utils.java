package frontier.armyhelper.util;

import android.view.Window;
import android.view.WindowManager;

public class Utils {

    public static void setKeepScreenOn (Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
}
