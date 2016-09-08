package frontier.armyhelper.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import frontier.armyhelper.R;
import frontier.armyhelper.util.Const;
import frontier.armyhelper.util.Utils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActionActivity extends Activity {

    final Calendar mCalendar = GregorianCalendar.getInstance();

    public static final String EXTRA_FIRST_PARAM = "FIRST_PARAM";
    public static final String EXTRA_SECOND_PARAM = "SECOND_PARAM";
    public static final String EXTRA_THIRD_PARAM = "THIRD_PARAM";

    private TextView vFirstParamTextView;
    private TextView vSecondParamTextView;
    private TextView vThirdParamTextView;
    private TextView vFourthParamTextView;
    private TextView vFifthParamTextView;

    private Subscription mTimerSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action);
        Utils.setKeepScreenOn(getWindow());

        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT, Locale.US);
        Date date = new Date(mCalendar.getTimeInMillis());

        String firstParam = getIntent().getStringExtra(EXTRA_FIRST_PARAM);
        String secondParam = getIntent().getStringExtra(EXTRA_SECOND_PARAM);
        String thirdParam = getIntent().getStringExtra(EXTRA_THIRD_PARAM);

        vFirstParamTextView = (TextView) findViewById(R.id.firstParamTextView);
        vSecondParamTextView = (TextView) findViewById(R.id.secondParamTextView);
        vThirdParamTextView = (TextView) findViewById(R.id.thirdParamTextView);
        vFourthParamTextView = (TextView) findViewById(R.id.fourthParamTextView);
        vFifthParamTextView = (TextView) findViewById(R.id.fifthParamTextView);

        vFirstParamTextView.setText(firstParam);
        vSecondParamTextView.setText(secondParam);
        vThirdParamTextView.setText(thirdParam);
        vFifthParamTextView.setText(dateFormat.format(date));

        checkAndSetAmPm();

        mTimerSubscription = Observable.interval(1, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        checkAndSetAmPm();
                    }
                });
    }

    @SuppressLint("SwitchIntDef")
    private void checkAndSetAmPm() {
        switch (mCalendar.get(Calendar.AM_PM)) {
            case Calendar.AM:
                vFourthParamTextView.setText("");
                break;
            case Calendar.PM:
                vFourthParamTextView.setText(R.string.pmIndicator);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTimerSubscription != null && !mTimerSubscription.isUnsubscribed()) {
            mTimerSubscription.unsubscribe();
            mTimerSubscription = null;
        }
    }
}
