package com.silabs.zware.zwarewebclient;

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;


public class ZwNodeEPList extends ZwXMLHandler{

    class ZwNodeEp {
        String desc;
        String node_id;
        String ep_id;
    }

    List<ZwNodeEp> zwNodeEpList;
    int zwNodeEPListSize;

    public String getDesc(int iid) {
        return this.zwNodeEpList.get(iid).desc;
    }

    public void setDesc(int iid,String desc) {
        this.zwNodeEpList.get(iid).desc = desc;
    }

    public String getNodeId(int iid) {
        return this.zwNodeEpList.get(iid).node_id;
    }

    public void setNodeId(int iid, String nodeid) {
        this.zwNodeEpList.get(iid).node_id = nodeid;
    }

    public String getEpId(int iid) {
        return this.zwNodeEpList.get(iid).ep_id;
    }

    public void setEpId(int iid, String epid) {
        this.zwNodeEpList.get(iid).ep_id = epid;
    }

    public ZwNodeEPList(String xml){
        super(xml);
    }
    public static final Parcelable.Creator<ZwNodeEPList> CREATOR
            = new Parcelable.Creator<ZwNodeEPList>() {
        public ZwNodeEPList createFromParcel(Parcel in) {
            return new ZwNodeEPList(in.readString());
        }

        public ZwNodeEPList[] newArray(int size) {
            return new ZwNodeEPList[size];
        }
    };
    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        NodeList nList = doc.getElementsByTagName("zwnode");
        System.out.println("nList.getLength:"+nList.getLength());
        zwNodeEPListSize = nList.getLength();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                setDesc(temp,eElement.getAttribute("desc"));
                setNodeId(temp,eElement.getAttribute("node_id"));
                setEpId(temp,eElement.getAttribute("ep_id"));
            }
        }
    }
}

