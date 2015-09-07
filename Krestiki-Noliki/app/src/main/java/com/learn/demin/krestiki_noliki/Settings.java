package com.learn.demin.krestiki_noliki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends ActionBarActivity {

    SharedPreferences sPref;

    final static String SAVED_FIELD_SIZE = "saved_field_size";
    final static String SAVED_WIN_SIZE = "saved_win_size";
    final static String SAVED_SYMBOL_X = "saved_symbol_x";
    EditText editText;
    NumberPicker np;

    Button ready, cancel;
    RadioButton radioButtonX, radioButtonO;


    View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int fieldSize = getResources().getInteger(R.integer.settings_default_size);
            if (v.getId() == R.id.settings_ready) {
                fieldSize = Integer.parseInt(editText.getText().toString());
                if (fieldSize >= getResources().getInteger(R.integer.settings_min_size) && fieldSize <= getResources().getInteger(R.integer.settings_max_size))
                    saveSettings();
                else {
                    String text=getString(R.string.settings_toast);
                    Toast toast = Toast.makeText(Settings.this, text, Toast.LENGTH_LONG);
                    toast.show();
                }

            }
            if (fieldSize >= getResources().getInteger(R.integer.settings_min_size) && fieldSize <= getResources().getInteger(R.integer.settings_max_size)) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }

        }
    };

    View.OnClickListener radioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.radioButtonX) radioButtonO.setChecked(false);
            else
                radioButtonX.setChecked(false);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsLayoutBuild();
        ready = (Button) findViewById(R.id.settings_ready);
        cancel = (Button) findViewById(R.id.settings_cancel);

        ready.setOnClickListener(settingsListener);
        cancel.setOnClickListener(settingsListener);

        radioButtonX = (RadioButton) findViewById(R.id.radioButtonX);
        radioButtonO = (RadioButton) findViewById(R.id.radioButtonO);

        radioButtonX.setOnClickListener(radioButtonListener);
        radioButtonO.setOnClickListener(radioButtonListener);

        loadSettings();

    }

    public void settingsLayoutBuild() {
        TextView textView = (TextView) findViewById(R.id.text_choose_symbol);
        textView.setTextSize(MainActivity.size.x * getFloatValueFromResourses(R.dimen.text_size));
        textView = (TextView) findViewById(R.id.text_field_size);
        textView.setTextSize(MainActivity.size.x * getFloatValueFromResourses(R.dimen.text_size));
        textView = (TextView) findViewById(R.id.text_line_length);
        textView.setTextSize(MainActivity.size.x * getFloatValueFromResourses(R.dimen.text_size));
        editText = (EditText) findViewById(R.id.field_size);
        editText.setTextSize(MainActivity.size.x * getFloatValueFromResourses(R.dimen.text_size));

        np = (NumberPicker) findViewById(R.id.win_size);
        np.setMaxValue(7);
        np.setMinValue(3);
    }

    public float getFloatValueFromResourses(int res) {
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return outValue.getFloat();
    }

    void saveSettings() {
        sPref = getSharedPreferences("mySettings.xml", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_FIELD_SIZE, editText.getText().toString());
        ed.commit();
        ed.putInt(SAVED_WIN_SIZE, np.getValue());
        ed.commit();
        ed.putBoolean(SAVED_SYMBOL_X, radioButtonX.isChecked());
        ed.commit();


    }

    void loadSettings() {
        sPref = getSharedPreferences("mySettings.xml", MODE_PRIVATE);
        editText.setText(sPref.getString(SAVED_FIELD_SIZE, "10"));
        np.setValue(sPref.getInt(SAVED_WIN_SIZE, 5));
        radioButtonX.setChecked(sPref.getBoolean(SAVED_SYMBOL_X, true));
        radioButtonO.setChecked(!sPref.getBoolean(SAVED_SYMBOL_X, true));

    }


}
