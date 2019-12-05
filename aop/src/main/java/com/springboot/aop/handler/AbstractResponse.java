package com.springboot.aop.handler;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kobs
 * @date 2019/12/5
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
