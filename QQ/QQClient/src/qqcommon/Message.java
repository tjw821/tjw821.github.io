package qqcommon;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;//发送方
    private String getter;//接收方
    private String content;//消息
    private String sendTime;//发送时间
    private String messType;//消息内容



    public Message() {
    }

    public Message(String sender, String getter, String content, String sendTime, String messType) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendTime = sendTime;
        this.messType = messType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
    }
}
