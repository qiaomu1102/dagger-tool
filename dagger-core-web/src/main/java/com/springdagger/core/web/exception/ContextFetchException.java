package com.springdagger.core.web.exception;

import com.springdagger.core.tool.api.BaseException;
import com.springdagger.core.tool.api.IResultCode;
import com.springdagger.core.tool.api.ResultCode;

/**
 * @author xiangbinbing
 * @date 2020-12-15 14:56
 */
public class ContextFetchException extends BaseException {
    
    public ContextFetchException() {
        iResultCode = ResultCode.UN_AUTHORIZED;
    }

    public ContextFetchException(String message) {
        super(message);
        iResultCode = ResultCode.UN_AUTHORIZED;
    }

    public ContextFetchException(IResultCode iResultCode) {
        super(iResultCode);
    }

    public ContextFetchException(IResultCode iResultCode, String message) {
        super(iResultCode, message);
    }
}
