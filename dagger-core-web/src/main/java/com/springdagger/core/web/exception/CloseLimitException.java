package com.springdagger.core.web.exception;

import com.springdagger.core.tool.api.IResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @package: com.qiaomu.common.exception
 * @author: kexiong
 * @date: 2019/8/28 16:50
 * @Description: TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CloseLimitException extends RuntimeException {

    private IResultCode iResultCode;
    private Object resultData;

    public CloseLimitException(IResultCode iResultCode){
        super(iResultCode.getMessage());
        this.iResultCode = iResultCode;
        this.resultData = null;
    }

    public CloseLimitException(IResultCode iResultCode, Object resultData){
        this.iResultCode = iResultCode;
        this.resultData = resultData;
    }
}
