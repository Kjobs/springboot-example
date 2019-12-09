package com.springboot.mybatis.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kobs
 */
@Data
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = -1412862937275946232L;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 错误信息实体
     */
    private ErrorInfo errorInfo;
}
