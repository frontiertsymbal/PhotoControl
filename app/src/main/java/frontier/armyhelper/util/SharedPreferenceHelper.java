package frontier.armyhelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;

public class SharedPreferenceHelper {

    private static final String PREFERENCE_NAME = "photo_control";
    private static final String KEY_PARAM = "PARAM";
    private static final String KEY_IS_INIT = "IS_INIT";

    private SharedPreferenceHelper() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveParamsList(Context context, ArrayList<String> paramsList) {
        for (int i = 0; i < paramsList.size(); i++) {
            String value = paramsList.get(i);
            if (!TextUtils.isEmpty(value)) {
                setKeyParam(context, i, value);
            }
        }
        setIsInit(context, true);
    }

    public static ArrayList<String> getParamsList(Context context) {
        ArrayList<String> paramsList = new ArrayList<>();
        int count = 0;
        boolean hasValue = true;
        while (hasValue) {
            String value = getValue(context, count);
            count++;
            if (!TextUtils.isEmpty(value)) {
                paramsList.add(value);
            } else {
                hasValue = false;
            }
        }
        return paramsList;
    }

    private static void setKeyParam(Context context, int pos, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_PARAM + pos, value);
        editor.commit();
    }

    private static String getValue(Context context, int pos) {
        return getSharedPreferences(context).getString(KEY_PARAM + pos, null);
    }

    private static void setIsInit(Context context, boolean isInit) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_INIT, isInit);
        editor.commit();
    }

    public static boolean isInit(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_INIT, false);
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
