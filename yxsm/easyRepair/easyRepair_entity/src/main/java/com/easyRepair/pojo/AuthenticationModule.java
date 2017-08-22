package com.easyRepair.pojo;

import com.easyRepair.tabEntity.ServiceArea;

import java.util.List;
import java.util.Set;

/**
 * Created by sean on 2016/12/28.
 */
//认证模型
public class AuthenticationModule {
    private String identityCode, profile, jobTitle, realName;
    private Set<ServiceArea> serviceAreaSet;
    private List<String> urls;


    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Set<ServiceArea> getServiceAreaSet() {
        return serviceAreaSet;
    }

    public void setServiceAreaSet(Set<ServiceArea> serviceAreaSet) {
        this.serviceAreaSet = serviceAreaSet;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
