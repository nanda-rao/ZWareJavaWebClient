package com.silabs.zware.zwarewebclient;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.silabs.zware.zwarewebclient.zw_web;

import java.util.ArrayList;
import java.util.List;

public class BinarySwitchInterfaceActivity extends Activity implements View.OnClickListener {

    private zw_web zwweb;
    private List<View> buttonList;
    private ZwApiConstants zwApiConstants;
    private String ifdesc;
    private ZwVersion zwVersion;
    private ZwDesc zwDesc;
    private ZwEP zwEP;
    private ZwEPIf zwIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_switch_interface);

        this.zwVersion = (ZwVersion) getIntent().getParcelableExtra("ZwVersion");
        this.zwVersion.deserialize();
        this.zwDesc = (ZwDesc) getIntent().getParcelableExtra("ZwDesc");
        this.zwDesc.deserialize();
        this.zwEP = getIntent().getParcelableExtra("ZwEP");
        this.zwIF = getIntent().getParcelableExtra("ZwIF");

        System.out.println("home=" + zwDesc.getHome());
        System.out.println("app_major=" + zwVersion.getApp_major());
        System.out.println("epdesc_from_parent=" + zwEP.desc);
        System.out.println("ifdesc from parent=" + zwIF.desc);
        ifdesc = zwIF.desc;

        zwApiConstants = ZwApiConstants.getInstance();
        buttonList = new ArrayList<>();
        for (int id = 0; id < 10; id++) {
            switch (id) {
                case 0:
                    buttonList.add(findViewById(R.id.ui_api1_button));
                    break;
                case 1:
                    buttonList.add(findViewById(R.id.ui_api2_button));
                    break;
                case 2:
                    buttonList.add(findViewById(R.id.ui_api3_button));
                    break;
                case 3:
                    buttonList.add(findViewById(R.id.ui_api4_button));
                    break;
                case 4:
                    buttonList.add(findViewById(R.id.ui_api5_button));
                    break;
                case 5:
                    buttonList.add(findViewById(R.id.ui_api6_button));
                    break;
                case 6:
                    buttonList.add(findViewById(R.id.ui_api7_button));
                    break;
                case 7:
                    buttonList.add(findViewById(R.id.ui_api8_button));
                    break;
                case 8:
                    buttonList.add(findViewById(R.id.ui_api9_button));
                    break;
                case 9:
                    buttonList.add(findViewById(R.id.ui_api10_button));
                    break;
            }
            Button uiButton = (Button) buttonList.get(id);
            uiButton.setOnClickListener(this);
            String button_value_string = new String(String.valueOf(uiButton.getText()));
            /*Disable the button if it contains "API" in it's text*/
            if (button_value_string.contains("API")) {
                uiButton.setEnabled(false);
                uiButton.setVisibility(View.GONE);
            } else {
                uiButton.setEnabled(true);
                uiButton.setVisibility(View.VISIBLE);
            }

        }
        Button myFab = (Button) findViewById(R.id.ui_back_fab);
        myFab.setOnClickListener(this);
        zwweb = zw_web.getInstance();
        if (zwweb != null) refresh();
    }

    public void onResume(){
        super.onResume();
        if (zwweb != null) refresh();
    }

    public void onClick(View view) {
        Button uiDevButton;
        System.out.println("Click received for id:" + view.getId());
        if(view.getId() == R.id.ui_back_fab) {
            System.out.println("Back FAB click received...");
            this.finish();
            return;
        }
        //then it must be a button other than FloatingAction Back button
        uiDevButton = findViewById(view.getId());
        String button_value_string = new String(String.valueOf(uiDevButton.getText()));
        String api = button_value_string;

        StringBuffer devicestatus_xml = new StringBuffer("");
        ZwDeviceStatus deviceStatus = zwweb.zwif_switch_setup(ifdesc, devicestatus_xml);
        deviceStatus.dump();

        deviceStatus = zwweb.zwif_switch_get(ifdesc, devicestatus_xml);
        deviceStatus.dump();

        /*Quick hack: Use API name from the button's text*/
        /* We currently support only BINARY_SWITCH*/
        if (api.equals("Refresh")) {
            System.out.println("API=" + api);
            /*TO DO:Invoke the API and display the results if any...*/
            TextView ui_device_status = findViewById(R.id.ui_device_status);
            if (deviceStatus.getState().equals("0")) {
                ui_device_status.setText("OFF");
                ui_device_status.setTextColor(Color.RED);
            } else {
                ui_device_status.setText("ON");
                ui_device_status.setTextColor(Color.GREEN);
            }
        } else if (api.equals("ON")) {
            System.out.println("API=" + api);
            /*TO DO:Invoke the API and display the results if any...*/

            String next_state = "255";
            devicestatus_xml = new StringBuffer("");
            deviceStatus = zwweb.zwif_switch_set(ifdesc, next_state, devicestatus_xml);
            deviceStatus.dump();

            Intent intent = new Intent(BinarySwitchInterfaceActivity.this, BinarySwitchControlActivity.class);

            intent.putExtra("ZwVersion", this.zwVersion);
            intent.putExtra("ZwDesc", this.zwDesc);
            intent.putExtra("ZwEP", this.zwEP);
            intent.putExtra("ZwIF", this.zwIF);

            startActivity(intent);
        } else if (api.equals("OFF")) {
            System.out.println("API=" + api);
            /*TO DO:Invoke the API and display the results if any...*/

            String next_state = "0";
            devicestatus_xml = new StringBuffer("");
            deviceStatus = zwweb.zwif_switch_set(ifdesc, next_state, devicestatus_xml);
            deviceStatus.dump();

            Intent intent = new Intent(BinarySwitchInterfaceActivity.this, BinarySwitchControlActivity.class);
            intent.putExtra("ZwVersion", this.zwVersion);
            intent.putExtra("ZwDesc", this.zwDesc);
            intent.putExtra("ZwEP", this.zwEP);
            intent.putExtra("ZwIF", this.zwIF);
            startActivity(intent);
        } else if (api.equals("SET")) {
            System.out.println("API=" + api);
            /*TO DO:Invoke the API and display the results if any...*/
            /*Can be used to set level in case of multi-level switch...*/
            String next_state = "255";
            devicestatus_xml = new StringBuffer("");
            deviceStatus = zwweb.zwif_switch_set(ifdesc, next_state, devicestatus_xml);
            deviceStatus.dump();

            Intent intent = new Intent(BinarySwitchInterfaceActivity.this, BinarySwitchControlActivity.class);
            intent.putExtra("ZwVersion", this.zwVersion);
            intent.putExtra("ZwDesc", this.zwDesc);
            intent.putExtra("ZwEP", this.zwEP);
            intent.putExtra("ZwIF", this.zwIF);

            startActivity(intent);
        } else {
            System.out.println("API=" + api + ":NOT SUPPORTED!!");
        }
    }

    private void refresh() {
        /*Invoke the setup and get APIs and display the results if any...*/

        StringBuffer devicestatus_xml = new StringBuffer("");
        ZwDeviceStatus deviceStatus = zwweb.zwif_switch_setup(ifdesc, devicestatus_xml);
        deviceStatus.dump();

        deviceStatus = zwweb.zwif_switch_get(ifdesc, devicestatus_xml);
        deviceStatus.dump();

        TextView ui_device_status = findViewById(R.id.ui_device_status);
        if (deviceStatus.getState().equals("0")) {
            ui_device_status.setText("OFF");
            ui_device_status.setTextColor(Color.RED);
        } else {
            ui_device_status.setText("ON");
            ui_device_status.setTextColor(Color.GREEN);
        }
    }

}

