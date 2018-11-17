package com.utt.wherearemyfriends.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.utt.wherearemyfriends.Group;
import com.utt.wherearemyfriends.MainController;
import com.utt.wherearemyfriends.network.Message;
import com.utt.wherearemyfriends.R;
import com.utt.wherearemyfriends.translation.LocaleHelper;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button option, manage;
    private TextView hello;
    private String name;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 1;
    private MainController ctrl;
    private MapFragment map;

    private Observer<Group> updateGreeting = (Group group) -> {
        String language = LocaleHelper.getLanguage(this);
        String lang = language.equalsIgnoreCase("sv") ? "sv" : "en";

        Resources r = LocaleHelper
                .setLocale(this, lang)
                .getResources();

        manage.setText(r.getString(R.string.manage_group));
        option.setText(r.getString(R.string.settings));
        if (group != null) {
            hello.setText(r.getString(R.string.greet_group, name, group.getName()));
        } else {
            hello.setText(r.getString(R.string.greet_nogroup, name));
        }
    };

    private void getPermission(String name, int req) {
        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, name)) {
            // The permission is not already granted
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, name)) {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{name}, req);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FragmentManager fm = getSupportFragmentManager();
        ctrl = (MainController) getApplication();

        // Find UI components
        hello = (TextView) findViewById(R.id.hello);
        manage = (Button) findViewById(R.id.groupBtn);
        manage.setOnClickListener(this);
        option = (Button) findViewById(R.id.optionOk);
        option.setOnClickListener(this);
        map = (MapFragment) fm.findFragmentById(R.id.map);

        //personalize hello message
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.file), Context.MODE_PRIVATE);
        name = sharedPref.getString("name", "default value");
        ctrl.setUsername(name);
        ctrl.getCurrentGroup().observe(this, updateGreeting);

        // Start locating the user
        getPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_ACCESS_FINE_LOCATION);
        getPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_ACCESS_COARSE_LOCATION);
        map.startLocating();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (R.id.groupBtn == v.getId()) {
            //change activity
            ctrl.send(Message.groups());
            Intent k = new Intent(DashboardActivity.this, GroupActivity.class);
            startActivity(k);
        }
        else if (R.id.optionOk == v.getId()) {
            //change activity
            Intent k = new Intent(DashboardActivity.this, OptionActivity.class);
            startActivity(k);
        }
    }
}
