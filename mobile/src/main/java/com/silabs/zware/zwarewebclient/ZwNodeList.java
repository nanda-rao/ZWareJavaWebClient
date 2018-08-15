package com.silabs.zware.zwarewebclient;

/*<zwave><version platform="linux" app_major="1" app_minor="20" ctl_major="8" ctl_minor="12" /></zwave>*/

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class ZwNodeList extends ZwXMLHandler{

    class ZwNode {
        String desc;
        String sid;
        String property;
        String vid;
        String pid;
        String type;
        String category;
        String alive_state;
    }
    List<ZwNode> zwNodeList;
    List<ZwEPList> zwEPListList;

    int zwNodeListSize;

    public String getDesc(int iid) {
        return this.zwNodeList.get(iid).desc;
    }

    public void setDesc(int iid,String desc) {
        System.out.println("setDesc:"+iid+"desc="+desc);
        this.zwNodeList.get(iid).desc = desc;
    }

    public String getId(int iid) {
        return this.zwNodeList.get(iid).sid;
    }

    public void setId(int iid, String sid) {
        this.zwNodeList.get(iid).sid = sid;
    }

    public String getProperty(int iid) {
        return this.zwNodeList.get(iid).property;
    }

    public void setProperty(int iid,String property) {
        this.zwNodeList.get(iid).property = property;
    }

    public String getVid(int iid) {
        return this.zwNodeList.get(iid).vid;
    }

    public void setVid(int iid, String vid) {
        this.zwNodeList.get(iid).vid = vid;
    }

    public String getPid(int iid) {
        return this.zwNodeList.get(iid).pid;
    }

    public void setPid(int iid,String pid) {
        this.zwNodeList.get(iid).pid = pid;
    }

    public String getType(int iid) {
        return this.zwNodeList.get(iid).type;
    }

    public void setType(int iid,String type) {
        this.zwNodeList.get(iid).type = type;
    }

    public String getCategory(int iid) {
        return this.zwNodeList.get(iid).category;
    }

    public void setCategory(int iid,String category) {
        this.zwNodeList.get(iid).category = category;
    }

    public String getAlive_state(int iid) {
        return this.zwNodeList.get(iid).alive_state;
    }

    public void setAlive_state(int iid,String alive_state) {
        this.zwNodeList.get(iid).alive_state = alive_state;
    }

    public ZwNodeList(String xml){
        super(xml);
    }
    public static final Parcelable.Creator<ZwNodeList> CREATOR
            = new Parcelable.Creator<ZwNodeList>() {
        public ZwNodeList createFromParcel(Parcel in) {
            return new ZwNodeList(in.readString());
        }

        public ZwNodeList[] newArray(int size) {
            return new ZwNodeList[size];
        }
    };

    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        NodeList nList = doc.getElementsByTagName("zwnode");
        System.out.println("nList.getLength:"+nList.getLength());
        zwNodeListSize = nList.getLength();
        this.zwNodeList = new ArrayList<>();
        this.zwEPListList = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ZwNode zwNode = new ZwNode();
                zwNodeList.add(zwNode);
                setDesc(temp,eElement.getAttribute("desc"));
                setId(temp,eElement.getAttribute("id"));
                setProperty(temp,eElement.getAttribute("property"));
                setVid(temp,eElement.getAttribute("vid"));
                setPid(temp,eElement.getAttribute("pid"));
                setType(temp,eElement.getAttribute("type"));
                setCategory(temp,eElement.getAttribute("category"));
                setAlive_state(temp,eElement.getAttribute("alive_state"));
                /*For each node we query zwEPList using zw_ep_list()*/
                StringBuffer zwEPList = new StringBuffer("");
                System.out.println("Getting EPList for NodeId:"+getDesc(temp));
                ZwEPList ep_list = zw_web.getInstance().zw_ep_list(getDesc(temp),zwEPList);
                System.out.println("zwEPList for NodeId="+getDesc(temp) + ":" + zwEPList);
                zwEPListList.add(ep_list);
            }
        }
    }
}

