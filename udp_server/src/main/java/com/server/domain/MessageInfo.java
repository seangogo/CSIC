package com.server.domain;

public class MessageInfo {
    //源客户端命名空间
    private String nameSpace;
    //目标厂商
    private String developers;
    //指令类型
    private String msgType;
    //消息内容
    private short msgContent;
    //指令模拟量
    private float real;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public short getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(short msgContent) {
        this.msgContent = msgContent;
    }

    public float getReal() {
        return real;
    }

    public void setReal(float real) {
        this.real = real;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "nameSpace='" + nameSpace + '\'' +
                ", developers='" + developers + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgContent=" + msgContent +
                ", real=" + real +
                '}';
    }

}