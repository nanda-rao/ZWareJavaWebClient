package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ZwDOMParser {

    private Document _last_document;

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

    public void ZwDOMParser() {
    }

    public Document getDocument(){
        return _last_document;
    }

    public Document Parse(String xml) {
        try {
            DocumentBuilder Builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            //String is = "<zwave><version platform=\"linux\" app_major=\"1\" app_minor=\"20\" ctl_major=\"8\" ctl_minor=\"12\" /></zwave>";
            if(xml.isEmpty()) {
                System.out.println("Input xml is empty.");
                return null;
            }
            else {
                ZwInputStream iss = new ZwInputStream(xml);
                //saxParser.parse(iss, handler);

                Document doc = this._last_document = Builder.parse(iss);

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                return doc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

