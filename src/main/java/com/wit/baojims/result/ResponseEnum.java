package com.wit.baojims.result;

public enum ResponseEnum {
    ADMIN_INFO_NULL(300, "用户信息不能为空"),
    MEMBER_INFO_NULL(301, "输入人员信息不能为空"),
    MEMBER_UPDATE_NULL(302, "人员id不能为空"),
    ACTIVITY_INFO_NULL(303, "活动信息不能为空"),
    VISIT_INFO_NULL(304, "走访信息不能为空");


    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
