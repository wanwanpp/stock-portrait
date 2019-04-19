package com.wp.common.enums;

public enum ResultEnum {

    SUCCEED(0, "成功"),
    PARAM_ERROR(1, "请求参数错误"),
    LOGIN_FAIL(2, "登录失败"),
    ROLE_ERROR(3, "角色权限有误"),;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
