package com.example.mycontacts;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String name;
    private String phoneNum;
    private String address;

    public Contact() {

    }

    public Contact(String name, String phoneNum, String address) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        phoneNum = in.readString();
        address = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phoneNum);
        dest.writeString(address);
    }
}
