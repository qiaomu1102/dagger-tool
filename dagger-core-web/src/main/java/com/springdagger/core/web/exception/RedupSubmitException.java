package com.springdagger.core.web.exception;


import com.springdagger.core.tool.api.BaseException;
import com.springdagger.core.tool.api.IResultCode;
import com.springdagger.core.tool.api.ResultCode;

/**
 * @author: qiaomu
 * @date: 2020/12/9 10:39
 * @Description: TODO
 */
public class RedupSubmitException extends BaseException {

    public RedupSubmitException() {
        iResultCode = ResultCode.REDUP_SUBMIT_ERROR;
    }

    public RedupSubmitException(String message) {
        super(message);
        iResultCode = ResultCode.REDUP_SUBMIT_ERROR;
    }

    public RedupSubmitException(IResultCode iResultCode) {
        super(iResultCode);
    }

    public RedupSubmitException(IResultCode iResultCode, String message) {
        super(iResultCode, message);
    }
}
