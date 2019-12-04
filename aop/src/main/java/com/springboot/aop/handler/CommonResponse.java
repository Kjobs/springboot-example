package com.springboot.aop.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应结果抽象
 *
 * @author kobs
 * @date 2019/12/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 1904960908017396910L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 实体数据
     */
    private Object data;

    /**
     * 请求是否成功
     */
    private Boolean success;


}
