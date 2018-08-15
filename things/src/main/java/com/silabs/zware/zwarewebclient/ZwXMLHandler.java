package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.*;

public abstract class ZwXMLHandler implements Parcelable{
    ZwSAXParser saxParser;//We will not use saxParser for now..may be later
    ZwDOMParser domParser;
    StringBuffer xml;
    String str_xml;

    public ZwXMLHandler(String xml) {
        //this.saxParser = new ZwSAXParser();
        //saxParser.Parse(xml);
        this.str_xml = xml;
        this.xml = new StringBuffer(xml);
        this.domParser = new ZwDOMParser();
        domParser.Parse(xml);
    }
    public ZwSAXParser getSAXParser(){
        return saxParser;
    }

    public Object getDOMParser() {
        return domParser.getDocument();
    }

    public abstract String serialize();
    public abstract void deserialize();
    // In constructor we will read the variables from Parcel. Make sure to read them in the same sequence in which we have written them in Parcel.
    public ZwXMLHandler(Parcel in) {
        String xml = in.readString();
        this.str_xml = xml;
        this.xml = new StringBuffer(xml);
        this.domParser = new ZwDOMParser();
        domParser.Parse(xml);
    }

    // This is where we will write our member variables in Parcel.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(str_xml);
    }
    @Override
    public int describeContents(){
        return 0;
    }
}

