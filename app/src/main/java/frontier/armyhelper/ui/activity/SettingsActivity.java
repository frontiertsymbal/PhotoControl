package frontier.armyhelper.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import frontier.armyhelper.R;
import frontier.armyhelper.ui.adapter.ArrayListAdapter;
import frontier.armyhelper.util.Const;
import frontier.armyhelper.util.SharedPreferenceHelper;

public class SettingsActivity extends Activity implements View.OnClickListener {

    private ListView mParamsListView;
    private Button mSaveButton;
    private Button mAddButton;
    private Button mAboutAppButton;
    private EditText mValueEditText;

    private ArrayListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        mParamsListView = (ListView) findViewById(R.id.paramsList);
        mSaveButton = (Button) findViewById(R.id.buttonSave);
        mAddButton = (Button) findViewById(R.id.buttonAdd);
        mAboutAppButton = (Button) findViewById(R.id.buttonAboutApp);
        mValueEditText = (EditText) findViewById(R.id.valueEditText);

        mSaveButton.setOnClickListener(this);
        mAddButton.setOnClickListener(this);
        mAboutAppButton.setOnClickListener(this);

        mAdapter = new ArrayListAdapter(this, 0);
        mAdapter.addAll(SharedPreferenceHelper.getParamsList(this));
        mParamsListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSave:
                final ArrayList<String> paramsList = mAdapter.getParamsList();
                if (paramsList.size() == 0) {
                    showEmptyListErrorDialog(paramsList);
                } else {
                    saveList(paramsList);
                }
                break;
            case R.id.buttonAdd:
                String value = mValueEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    mValueEditText.setText("");
                    mAdapter.add(value);
                    mAdapter.notifyDataSetChanged();
                } else {
                    showEmptyParamErrorDialog();
                }
                break;
            case R.id.buttonAboutApp:
                showAboutAppDialog();
                break;
        }
    }

    private void showAboutAppDialog() {
        String aboutAppText = getString(R.string.aboutAppMessage);
        String email = getString(R.string.email);

        SpannableString spannableString = new SpannableString(getString(R.string.aboutAppMessagePattern, aboutAppText, email));
        spannableString.setSpan(new UnderlineSpan(), aboutAppText.length() + 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Linkify.addLinks(spannableString, Linkify.EMAIL_ADDRESSES);

        final TextView message = new TextView(SettingsActivity.this);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Const.DIALOG_DIP_PADDING, getResources().getDisplayMetrics());
        int topPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Const.DIALOG_DIP_TOP_PADDING, getResources().getDisplayMetrics());
        message.setPadding(padding, topPadding, padding, 0);
        message.setTextSize(Const.DIALOG_DIP_TEXT_SIZE);
        message.setText(spannableString);
        message.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.appName)
                .setCancelable(true)
                .setView(message)
                .setNegativeButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showEmptyParamErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.valueIsEmptyMessage)
                .setCancelable(false)
                .setNegativeButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEmptyListErrorDialog(final ArrayList<String> paramsList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.paramsListIsEmptyMessage)
                .setCancelable(false)
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveList(paramsList);
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveList(ArrayList<String> paramsList) {
        SharedPreferenceHelper.clearSharedPreferences(this);
        SharedPreferenceHelper.saveParamsList(this, paramsList);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
