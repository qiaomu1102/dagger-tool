package com.springdagger.web.common;


/**
 * @package: com.qiaomu.common.exception
 * @author: kexiong
 * @date: 2019/8/28 16:50
 * @Description: 自定义业务异常
 */
public class CustomException extends RuntimeException {


    private final EnumStatusCode enumStatusCode;
    private final Object resultData;

    public CustomException(EnumStatusCode enumStatusCode){
        this.enumStatusCode = enumStatusCode;
        this.resultData = null;
    }

    public CustomException(EnumStatusCode enumStatusCode, Object resultData){
        this.enumStatusCode = enumStatusCode;
        this.resultData = resultData;
    }

    public EnumStatusCode getEnumStatusCode() {
        return enumStatusCode;
    }

    public Object getResultData() {
        return resultData;
    }
}
