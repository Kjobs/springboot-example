package com.springboot.mybatis.api;

import lombok.Data;

import java.io.Serializable;

/**
 * 错误信息封装
 *
 * @author kobs
 */
@Data
public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = -3209207607564440158L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 错误描述
     */
    private String details;

    public ErrorInfo(Integer code, String msg, String details) {
        this.code = code;
        this.msg = msg;
        this.details = details;
    }
}
