package com.utt.wherearemyfriends.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utt.wherearemyfriends.MainController;
import com.utt.wherearemyfriends.network.Message;
import com.utt.wherearemyfriends.R;
import com.utt.wherearemyfriends.translation.LocaleHelper;

public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private Button create;
    private EditText gname;
    private MainController ctrl;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ctrl = (MainController) getApplication();

        //find ui component
        create = (Button) findViewById(R.id.createOk);
        create.setOnClickListener(this);
        gname = (EditText) findViewById(R.id.gname);
        title = (TextView) findViewById(R.id.createTitle);

        //==================
        // TRANSLATION PART
        //==================

        Context context;
        Resources resources;

        if (LocaleHelper.getLanguage(CreateGroupActivity.this).equalsIgnoreCase("en")) {
            context = LocaleHelper.setLocale(CreateGroupActivity.this, "en");
            resources = context.getResources();
            title.setText(resources.getString(R.string.create_group));
            gname.setHint(resources.getString(R.string.enter_gname));

        } else if (LocaleHelper.getLanguage(CreateGroupActivity.this).equalsIgnoreCase("sv")) {
            context = LocaleHelper.setLocale(CreateGroupActivity.this, "sv");
            resources = context.getResources();
            title.setText(resources.getString(R.string.create_group));
            gname.setHint(resources.getString(R.string.enter_gname));

        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.createOk == v.getId()) {
            String group = gname.getText().toString();
            String user = ctrl.getUser().getValue().getName();
            ctrl.send(Message.register(group, user));

            //change activity
            Intent k = new Intent(CreateGroupActivity.this, DashboardActivity.class);
            startActivity(k);
        }
    }
}
