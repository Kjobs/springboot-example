package com.springboot.mybatis.api;

/**
 * 结果封装
 *
 * @author kobs
 */
public class CommonResultResponse<TResult> extends AbstractResponse {

    private static final long serialVersionUID = -4071243687022402968L;

    private TResult result;

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }
}
