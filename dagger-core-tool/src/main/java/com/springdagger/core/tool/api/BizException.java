package com.springdagger.core.tool.api;

/**
 * @package: com.qiaomu.common.exception
 * @author: kexiong
 * @date: 2019/8/28 16:50
 * @Description: 自定义业务异常
 */
public class BizException extends BaseException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(IResultCode iResultCode) {
        super(iResultCode);
    }

    public BizException(IResultCode iResultCode, String message) {
        super(iResultCode, message);
    }
}
