package com.springdagger.core.web.exception;

import com.springdagger.core.tool.api.BaseException;
import com.springdagger.core.tool.api.IResultCode;
import com.springdagger.core.tool.api.ResultCode;

/**
 * @author: qiaomu
 * @date: 2020/12/3 13:46
 * @Description: TODO
 */
public class SecureException extends BaseException {

    public SecureException() {
        iResultCode = ResultCode.UN_AUTHORIZED;
    }

    public SecureException(String message) {
        super(message);
        iResultCode = ResultCode.UN_AUTHORIZED;
    }

    public SecureException(IResultCode iResultCode) {
        super(iResultCode);
    }

    public SecureException(IResultCode iResultCode, String message) {
        super(iResultCode, message);
    }
}
