package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ZwDesc extends ZwXMLHandler{
    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    String home;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    String local;

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    String suc;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    String role;

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    String capability;

    public String getZw_role() {
        return zw_role;
    }

    public void setZw_role(String zw_role) {
        this.zw_role = zw_role;
    }

    String zw_role;

    public ZwDesc(String xml){
        super(xml);
    }

    public static final Parcelable.Creator<ZwDesc> CREATOR
            = new Parcelable.Creator<ZwDesc>() {
        public ZwDesc createFromParcel(Parcel in) {
            return new ZwDesc(in.readString());
        }

        public ZwDesc[] newArray(int size) {
            return new ZwDesc[size];
        }
    };

    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        NodeList nList = doc.getElementsByTagName("zwnet");
        System.out.println("ZwDesc:zwnet:size:"+nList.getLength());
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                setHome(eElement.getAttribute("home"));
                setLocal(eElement.getAttribute("local"));
                setSuc(eElement.getAttribute("suc"));
                setRole(eElement.getAttribute("role"));
                setCapability(eElement.getAttribute("capability"));
                setZw_role(eElement.getAttribute("zw_role"));
            }
        }
    }
}

