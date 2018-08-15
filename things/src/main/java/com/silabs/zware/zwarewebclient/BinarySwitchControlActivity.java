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

public class BinarySwitchControlActivity extends Activity implements View.OnClickListener {

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
        setContentView(R.layout.activity_binary_switch_control);

        this.zwVersion = (ZwVersion) getIntent().getParcelableExtra("ZwVersion");
        this.zwVersion.deserialize();
        this.zwDesc = (ZwDesc) getIntent().getParcelableExtra("ZwDesc");
        this.zwDesc.deserialize();
        this.zwEP = getIntent().getParcelableExtra("ZwEP");
        this.zwIF = getIntent().getParcelableExtra("ZwIF");

        System.out.println("home="+zwDesc.getHome());
        System.out.println("app_major="+zwVersion.getApp_major());
        System.out.println("epdesc_from_parent="+zwEP.desc);
        System.out.println("ifdesc from parent="+zwIF.desc);
        ifdesc = zwIF.desc;

        zwApiConstants = ZwApiConstants.getInstance();
        buttonList = new ArrayList<>();
        for(int id=0;id<8;id++) {
            switch (id) {
                case 0:
                    buttonList.add(findViewById(R.id.ui_on_button));
                    break;
                case 1:
                    buttonList.add(findViewById(R.id.ui_off_button));
                    break;
                case 2:
                    buttonList.add(findViewById(R.id.ui_cmd3_button));
                    break;
                case 3:
                    buttonList.add(findViewById(R.id.ui_cmd4_button));
                    break;
                case 4:
                    buttonList.add(findViewById(R.id.ui_cmd5_button));
                    break;
                case 5:
                    buttonList.add(findViewById(R.id.ui_cmd6_button));
                    break;
                case 6:
                    buttonList.add(findViewById(R.id.ui_cmd7_button));
                    break;
                case 7:
                    buttonList.add(findViewById(R.id.ui_cmd8_button));
                    break;
            }
            Button uiButton = (Button) buttonList.get(id);
            uiButton.setOnClickListener(this);
            String button_value_string = new String(String.valueOf(uiButton.getText()));
            /*Disable the button if it contains "API" in it's text*/
            if(button_value_string.contains("CMD")){
                uiButton.setEnabled(false);
                uiButton.setVisibility(View.GONE);
            }
            else {
                uiButton.setEnabled(true);
                uiButton.setVisibility(View.VISIBLE);
            }

        }
        Button myFab = (Button) findViewById(R.id.ui_back_fab);
        myFab.setOnClickListener(this);
        zwweb = zw_web.getInstance();
        if(zwweb != null) refresh();
    }

    public void onStart(){
        super.onStart();
        if(zwweb != null) refresh();
    }

    public void onResume(){
        super.onResume();
        if(zwweb != null) refresh();
    }

    public void onClick(View view){
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

        if (api.equals("Refresh")) {
            if(zwweb != null) refresh();
        }
    }

    private void refresh() {
        /*Invoke the setup and get APIs and display the results if any...*/


        try {
            //while (true) {
                System.out.println("Getting status..");
                StringBuffer devicestatus_xml = new StringBuffer("");
                ZwDeviceStatus deviceStatus = zwweb.zwif_switch_setup(ifdesc, devicestatus_xml);
                deviceStatus.dump();

                /*For now just keep getting the device state and update results...*/
                deviceStatus = zwweb.zwif_switch_get(ifdesc, devicestatus_xml);
                TextView ui_device_status = findViewById(R.id.ui_device_status);
                System.out.println("Device status:"+deviceStatus.getState());

                if (deviceStatus.getState().equals("0")) {
                    ui_device_status.setText("OFF");
                    ui_device_status.setTextColor(Color.RED);
                } else {
                    ui_device_status.setText("ON");
                    ui_device_status.setTextColor(Color.GREEN);
                }
            //}
        }
        catch (Exception e) {
            System.out.println("BinarySwitchControlActivity:Caught exception:");
            e.printStackTrace();
        }

    }

    private ZwEPIFList zware_if_list(String epdesc,StringBuffer zwIFList){
        return(zwweb.zw_ep_if_list(epdesc,zwIFList));
    }
}

