package com.androidtown.blackout;

public class ItemFormSMS {

    private String timetamp, contactId_string, mType, address, body, read;//보안을 위해서 private로
    private int imageNumber;


    public ItemFormSMS(String timetamp,String contactId_string,String mType,String address,String body,long read ,  int imageNumber1) {//new생성자를 통해서 생성자가 만들어진다.
        this.timetamp = timetamp;
        this.contactId_string = contactId_string;
        this.mType = mType;
        this.address = address;
        this.body = body;
        this.read = String.valueOf(read);
        this.imageNumber = imageNumber1;
    }

    public String getTimetamp() {

        return timetamp;
    }

    public String getContactId_string() {

        return contactId_string;
    }

    public String getmType() {

        return mType;
    }

    public String getAddress() {

        return address;
    }

    public String getBody() {

        return body;
    }

    public String getRead() {

        return read;
    }

    public int getImageNumber1() {

        return imageNumber;
    }


    public void setTimetamp(String timetamp) {

        this.timetamp = timetamp;
    }

    public void setContactId_string(String contactId_string) {

        this.contactId_string = contactId_string;
    }


    public void setmType(String mType) {

        this.mType = mType;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setBody(String body) {

        this.body = body;
    }

    public void setRead(String read) {

        this.read = read;
    }

    public void setImageNumber(int imageNumber1) {

        this.imageNumber = imageNumber1;
    }

}