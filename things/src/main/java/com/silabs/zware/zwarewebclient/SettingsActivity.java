package com.silabs.zware.zwarewebclient;

import android.support.design.widget.FloatingActionButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        System.out.println("Settings Activity On Create");
        EditText uiDefaultURLView = findViewById(R.id.ui_zware_default_url);
        uiDefaultURLView.setText(ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.get("ZW_DEFAULT_URL"));
        CheckBox uiSimulationModeView = findViewById(R.id.ui_simulation_mode);
        boolean simulation_mode = false;
        if(ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.get("ZW_OFFLINE_SIMULATION_MODE").equals("1")){
            simulation_mode = true;
        }
        uiSimulationModeView.setChecked(simulation_mode);
        Button myFab = (Button) findViewById(R.id.ui_back_fab);
        myFab.setOnClickListener(this);
    }

    public void onClick(View view) {
        System.out.println("Click received for id:" + view.getId());
        if (view.getId() == R.id.ui_back_fab) {
            System.out.println("Back FAB click received...");
            this.finish();
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CheckBox uiSimulationModeView = findViewById(R.id.ui_simulation_mode);

        if(uiSimulationModeView.isChecked()) {
            ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.put("ZW_OFFLINE_SIMULATION_MODE", "1");
        }
        else {
            ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.put("ZW_OFFLINE_SIMULATION_MODE", "0");
        }
        EditText uiDefaultURLView = findViewById(R.id.ui_zware_default_url);
        ZwGlobalConfig.getInstance(this.getBaseContext()).zwGlobalConfig.put("ZW_DEFAULT_URL",String.valueOf(uiDefaultURLView.getText()));
        System.out.println("def url:"+String.valueOf(uiDefaultURLView.getText()));
        ZwGlobalConfig.getInstance(this.getBaseContext()).dump();
        ZwGlobalConfig.getInstance(this.getBaseContext()).update();
    }
}
