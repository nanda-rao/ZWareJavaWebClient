
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

public class ZwEPIFList extends ZwXMLHandler {
    /*class ZwEPIf_int implements Parcelable{
        String desc;//intf_desc
        String id;//intf_id
        String name;//intf_name
        String ver;
        String real_ver;
        String sec;
        String unsec;
        public ZwEPIf(){

        }
        public ZwEPIf(Parcel in){
            this.desc = in.readString();
            this.id = in.readString();
            this.name = in.readString();
            this.ver = in.readString();
            this.real_ver = in.readString();
            this.sec = in.readString();
            this.unsec = in.readString();
        }
        // This is where we will write our member variables in Parcel.
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.desc);
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.ver);
            dest.writeString(this.real_ver);
            dest.writeString(this.sec);
            dest.writeString(this.unsec);
        }
        @Override
        public int describeContents(){
            return 0;
        }
        public final Parcelable.Creator<ZwEPIf> CREATOR
                = new Parcelable.Creator<ZwEPIf>() {
            public ZwEPIf createFromParcel(Parcel in) {
                return new ZwEPIf(in);
            }

            public ZwEPIf[] newArray(int size) {
                return new ZwEPIf[size];
            }
        };
    }*/
    String ep_desc;
    List<ZwEPIf> zwEpIfList;
    int zwEpIfListSize;

    public String getEp_desc() {
        return this.ep_desc;
    }

    public void setEp_desc(String ep_desc) {
        this.ep_desc = ep_desc;
    }

    public String getDesc(int iid) {
        return this.zwEpIfList.get(iid).desc;
    }

    public void setDesc(int iid,String desc) {
        this.zwEpIfList.get(iid).desc = desc;
    }

    public String getName(int iid) {
        return this.zwEpIfList.get(iid).name;
    }

    public void setName(int iid,String name) {
        this.zwEpIfList.get(iid).name = name;
    }

    public String getVer(int iid) {
        return this.zwEpIfList.get(iid).ver;
    }

    public void setVer(int iid,String ver) {
        this.zwEpIfList.get(iid).ver = ver;
    }

    public String getRealVer(int iid) {
        return this.zwEpIfList.get(iid).real_ver;
    }

    public void setRealVer(int iid,String real_ver) {
        this.zwEpIfList.get(iid).real_ver = real_ver;
    }

    public String getSec(int iid) {
        return this.zwEpIfList.get(iid).sec;
    }

    public void setSec(int iid,String sec) {
        this.zwEpIfList.get(iid).sec = sec;
    }

    public String getUnsec(int iid) {
        return this.zwEpIfList.get(iid).unsec;
    }

    public void setUnsec(int iid,String unsec) {
        this.zwEpIfList.get(iid).unsec = unsec;
    }

    public ZwEPIFList(String ep_desc,String xml){
        super(xml);
        this.ep_desc = ep_desc;
    }
    public ZwEPIFList(String xml){
        super(xml);
    }
    public static final Parcelable.Creator<ZwEPIFList> CREATOR
            = new Parcelable.Creator<ZwEPIFList>() {
        public ZwEPIFList createFromParcel(Parcel in) {
            return new ZwEPIFList(in.readString());
            /*TO DO: Check if ep_desc may be required here..should be OK for now as we do not expect to serialize ZwEPList*/
        }

        public ZwEPIFList[] newArray(int size) {
            return new ZwEPIFList[size];
        }
    };
    public String serialize(){
        return "";
    }
    public void deserialize(){
        Document doc = (Document) super.getDOMParser();
        //Element rNode = doc.getElementById("zwnode");
        //setNodeId(rNode.getAttribute("node_id"));

        NodeList nList = doc.getElementsByTagName("zwif");
        System.out.println("nList.getLength:"+nList.getLength());
        zwEpIfListSize = nList.getLength();
        this.zwEpIfList = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("ZwEPList::deserialize:ep_desc="+getEp_desc()+":ifid="+temp+":"+nNode.getNodeType()+":ELEMENT_NODE:"+Node.ELEMENT_NODE);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ZwEPIf zwEPIf = new ZwEPIf();
                zwEpIfList.add(zwEPIf);
                setDesc(temp,eElement.getAttribute("desc"));

                String if_name = eElement.getAttribute("name");
                System.out.println("if_name=" + if_name);

                String[] if_name_start = if_name.split("COMMAND_CLASS_",2);
                if(if_name_start.length == 2) {
                    System.out.println("Command Class Name=" + if_name_start[1]);
                    /*Ignore string "COMMAND_CLASS_" in "COMMAND_CLASS_XXX" and just use "XXX" for name*/
                    //setName(temp,eElement.getAttribute("name"));
                    setName(temp, if_name_start[1]);
                }
                else { //Just display the entire command class name...
                    System.out.println("Command Class Name=" + if_name);
                    setName(temp, if_name);
                }
                setVer(temp, eElement.getAttribute("ver"));
                setRealVer(temp, eElement.getAttribute("real_ver"));
                setSec(temp, eElement.getAttribute("sec"));
                setUnsec(temp, eElement.getAttribute("unsec"));

                /*String decoded_name = "";
                try {
                    decoded_name = URLDecoder.decode(eElement.getAttribute("name"), "UTF-8");
                    setName(temp, decoded_name);
                }
                catch (Exception e){
                    System.out.println("ZwEPList::deserialize:Exception when decoding UTF-8:nodeid="+getNodeId()+":epid="+temp+":"+eElement.getAttribute("name"));
                    decoded_name = eElement.getAttribute("name");
                    setName(temp, decoded_name);
                }*/
                System.out.println("ZwEPIFList::deserialize:ep_desc="+getEp_desc()+":intf_id="+temp+":"+eElement.getAttribute("name"));
            }
        }
    }
}


