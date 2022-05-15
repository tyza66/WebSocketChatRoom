package com.tyza66.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Msg {
    private String type;
    private List<String>userList;
    private String msgSender;
    private String msgReceiver;
    private String msgInfo;
    private Date msgDate;
    private String msgDateStr;
    public String getMsgSender() {
        return msgSender;
    }
    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }
    public String getMsgReceiver() {
        return msgReceiver;
    }
    public void setMsgReceiver(String msgReceiver) {
        this.msgReceiver = msgReceiver;
    }
    public String getMsgInfo() {
        return msgInfo;
    }
    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }
    public Date getMsgDate() {
        return msgDate;
    }
    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }
    public String getMsgDateStr() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msgDateStr=sdf.format(msgDate);
        return msgDateStr;
    }
    public void setMsgDateStr(String msgDateStr) {
        this.msgDateStr = msgDateStr;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getUserList() {
        return userList;
    }

    public void setUserList() {
        setUserList();
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }











}
