package com.silabs.zware.zwarewebclient;

import android.content.Intent;
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

public class DeviceControlActivity extends Activity implements View.OnClickListener {
    private zw_web zwweb;
    private List<View> buttonList;
    private ZwApiConstants zwApiConstants;
    private String epdesc;
    ZwEPIFList zwEPIFList;
    ZwEPIf zwIF;

    private ZwVersion zwVersion;//of selected device
    private ZwEP zwEP;//of selected end point
    private ZwDesc zwDesc;//of selected device

    private String zw_if_desc;//of selected interface
    private String zw_if_name;//if selected interface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);

        //epdesc = "525";/*TO DO need to get epdesc from the parent activity*/

        ZwVersion zwVersion = (ZwVersion) getIntent().getParcelableExtra("ZwVersion");
        zwVersion.deserialize();
        ZwDesc zwDesc = (ZwDesc) getIntent().getParcelableExtra("ZwDesc");
        zwDesc.deserialize();
        ZwEP zwEP = getIntent().getParcelableExtra("ZwEP");

        System.out.println("home="+zwDesc.getHome());

        System.out.println("app_major="+zwVersion.getApp_major());

        System.out.println("epdesc_from_parent="+zwEP.desc);

        epdesc = zwEP.desc;

        this.zwVersion = zwVersion;
        this.zwEP = zwEP;
        this.zwDesc = zwDesc;

        zwApiConstants = ZwApiConstants.getInstance();
        buttonList = new ArrayList<>();
        for(int id=0;id<10;id++) {
            switch (id) {
                case 0:
                    buttonList.add(findViewById(R.id.ui_if1_button));
                    break;
                case 1:
                    buttonList.add(findViewById(R.id.ui_if2_button));
                    break;
                case 2:
                    buttonList.add(findViewById(R.id.ui_if3_button));
                    break;
                case 3:
                    buttonList.add(findViewById(R.id.ui_if4_button));
                    break;
                case 4:
                    buttonList.add(findViewById(R.id.ui_if5_button));
                    break;
                case 5:
                    buttonList.add(findViewById(R.id.ui_if6_button));
                    break;
                case 6:
                    buttonList.add(findViewById(R.id.ui_if7_button));
                    break;
                case 7:
                    buttonList.add(findViewById(R.id.ui_if8_button));
                    break;
                case 8:
                    buttonList.add(findViewById(R.id.ui_if9_button));
                    break;
                case 9:
                    buttonList.add(findViewById(R.id.ui_if10_button));
                    break;
            }
            buttonList.get(id).setEnabled(false);
            buttonList.get(id).setVisibility(View.GONE);
            buttonList.get(id).setOnClickListener(this);
        }
        Button myFab = (Button) findViewById(R.id.ui_back_fab);
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
        String button_value_string = new String(String.valueOf(uiDevButton.getContentDescription()));

        /*Quick hack: Extract if_desc from the button's content description*/
        String[] if_desc_start = button_value_string.split("desc=",2);
        System.out.println("IF_DESC=" + if_desc_start[1]);
        String[] if_name_start = button_value_string.split("name=",2);
        System.out.println("IF_NAME=" + if_name_start[1]);

        this.zw_if_desc = if_desc_start[1];
        this.zw_if_name = if_name_start[1];

        String[] iid_str_start = button_value_string.split("i=",2);
        System.out.println("iid=" + iid_str_start[1].substring(0,1));
        Integer iid = new Integer(iid_str_start[1].substring(0,1));

        this.zwIF = this.zwEPIFList.zwEpIfList.get(iid.intValue());

        /*TO DO:Start Interface Control Intent for the selected Interface(CC)*/
        /* We currently support only BINARY_SWITCH*/
        if(if_name_start[1].equals("SWITCH_BINARY")){
            /*TO DO:Start Interface Control Intent for the selected end point*/
            Intent intent = new Intent(DeviceControlActivity.this,BinarySwitchInterfaceActivity.class);

            intent.putExtra("ZwVersion", this.zwVersion);
            intent.putExtra("ZwDesc", this.zwDesc);
            intent.putExtra("ZwEP",this.zwEP);
            intent.putExtra("ZwIF",this.zwIF);

            startActivity(intent);
        }
        else {
            System.out.println("Command Class=" + if_name_start[1] + ":NOT SUPPORTED!!");
        }
    }

    private void refresh(){

        StringBuffer zwIFList = new StringBuffer("");
        ZwEPIFList if_list = zware_if_list(epdesc,zwIFList);
        System.out.println("zwIFList="+zwIFList);
        this.zwEPIFList = if_list;

        if(if_list != null) {
            System.out.println("IFList Success! zwEPIfListSize="+if_list.zwEpIfListSize);
            String zwIFListTextString = "";
            //uiZWNodeList.setText(zwNodeList);
            int button_id = 0;
            for(int iid=0;iid<if_list.zwEpIfListSize;iid++) {
                    System.out.println("desc=" + if_list.getDesc(iid));
                    System.out.println("epdesc:" + if_list.getDesc(iid) + " IFEPListSize=" + if_list.zwEpIfList.size());
                    {
                        Button devButton = (Button) buttonList.get(button_id);
                        devButton.setEnabled(true);
                        devButton.setVisibility(View.VISIBLE);
                        //devButton.setText(zwApiConstants.zwDevCategoryDescriptions.get(node_list.getCategory(iid))+":"+ node_list.zwEPListList.get(iid).getName(epid));//zwEpList.get(epid).name);
                        //TO DO:Need to handle localization correctly when calling setText
                        /*Quick hack:setting ep_desc as the button text*/
                        String button_desc_text = "i="+ iid + ":desc="+if_list.getDesc(iid)+":name="+if_list.getName(iid);
                        devButton.setText(if_list.getName(iid));
                        devButton.setContentDescription(button_desc_text);
                        System.out.println("desc=" + if_list.getDesc(iid));
                        zwIFListTextString = zwIFListTextString.concat(if_list.getDesc(iid));
                        zwIFListTextString = zwIFListTextString.concat("::");
                        button_id = (button_id+1) % 10;//TO DO: Fix me...For now we are just wrapping around out list of 10 buttons for a max of 10 devices...
                    }
            }
            System.out.println("zwIFListTextString=\n"+zwIFListTextString+"\n");

            //zwNodeListTextString = "check me out";
            //uiZWEPIFList.setText(zwIFListTextString);

        }
        else {
            System.out.println("NodeList Failed!");
        }
    }

    private ZwEPIFList zware_if_list(String epdesc,StringBuffer zwIFList){
        return(zwweb.zw_ep_if_list(epdesc,zwIFList));
    }
}

