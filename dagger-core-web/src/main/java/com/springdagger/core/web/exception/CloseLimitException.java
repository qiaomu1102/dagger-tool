package com.springdagger.core.web.exception;

import com.springdagger.core.tool.api.BaseException;
import com.springdagger.core.tool.api.IResultCode;
import com.springdagger.core.tool.api.ResultCode;

/**
 * @package: com.qiaomu.common.exception
 * @author: kexiong
 * @date: 2019/8/28 16:50
 * @Description: TODO
 */
public class CloseLimitException extends BaseException {

    public CloseLimitException(String message){
        super(message);
    }

    public CloseLimitException(IResultCode iResultCode){
        super(iResultCode);
    }

    public CloseLimitException(IResultCode iResultCode, Object resultData){
        super(iResultCode, resultData);
    }
}
