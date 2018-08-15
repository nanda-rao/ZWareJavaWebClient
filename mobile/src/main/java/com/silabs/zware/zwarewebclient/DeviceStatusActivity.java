package com.silabs.zware.zwarewebclient;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeviceStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private zw_web zwweb;
    private List<View> buttonList;
    private ZwApiConstants zwApiConstants;
    private String ifdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_status);

        ifdesc = "2425357";/*TO DO need to get ifdesc from the parent activity*/

        zwApiConstants = ZwApiConstants.getInstance();
        buttonList = new ArrayList<>();
        for(int id=0;id<8;id++) {
            switch (id) {
                case 0:
                    buttonList.add(findViewById(R.id.ui_refresh_button));
                    break;
                case 1:
                    buttonList.add(findViewById(R.id.ui_action2_button));
                    break;
                case 2:
                    buttonList.add(findViewById(R.id.ui_action3_button));
                    break;
                case 3:
                    buttonList.add(findViewById(R.id.ui_action4_button));
                    break;
                case 4:
                    buttonList.add(findViewById(R.id.ui_action5_button));
                    break;
                case 5:
                    buttonList.add(findViewById(R.id.ui_action6_button));
                    break;
                case 6:
                    buttonList.add(findViewById(R.id.ui_action7_button));
                    break;
                case 7:
                    buttonList.add(findViewById(R.id.ui_action8_button));
                    break;
            }
            Button uiButton = (Button) buttonList.get(id);
            uiButton.setOnClickListener(this);
            String button_value_string = new String(String.valueOf(uiButton.getText()));
            /*Disable the button if it contains "API" in it's text*/
            if(button_value_string.contains("action")){
                uiButton.setEnabled(false);
                uiButton.setVisibility(View.GONE);
            }
            else {
                uiButton.setEnabled(true);
                uiButton.setVisibility(View.VISIBLE);
            }

        }
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.ui_back_fab);
        myFab.setOnClickListener(this);

        zwweb = zw_web.getInstance();
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
        String action = button_value_string;

        /*Quick hack: Use API name from the button's text*/
        /* We currently support only BINARY_SWITCH*/
        if(action.equals("Refresh")){
            System.out.println("Action=" + action);
            /*TO DO:Invoke the API and display the results if any to the corresponding TextViews...*/
        }
        else {
            System.out.println("Action=" + action + ":NOT SUPPORTED!!");
        }
    }

    private void refresh(){

    }

    private ZwEPIFList zware_if_list(String epdesc,StringBuffer zwIFList){
        return(zwweb.zw_ep_if_list(epdesc,zwIFList));
    }
}

