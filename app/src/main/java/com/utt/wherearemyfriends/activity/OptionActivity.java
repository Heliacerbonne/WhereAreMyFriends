package com.utt.wherearemyfriends.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.utt.wherearemyfriends.R;
import com.utt.wherearemyfriends.translation.LocaleHelper;

import org.w3c.dom.Text;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button optionOk;
    // Translation
    private Spinner mLanguage;
    private ArrayAdapter<String> mAdapter;
    private TextView chooseTxt, optionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //find ui component
        optionOk = (Button) findViewById(R.id.optionOk);
        optionOk.setOnClickListener(this);
        chooseTxt = (TextView) findViewById(R.id.chooseTxt);
        optionTxt = (TextView) findViewById(R.id.optionTxt) ;

        //==================
        // TRANSLATION PART
        //==================

        mLanguage = (Spinner) findViewById(R.id.spLanguage);
        mAdapter = new ArrayAdapter<String>(OptionActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        if (LocaleHelper.getLanguage(OptionActivity.this).equalsIgnoreCase("en")) {
            mLanguage.setSelection(mAdapter.getPosition("English"));
        } else if (LocaleHelper.getLanguage(OptionActivity.this).equalsIgnoreCase("sv")) {
            mLanguage.setSelection(mAdapter.getPosition("Svenska"));
        }

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 0:
                        context = LocaleHelper.setLocale(OptionActivity.this, "en");
                        resources = context.getResources();
                        chooseTxt.setText(resources.getString(R.string.select_language));
                        optionTxt.setText(resources.getString(R.string.settings));
                        break;
                    case 1:
                        context = LocaleHelper.setLocale(OptionActivity.this, "sv");
                        resources = context.getResources();
                        chooseTxt.setText(resources.getString(R.string.select_language));
                        optionTxt.setText(resources.getString(R.string.settings));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (R.id.optionOk == v.getId()) {
            //change activity
            Intent k = new Intent(OptionActivity.this, DashboardActivity.class);
            startActivity(k);
        }
    }
}
