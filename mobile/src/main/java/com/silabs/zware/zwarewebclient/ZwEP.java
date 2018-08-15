package com.silabs.zware.zwarewebclient;

import android.os.Parcel;
import android.os.Parcelable;

class ZwEP implements Parcelable {
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
    public static final Parcelable.Creator<ZwEP> CREATOR
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
