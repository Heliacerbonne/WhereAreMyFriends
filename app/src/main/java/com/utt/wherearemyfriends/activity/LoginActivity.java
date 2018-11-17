package com.utt.wherearemyfriends.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utt.wherearemyfriends.R;
import com.utt.wherearemyfriends.translation.LocaleHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private EditText loginEdt;
    private TextView title,subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //verif SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.file), Context.MODE_PRIVATE);

        //change activity if there is no known FirstName
        if (!sharedPref.getString("name", "").equals("")) {
            Intent k = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(k);
            return;
        }

        //find ui component
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginEdt = (EditText) findViewById(R.id.loginEdt);
        loginBtn.setOnClickListener(this);
        title = (TextView) findViewById(R.id.where);
        subtitle = (TextView) findViewById(R.id.loginTxt);

        //==================
        // TRANSLATION PART
        //==================

        Context context;
        Resources resources;

        if (LocaleHelper.getLanguage(LoginActivity.this).equalsIgnoreCase("en")) {
            context = LocaleHelper.setLocale(LoginActivity.this, "en");
            resources = context.getResources();
            title.setText(resources.getString(R.string.app_name));
            subtitle.setText(resources.getString(R.string.login));
            loginEdt.setHint(resources.getString(R.string.enter_name));
        } else if (LocaleHelper.getLanguage(LoginActivity.this).equalsIgnoreCase("sv")) {
            context = LocaleHelper.setLocale(LoginActivity.this, "sv");
            resources = context.getResources();
            title.setText(resources.getString(R.string.app_name));
            subtitle.setText(resources.getString(R.string.login));
            loginEdt.setHint(resources.getString(R.string.enter_name));
        }

    }

    @Override
    public void onClick(View v) {
        if (R.id.loginBtn == v.getId()) {
            loginEdt = (EditText) findViewById(R.id.loginEdt);

            //stock name in SharedPref
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.file), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name",loginEdt.getText().toString());
            editor.apply();

            //change activity
            Intent k = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(k);
        }
    }
}
