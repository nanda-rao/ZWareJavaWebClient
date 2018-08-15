package com.silabs.zware.zwarewebclient;

import android.os.Parcel;
import android.os.Parcelable;

class ZwEPIf implements Parcelable {
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
    public static final Parcelable.Creator<ZwEPIf> CREATOR
            = new Parcelable.Creator<ZwEPIf>() {
        public ZwEPIf createFromParcel(Parcel in) {
            return new ZwEPIf(in);
        }

        public ZwEPIf[] newArray(int size) {
            return new ZwEPIf[size];
        }
    };
}
