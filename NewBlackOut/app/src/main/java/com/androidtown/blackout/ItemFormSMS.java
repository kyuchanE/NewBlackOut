package com.androidtown.blackout;

public class ItemFormSMS {

    private String address,threadId,contactId, contactId_string,timestamp, body,read,messageId;//보안을 위해서 private로
    private int imageNumber;


    public ItemFormSMS(String messageId, String threadId, String address, long contactId, String contactId_string, String timetamp, String body, long read, int imageNumber1) {//new생성자를 통해서 생성자가 만들어진다.
        this.messageId = messageId;
        this.threadId = threadId;
        this.address = address;
        this.contactId = String.valueOf(contactId);
        this.contactId_string = contactId_string;
        this.timestamp = timetamp;
        this.body = body;
        this.read = String.valueOf(read);
        this.imageNumber = imageNumber1;
    }

    public String getMessageId() {//외부로 id값을 리턴해서 내보내준다.

        return messageId;
    }

    public String getThreadId() {//외부로 이미지 값을 리턴해서 보내준다.

        return threadId;
    }

    public String getAddress() {

        return address;
    }

    public String getContactId() {

        return contactId;
    }

    public String getContactId_string() {

        return contactId_string;
    }

    public String getTimestamp() {

        return timestamp;
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


    public void setMessageId(String messageId) {//외부로 id값을 리턴해서 내보내준다.

        this.messageId = messageId;
    }

    public void setThreadId(String threadId) {//외부로 이미지 값을 리턴해서 보내준다.

        this.threadId = threadId;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setContactId(String contactId) {

        this.contactId = contactId;
    }

    public void setContactId_string(String contactId_string) {

        this.contactId_string = contactId_string;
    }

    public void setTimestamp(String timestamp) {

        this.timestamp = timestamp;
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