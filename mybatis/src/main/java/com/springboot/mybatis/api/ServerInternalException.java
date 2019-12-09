package com.springboot.mybatis.api;

/**
 * @author kobs
 * @date 2019/12/9
 */
public class ServerInternalException extends RuntimeException {

    private final static Integer ERROR_CODE = 50000;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ServerInternalException() {
        super();
        this.setCode(ERROR_CODE);
    }

    public ServerInternalException(String message) {
        super(message);
        this.setCode(ERROR_CODE);
    }
}
