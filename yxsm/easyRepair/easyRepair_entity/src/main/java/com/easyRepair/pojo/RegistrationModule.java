package com.easyRepair.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 Created by sean on 2016/11/24. */
@ApiModel(value = "RegistrationModule", description = "签到记录返回数据")
public class RegistrationModule {
    @ApiModelProperty(value = "签到日期", required = true)
    private String registrationDate;
    @ApiModelProperty(value = "是否签到 0=否 1=是", required = true)
    private String isRegistration;
    
    public String getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getIsRegistration() {
        return isRegistration;
    }
    
    public void setIsRegistration(String isRegistration) {
        this.isRegistration = isRegistration;
    }
}
