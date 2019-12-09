package com.springboot.mybatis.api;

/**
 * 错误状态码及错误信息枚举类
 *
 * @author kobs
 */
public enum CommonErrorEnum {

    /**
     * 自定义错误状态码，用以标记错误详情
     */
    GLOBAL_ERROR(50000, "服务器请求出现异常"),
    DATA_SAVE_ERROR(500001, "数据保存时出错"),
    DATA_UPDATE_ERROR(500002,"数据更新时出错");

    private int code;
    private String message;

    CommonErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
