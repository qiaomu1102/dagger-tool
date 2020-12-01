package com.springdagger.core.tool.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: qiaomu
 * @date: 2020/12/1 18:37
 * @Description: TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private IResultCode iResultCode;
    private Object resultData;

    public BaseException(IResultCode iResultCode){
        super(iResultCode.getMessage());
        this.iResultCode = iResultCode;
        this.resultData = null;
    }

    public BaseException(IResultCode iResultCode, Object resultData){
        this.iResultCode = iResultCode;
        this.resultData = resultData;
    }
}
