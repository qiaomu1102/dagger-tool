package com.springdagger.core.web.exception;

import com.springdagger.core.tool.api.BaseException;
import com.springdagger.core.tool.api.IResultCode;
import com.springdagger.core.tool.api.ResultCode;

/**
 * @author: qiaomu
 * @date: 2020/12/9 10:39
 * @Description: TODO
 */
public class VerifyException extends BaseException {

    public VerifyException() {
        iResultCode = ResultCode.VERIFY_ERROR;
    }

    public VerifyException(String message) {
        super(message);
        iResultCode = ResultCode.VERIFY_ERROR;
    }

    public VerifyException(IResultCode iResultCode) {
        super(iResultCode);
    }

    public VerifyException(IResultCode iResultCode, String message) {
        super(iResultCode, message);
    }
}
