package com.springdagger.core.web.context;

import com.springdagger.core.web.exception.ContextFetchException;
import com.springdagger.core.tool.api.ResultCode;
import com.springdagger.core.tool.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangbinbing
 * @date 2020-12-15 14:43
 */
@Slf4j
public class UserContextHolder {

    private volatile static ThreadLocal<String> inheritableInvoiceHolder = new InheritableThreadLocal<>();

    public static void create(String tokenInfo) {
        inheritableInvoiceHolder.remove();
        inheritableInvoiceHolder.set(tokenInfo);
    }

    public static void clean() {
        inheritableInvoiceHolder.remove();
    }

    public static String getUserId() {
        String tokenInfo = inheritableInvoiceHolder.get();
        if (StringUtil.isBlank(tokenInfo)) {
//            log.error("getUserId 获取上下文为空");
            throw new ContextFetchException(ResultCode.LOGIN_EXPIRED, "Failed to obtain user information");
        }
        return tokenInfo;
    }
}
