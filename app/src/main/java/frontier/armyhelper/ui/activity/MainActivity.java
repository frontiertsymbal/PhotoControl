package frontier.armyhelper.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

import frontier.armyhelper.R;
import frontier.armyhelper.util.Const;
import frontier.armyhelper.util.SharedPreferenceHelper;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener  {

    private Spinner vFirstParamSpinner;
    private Spinner vSecondParamSpinner;
    private Spinner vThirdParamSpinner;
    private Button vOkButton;
    private Button vClockButton;
    private Button vSettingsButton;

    private String mFirstSelectedParam;
    private String mSecondSelectedParam;
    private String mThirdSelectedParam;
    private ArrayList<String> mSpinnerParamsList;

    private ArrayAdapter<String> mSpinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initSpinnerParamsList();

        vFirstParamSpinner = (Spinner) findViewById(R.id.firstParamSpinner);
        vSecondParamSpinner = (Spinner) findViewById(R.id.secondParamSpinner);
        vThirdParamSpinner = (Spinner) findViewById(R.id.thirdParamSpinner);

        vOkButton = (Button) findViewById(R.id.buttonOk);
        vClockButton = (Button) findViewById(R.id.buttonClock);
        vSettingsButton = (Button) findViewById(R.id.buttonSettings);

        mSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mSpinnerParamsList);
        mSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vFirstParamSpinner.setAdapter(mSpinnerArrayAdapter);
        vFirstParamSpinner.setOnItemSelectedListener(this);
        vSecondParamSpinner.setAdapter(mSpinnerArrayAdapter);
        vSecondParamSpinner.setOnItemSelectedListener(this);
        vThirdParamSpinner.setAdapter(mSpinnerArrayAdapter);
        vThirdParamSpinner.setOnItemSelectedListener(this);

        vOkButton.setOnClickListener(this);
        vClockButton.setOnClickListener(this);
        vSettingsButton.setOnClickListener(this);
    }

    private void initSpinnerParamsList() {
        if (!SharedPreferenceHelper.isInit(this)) {
            mSpinnerParamsList = new ArrayList<>();
            Collections.addAll(mSpinnerParamsList, getResources().getStringArray(R.array.params));
            SharedPreferenceHelper.saveParamsList(this, mSpinnerParamsList);
        } else {
            mSpinnerParamsList = SharedPreferenceHelper.getParamsList(this);
        }

        mSpinnerParamsList.add(0, Const.EMPTY_FIELD);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonOk:
                intent = new Intent(MainActivity.this, ActionActivity.class);
                intent.putExtra(ActionActivity.EXTRA_FIRST_PARAM, mFirstSelectedParam);
                intent.putExtra(ActionActivity.EXTRA_SECOND_PARAM, mSecondSelectedParam);
                intent.putExtra(ActionActivity.EXTRA_THIRD_PARAM, mThirdSelectedParam);
                startActivity(intent);
                break;
            case R.id.buttonClock:
                intent = new Intent(MainActivity.this, ClockActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonSettings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getSelectedItem().toString().equals(Const.EMPTY_FIELD) ? "" : parent.getSelectedItem().toString();
        switch (parent.getId()) {
            case R.id.firstParamSpinner:
                mFirstSelectedParam = selectedItem;
                break;
            case R.id.secondParamSpinner:
                mSecondSelectedParam = selectedItem;
                break;
            case R.id.thirdParamSpinner:
                mThirdSelectedParam = selectedItem;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}