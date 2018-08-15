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


public class ZwEPList extends ZwXMLHandler{

    /*class ZwEP_int implements Parcelable{
        String desc;
        String ep_id;
        String generic;
        String specific;
        String name;
        String location;
        public  ZwEP(){

        }
        public ZwEP(Parcel in){
            this.desc = in.readString();
            this.ep_id = in.readString();
            this.generic = in.readString();
            this.specific = in.readString();
            this.name = in.readString();
            this.location = in.readString();
        }
        public final Parcelable.Creator<ZwEP> CREATOR
                = new Parcelable.Creator<ZwEP>() {
            public ZwEP createFromParcel(Parcel in) {
                return new ZwEP(in);
            }

            public ZwEP[] newArray(int size) {
                return new ZwEP[size];
            }
        };

        // This is where we will write our member variables in Parcel.
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.desc);
            dest.writeString(this.ep_id);
            dest.writeString(this.generic);
            dest.writeString(this.specific);
            dest.writeString(this.name);
            dest.writeString(this.location);
        }
        @Override
        public int describeContents(){
            return 0;
        }
    }
    */
    String node_id;
    List<ZwEP> zwEpList;
    int zwEPListSize;

    public String getDesc(int iid) {
        return this.zwEpList.get(iid).desc;
    }

    public void setDesc(int iid,String desc) {
        this.zwEpList.get(iid).desc = desc;
    }

    public String getNodeId() {
        return this.node_id;
    }

    public void setNodeId(String nodeid) {
        this.node_id = nodeid;
    }

    public String getEpId(int iid) {
        return this.zwEpList.get(iid).ep_id;
    }

    public void setEpId(int iid, String epid) {
        this.zwEpList.get(iid).ep_id = epid;
    }

    public String getGeneric(int iid){
        return this.zwEpList.get(iid).generic;
    }
    public void setGeneric(int iid,String generic){
        this.zwEpList.get(iid).generic = generic;
    }

    public String getSpecific(int iid){
        return this.zwEpList.get(iid).specific;
    }

    public void setSpecific(int iid,String specific){
        this.zwEpList.get(iid).specific = specific;
    }
    public String getName(int iid){
        return this.zwEpList.get(iid).name;
    }

    public void setName(int iid,String name){
        this.zwEpList.get(iid).name = name;
    }
    public String getLocation(int iid){
        return this.zwEpList.get(iid).location;
    }

    public void setLocation(int iid,String location){
        this.zwEpList.get(iid).location = location;
    }

    public ZwEPList(String noded,String xml){
        super(xml);
        this.node_id = noded;
    }
    public ZwEPList(String xml){
        super(xml);
    }

    public static final Parcelable.Creator<ZwEPList> CREATOR
            = new Parcelable.Creator<ZwEPList>() {
        public ZwEPList createFromParcel(Parcel in) {
            return new ZwEPList(in.readString());
            //TO DO: Check if noded may be required here..should be OK for now as we do not expect to serialize ZwEPList
        }

        public ZwEPList[] newArray(int size) {
            return new ZwEPList[size];
        }
    };
    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        //Element rNode = doc.getElementById("zwnode");
        //setNodeId(rNode.getAttribute("node_id"));

        NodeList nList = doc.getElementsByTagName("zwep");
        System.out.println("nList.getLength:"+nList.getLength());
        zwEPListSize = nList.getLength();
        this.zwEpList = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("ZwEPList::deserialize:nodeid="+getNodeId()+":epid="+temp+":"+nNode.getNodeType()+":ELEMENT_NODE:"+Node.ELEMENT_NODE);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ZwEP zwEP = new ZwEP();
                zwEpList.add(zwEP);
                setDesc(temp,eElement.getAttribute("desc"));
                setEpId(temp,eElement.getAttribute("ep_id"));
                setGeneric(temp, eElement.getAttribute("generic"));
                setSpecific(temp, eElement.getAttribute("specific"));
                String decoded_name = "";
                try {
                    decoded_name = URLDecoder.decode(eElement.getAttribute("name"), "UTF-8");
                    setName(temp, decoded_name);
                }
                catch (Exception e){
                    System.out.println("ZwEPList::deserialize:Exception when decoding UTF-8:nodeid="+getNodeId()+":epid="+temp+":"+eElement.getAttribute("name"));
                    decoded_name = eElement.getAttribute("name");
                    setName(temp, decoded_name);
                }
                System.out.println("ZwEPList::deserialize:nodeid="+getNodeId()+":epid="+temp+":"+eElement.getAttribute("name")+":"+decoded_name);
                setLocation(temp, eElement.getAttribute("location"));
            }
        }
    }
}


