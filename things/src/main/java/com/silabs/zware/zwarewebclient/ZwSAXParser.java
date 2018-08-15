package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

import java.lang.reflect.*;


public class ZwSAXParser {
    class ZwInputStream extends InputStream {
        private char[] _buff;
        private int offset;
        private boolean eos;
        public ZwInputStream(String is){
            this._buff = is.toCharArray();
            offset = 0;
        }
        @Override
        public int read(){
            if(offset < _buff.length)
                return (int) this._buff[offset++];
            return -1;
        }
        @Override
        public void reset(){
            offset = 0;
        }
    }

    public void MySAXParser() {
    }

    public void Parse(String xml) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean zwave = false;
                boolean version = false;
                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("zwave")) {
                        System.out.println("\n\n zwave???");
                        System.out.println("uri:"+uri);
                        System.out.println("localName:"+localName);
                        System.out.println("qName:"+qName);
                        System.out.println("Attributes:"+attributes.getLength());
                        System.out.println("Attributes[0]:"+attributes.getLocalName(0));
                        System.out.println("Attributes[1]:"+attributes.getLocalName(1));
                        System.out.println("Attributes[2]:"+attributes.getLocalName(2));
                        System.out.println("Attributes[app_major]="+attributes.getValue("app_major"));
                        zwave = true;
                    }
                    if (qName.equalsIgnoreCase("version")) {
                        System.out.println("\n\n version???");
                        System.out.println("uri:"+uri);
                        System.out.println("localName:"+localName);
                        System.out.println("qName:"+qName);
                        System.out.println("Attributes:"+attributes.getLength());
                        System.out.println("Attributes[0]:"+attributes.getLocalName(0));
                        System.out.println("Attributes[1]:"+attributes.getLocalName(1));
                        System.out.println("Attributes[2]:"+attributes.getLocalName(2));
                        System.out.println("Attributes[app_major]="+attributes.getValue("app_major"));
                        version = true;
                    }
                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (zwave) {
                        System.out.println("\n\n zwave : " + new String(ch, start, length));
                        zwave = false;
                    }
                    if (version) {
                        System.out.println("\n\n version : " + new String(ch, start, length));
                        version = false;
                    }
                }//end of characters
            };//end of DefaultHandler

            //String is = "<zwave><version platform=\"linux\" app_major=\"1\" app_minor=\"20\" ctl_major=\"8\" ctl_minor=\"12\" /></zwave>";
            ZwInputStream iss = new ZwInputStream(xml);
            saxParser.parse(iss, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

