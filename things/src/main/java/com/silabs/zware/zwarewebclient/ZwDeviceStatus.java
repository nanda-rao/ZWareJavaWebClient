
package com.silabs.zware.zwarewebclient;

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class ZwDeviceStatus extends ZwXMLHandler {
    String utime;
    String state;
    String target_state;

    String duration;
    String error;
    String if_desc;
    String zwintfcmd;

    public String getTarget_state() {
        return target_state;
    }

    public void setTarget_state(String target_state) {
        this.target_state = target_state;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getIf_desc() {
        return if_desc;
    }

    public String getZwintfcmd() {
        return zwintfcmd;
    }

    public String getUtime() {
        return this.utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public ZwDeviceStatus(String xml){
        super(xml);
        this.error = ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR");
    }
    public ZwDeviceStatus(String if_desc,String zwintfcmd, String xml){
        super(xml);
        this.if_desc = if_desc;
        this.zwintfcmd = zwintfcmd;
        this.error = ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR");
    }
    public static final Parcelable.Creator<ZwDeviceStatus> CREATOR
            = new Parcelable.Creator<ZwDeviceStatus>() {
        public ZwDeviceStatus createFromParcel(Parcel in) {
            return new ZwDeviceStatus(in.readString());
            /*To Do: check if if_desc and zwintfcmd may be needed here...should be OK for now as we do not expect ZwDeviceStatus to be serialized..*/
        }

        public ZwDeviceStatus[] newArray(int size) {
            return new ZwDeviceStatus[size];
        }
    };
    public void dump() {
        System.out.println("ifdesc:"+this.getIf_desc());
        System.out.println("utime:"+this.getUtime());
        System.out.println("state:"+this.getState());
        System.out.println("error:"+ZwApiConstants.getInstance().zwErrorDescriptions.get(this.getError()));
    }

    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        //Element rNode = doc.getElementById("zwnode");
        //setNodeId(rNode.getAttribute("node_id"));

        NodeList nList = doc.getElementsByTagName("zwif");
        System.out.println("nList.getLength:"+nList.getLength());
        //We expect  one zwif Tag on success...
        if(nList.getLength() == 1) {
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (getIf_desc().equals(eElement.getAttribute("desc"))) {
                        nList = doc.getElementsByTagName("switch");
                        //We expect only one switch Tag...
                        for (temp = 0; temp < nList.getLength(); temp++) {
                            nNode = nList.item(temp);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                eElement = (Element) nNode;
                                //handle the eElement's attributes
                                setState(eElement.getAttribute("state"));
                                setUtime(eElement.getAttribute("utime"));
                                setTarget_state(eElement.getAttribute("target_state"));
                                setDuration(eElement.getAttribute("duration"));
                                this.error = ZwApiConstants.getInstance().zwErrorCodes.get("ZW_NO_ERROR");
                            }
                        }
                    }
                }
            }
        }
        else {
            if(zwintfcmd.equals(ZwApiConstants.getInstance().zwInterfaceCommands.get("CMD_BSWITCH_SETUP"))) {
                this.error = ZwApiConstants.getInstance().zwErrorCodes.get("ZW_DEFAULT_ERROR");
            }
        }
    }
}


