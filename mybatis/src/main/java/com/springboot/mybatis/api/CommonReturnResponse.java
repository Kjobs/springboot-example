package com.springboot.mybatis.api;

/**
 * 响应结果抽象
 *
 * @author kobs
 */
public class CommonReturnResponse extends CommonResultResponse<Object>  {

    private static final long serialVersionUID = -8069876382651884534L;

    public static CommonReturnResponse success(Object o) {
        CommonReturnResponse response = new CommonReturnResponse();
        response.setSuccess(true);
        response.setResult(o);
        response.setErrorInfo(null);
        return response;
    }

    public static CommonReturnResponse error(int code, String msg, String details) {
        CommonReturnResponse response = new CommonReturnResponse();
        response.setSuccess(false);
        response.setResult(null);
        ErrorInfo errorInfo = new ErrorInfo(code, msg, details);
        response.setErrorInfo(errorInfo);
        return response;
    }

    public static CommonReturnResponse error(CommonErrorEnum errorEnum) {
        CommonReturnResponse response = new CommonReturnResponse();
        response.setSuccess(false);
        response.setResult(null);
        ErrorInfo errorInfo = new ErrorInfo(errorEnum.getCode(), errorEnum.getMessage(), null);
        response.setErrorInfo(errorInfo);
        return response;
    }

    /**
     * 错误异常
     *
     * @param exception 异常
     */
    public static CommonReturnResponse error(Exception exception) {
        // 在这里用于区别自定义异常判断
        if (exception instanceof ServerInternalException) {
            ServerInternalException e = (ServerInternalException) exception;
            return error(e.getCode(), e.getMessage(), null);
        }
        return error(CommonErrorEnum.GLOBAL_ERROR);
    }
}
