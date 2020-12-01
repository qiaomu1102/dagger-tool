package com.springdagger.core.tool.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @package: com.qiaomu.common.exception
 * @author: kexiong
 * @date: 2019/8/28 16:50
 * @Description: 自定义业务异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {


    private IResultCode iResultCode;
    private Object resultData;

    public BizException(IResultCode iResultCode){
        super(iResultCode.getMessage());
        this.iResultCode = iResultCode;
        this.resultData = null;
    }

    public BizException(IResultCode iResultCode, Object resultData){
        this.iResultCode = iResultCode;
        this.resultData = resultData;
    }
}
