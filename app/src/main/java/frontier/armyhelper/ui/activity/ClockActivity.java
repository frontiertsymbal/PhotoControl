package frontier.armyhelper.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import frontier.armyhelper.R;
import frontier.armyhelper.util.Utils;

public class ClockActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_activity);
        Utils.setKeepScreenOn(getWindow());
    }
}
