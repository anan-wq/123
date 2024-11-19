package com.bestbigkk.common.enums;

/**
* @author: 开
* @date: 2020-04-19 20:01:03
* @describe: 系统身份
*/
public enum  Identity {

    /**
     * 身份
     */
    EVENT_OPERATOR(1, "General User"),
    APPROVAL_OPERATOR(2, "Admin Staff"),
    MATERIAL_OPERATOR(3, "Management Staff"),
    SYSTEM_OPERATOR(4, "General Staff");

    public Integer identityCode;
    public String identityName;

    Identity(int identityCode, String identityName) {
        this.identityCode = identityCode;
        this.identityName = identityName;
    }

}
