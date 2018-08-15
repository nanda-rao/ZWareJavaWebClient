package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.*;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ZwVersion extends ZwXMLHandler{
    String platform;
    String app_major;
    String app_minor;
    String ctl_major;
    String ctl_minor;

    public ZwVersion(String xml){
        super(xml);
    }
    public static final Parcelable.Creator<ZwVersion> CREATOR
            = new Parcelable.Creator<ZwVersion>() {
        public ZwVersion createFromParcel(Parcel in) {
            return new ZwVersion(in.readString());
        }

        public ZwVersion[] newArray(int size) {
            return new ZwVersion[size];
        }
    };

    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        NodeList nList = doc.getElementsByTagName("version");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                setPlatform(eElement.getAttribute("platform"));
                setApp_major(eElement.getAttribute("app_major"));
                setApp_minor(eElement.getAttribute("app_minor"));
                setCtl_major(eElement.getAttribute("ctl_major"));
                setCtl_minor(eElement.getAttribute("ctl_minor"));
            }
        }
    }
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String value) {
        this.platform = value;
    }

    public String getApp_major() {
        return app_major;
    }

    public void setApp_major(String value) {
        this.app_major = value;
    }

    public String getApp_minor() {
        return app_minor;
    }

    public void setApp_minor(String value) {
        this.app_minor = value;
    }

    public String getCtl_major() {
        return ctl_major;
    }

    public void setCtl_major(String value) {
        this.ctl_major = value;
    }

    public String getCtl_minor() {
        return ctl_minor;
    }

    public void setCtl_minor(String value) {
        this.ctl_minor = value;
    }
}

