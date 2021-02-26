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

    public IResultCode iResultCode;

    public BaseException(){
        this.iResultCode = ResultCode.FAILURE;
    }

    public BaseException(String message){
        super(message);
    }

    public BaseException(IResultCode iResultCode){
        super(iResultCode.getMessage());
        this.iResultCode = iResultCode;
    }

    public BaseException(IResultCode iResultCode, String message){
        super(message);
        this.iResultCode = iResultCode;
    }
}
