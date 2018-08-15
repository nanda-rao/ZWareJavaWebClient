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

public class DeviceDiscoveryActivity extends Activity implements View.OnClickListener {
    private zw_web zwweb;
    private List<View> buttonList;
    private ZwApiConstants zwApiConstants;
    ZwNodeList zwNodeList;
    ZwVersion zwVersion;
    ZwDesc zwDesc;
    ZwEP zwEP;
    String zw_node_desc;//of the select node id
    String zw_ep_desc;//of the selected end point
    String zwCategory;//of the selected end point

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_discovery);

        zwApiConstants = ZwApiConstants.getInstance();
        buttonList = new ArrayList<>();
        for(int id=0;id<10;id++) {
            switch (id) {
                case 0:
                    buttonList.add(findViewById(R.id.ui_dev1_button));
                    break;
                case 1:
                    buttonList.add(findViewById(R.id.ui_dev2_button));
                    break;
                case 2:
                    buttonList.add(findViewById(R.id.ui_dev3_button));
                    break;
                case 3:
                    buttonList.add(findViewById(R.id.ui_dev4_button));
                    break;
                case 4:
                    buttonList.add(findViewById(R.id.ui_dev5_button));
                    break;
                case 5:
                    buttonList.add(findViewById(R.id.ui_dev6_button));
                    break;
                case 6:
                    buttonList.add(findViewById(R.id.ui_dev7_button));
                    break;
                case 7:
                    buttonList.add(findViewById(R.id.ui_dev8_button));
                    break;
                case 8:
                    buttonList.add(findViewById(R.id.ui_dev9_button));
                    break;
                case 9:
                    buttonList.add(findViewById(R.id.ui_dev10_button));
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

        //then it must be one of the device buttons...
        uiDevButton = findViewById(view.getId());

        //String button_value_string = new String(String.valueOf(uiDevButton.getText()));
        String button_value_string = new String(String.valueOf(uiDevButton.getContentDescription()));

        /*Quick hack: Extract ep_desc from the button's content description*/
        String[] ep_desc_start = button_value_string.split("ep_desc=",2);
        System.out.println("EP_DESC=" + ep_desc_start[1]);
        this.zw_ep_desc = ep_desc_start[1];
        /*Quick hack: Extract category code from the button's text*/
        String[] cat_start = button_value_string.split("cat=",2);
        System.out.println("CAT=" + cat_start[1]);
        this.zwCategory = cat_start[1];

        String[] iid_str_start = button_value_string.split("i=",2);
        System.out.println("iid=" + iid_str_start[1].substring(0,1));
        Integer iid = new Integer(iid_str_start[1].substring(0,1));

        String[] epid_str_start = button_value_string.split("e=",2);
        System.out.println("epid=" + epid_str_start[1].substring(0,1));
        Integer epid = new Integer(epid_str_start[1].substring(0,1));

        ZwEP zwEP = zwNodeList.zwEPListList.get(iid.intValue()).zwEpList.get(epid.intValue());
        this.zwEP = zwEP;

        zw_node_desc = zwNodeList.getDesc(iid.intValue());

        System.out.println("Selected zwEP:"+zwEP.name);
        System.out.println("Selected node desc:"+zw_node_desc);
        System.out.println("Selected ep desc:" + zwEP.desc);

        /*TO DO:Start Device Control Intent for the selected end point*/
        Intent intent = new Intent(DeviceDiscoveryActivity.this,DeviceControlActivity.class);
        intent.putExtra("ZwVersion", this.zwVersion);
        intent.putExtra("ZwDesc", this.zwDesc);
        intent.putExtra("ZwEP",this.zwEP);

        startActivity(intent);
    }

    private void refresh(){
        TextView uiZWVersion = findViewById(R.id.ui_zwversion);
        TextView uiZWDesc = findViewById(R.id.ui_zwdesc);
        TextView uiZWNodeList = findViewById(R.id.ui_zwnodelist);
        TextView uiZWEPList = findViewById(R.id.ui_zweplist);

        uiZWVersion.setText("Version\nVersion appears here");
        uiZWDesc.setText("Description\nDescription appears here");
        uiZWNodeList.setText("NodeList\nNode List appears here");
        uiZWEPList.setText("EPList\nEnd Point List appears here");

        StringBuffer zwVersion = new StringBuffer("");
        ZwVersion version = zware_version(zwVersion);
        if(version != null) {
            this.zwVersion = version;
            System.out.println("Version Success!");
            //uiZWVersion.setText(zwVersion);
            System.out.println("app_major="+version.getApp_major());
            System.out.println("app_minor="+version.getApp_minor());
            System.out.println("ctl_major="+version.getCtl_major());
            System.out.println("ctl_minor="+version.getCtl_minor());
            String verStr = "";
            verStr+=("app_major="+version.getApp_major());
            verStr+=("app_minor="+version.getApp_minor());
            verStr+=("ctl_major="+version.getCtl_major());
            verStr+=("ctl_minor="+version.getCtl_minor());
            uiZWVersion.setText(verStr);
        }
        else {
            System.out.println("Version Failed!");
        }

        StringBuffer zwDesc = new StringBuffer("");
        ZwDesc desc = zware_desc(zwDesc);
        System.out.println("zwDesc="+zwDesc);

        if(desc != null) {
            this.zwDesc = desc;
            System.out.println("Desc Success!");
            //uiZWDesc.setText(zwDesc);
            String descStr = "";
            descStr+=("homeid="+desc.getHome());
            descStr+=(" local_node_id="+desc.getLocal());
            descStr+=(" net_role="+desc.getRole());
            descStr+=(" \ncapability="+desc.getCapability());
            descStr+=(" net_role="+desc.getRole());
            descStr+=(" zw_role="+desc.getZw_role());
            uiZWDesc.setText(descStr);
            System.out.println("Desc:\n"+descStr);
        }
        else {
            System.out.println("Desc Failed!");
        }

        StringBuffer zwNodeList = new StringBuffer("");
        ZwNodeList node_list = zware_node_list(zwNodeList);
        this.zwNodeList = node_list;

        System.out.println("zwNodeList="+zwNodeList);

        if(node_list != null) {
            System.out.println("NodeList Success!");
            String zwNodeListTextString = "";
            //uiZWNodeList.setText(zwNodeList);
            int button_id = 0;
            for(int iid=0;iid<node_list.zwNodeListSize;iid++) {
                if(desc.getLocal().equals(node_list.getDesc(iid))) {//skip our local node
                    System.out.println("Skipping local node...");
                    System.out.println("desc=" + node_list.getDesc(iid) + "getLocal:" + desc.getLocal());
                    System.out.println("NodeId:" + node_list.getDesc(iid) + " EPListSize=" + node_list.zwEPListList.size());
                }
                else {
                    System.out.println("desc=" + node_list.getDesc(iid) + "getLocal:" + desc.getLocal());
                    System.out.println("NodeId:" + node_list.getDesc(iid) + " EPListSize=" + node_list.zwEPListList.size());
                    System.out.println("NodeId From EPList:" + node_list.zwEPListList.get(iid).node_id + "Size of EPList:" + node_list.zwEPListList.get(iid).zwEpList.size() +
                            ":" + node_list.zwEPListList.get(iid).zwEPListSize);
                    for(int epid=0;epid<node_list.zwEPListList.get(iid).zwEpList.size();epid++) {
                        Button devButton = (Button) buttonList.get(button_id);
                        devButton.setEnabled(true);
                        devButton.setVisibility(View.VISIBLE);
                        //devButton.setText(zwApiConstants.zwDevCategoryDescriptions.get(node_list.getCategory(iid))+":"+ node_list.zwEPListList.get(iid).getName(epid));//zwEpList.get(epid).name);
                        //TO DO:Need to handle localization correctly when calling setText
                        String devButtonText = "n=" + node_list.getDesc(iid) + ":i=" + iid +
                                ":e=" +       epid + ":" + zwApiConstants.zwDevCategoryDescriptions.get(node_list.getCategory(iid))+
                                ":cat=" +     node_list.getCategory(iid) +
                                ":" +         node_list.zwEPListList.get(iid).zwEpList.get(epid).name +
                                ":ep_desc=" + node_list.zwEPListList.get(iid).zwEpList.get(epid).desc;

                        devButton.setText(node_list.zwEPListList.get(iid).zwEpList.get(epid).name);
                        devButton.setContentDescription(devButtonText);
                        System.out.println("desc=" + node_list.getDesc(iid) + "getLocal:" + desc.getLocal());
                        zwNodeListTextString = zwNodeListTextString.concat(node_list.getDesc(iid));
                        zwNodeListTextString = zwNodeListTextString.concat("::");
                        button_id = (button_id+1) % 10;//TO DO: Fix me...For now we are just wrapping around out list of 10 buttons for a max of 10 devices...
                    }
                }
            }
            System.out.println("zwNodeListTextString=\n"+zwNodeListTextString+"\n");

            //zwNodeListTextString = "check me out";
            uiZWEPList.setText(zwNodeListTextString);

        }
        else {
            System.out.println("NodeList Failed!");
        }

        StringBuffer zwNodeEPList = new StringBuffer("");
        ZwNodeEPList node_ep_list = zware_node_ep_list(zwNodeEPList);
        System.out.println("zwNodeEPLIST="+zwNodeEPList);

        if(node_ep_list != null) {
            System.out.println("Node EP Success!");
            String zwNodeEPListTextString = "";
            //uiZWEPList.setText(zwNodeEPList);
            for(int iid=0;iid<node_ep_list.zwNodeEPListSize;iid++) {
                System.out.println("desc=" + node_list.getDesc(iid));
                zwNodeEPListTextString = zwNodeEPListTextString + node_list.getDesc(iid) + "\n";
            }
            uiZWNodeList.setText(zwNodeEPListTextString);


        }
        else {
            System.out.println("Node EP Failed!");
        }

        /*System.out.println("ZWVersion:"+uiZWVersion.getText());
        System.out.println("ZWDesc:"+uiZWDesc.getText());
        System.out.println("ZWNodeList:"+uiZWNodeList.getText());
        System.out.println("ZWEPList:"+uiZWEPList.getText());*/

    }

    private ZwVersion zware_version(StringBuffer zwVersion){
        return(zwweb.zw_version(zwVersion));
    }
    private ZwDesc zware_desc(StringBuffer zwDesc){
        return(zwweb.zw_desc(zwDesc));
    }
    private ZwNodeList zware_node_list(StringBuffer zwNodeList){
        return(zwweb.zw_node_list(zwNodeList));
    }
    private ZwNodeEPList zware_node_ep_list(StringBuffer zwNodeEPList){
        return(zwweb.zw_node_ep_list(zwNodeEPList));
    }
    /*private ZwEPList zware_ep_list(String noded,StringBuffer zwEPList){
        return(zwweb.zw_ep_list(noded,zwEPList));
    }*/
}

