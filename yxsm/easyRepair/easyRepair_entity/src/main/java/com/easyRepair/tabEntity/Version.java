package com.easyRepair.tabEntity;

public class Version {
    private Long id;
    private String code;
    private String isupdate;
    private String appstore;
    private String android;
    private java.sql.Timestamp createtime;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getIsupdate() {
        return isupdate;
    }
    
    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }
    
    public String getAppstore() {
        return appstore;
    }
    
    public void setAppstore(String appstore) {
        this.appstore = appstore;
    }
    
    public String getAndroid() {
        return android;
    }
    
    public void setAndroid(String android) {
        this.android = android;
    }
    
    public java.sql.Timestamp getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(java.sql.Timestamp createtime) {
        this.createtime = createtime;
    }
}
