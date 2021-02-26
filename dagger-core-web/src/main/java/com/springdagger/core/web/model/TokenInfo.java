package com.springdagger.core.web.model;

import lombok.Data;

/**
 * @author: qiaomu
 * @date: 2020/12/10 11:50
 * @Description: TODO
 */
@Data
public class TokenInfo {

    /**
     * 令牌值
     */
    private String token;

    /**
     * 过期秒数
     */
    private int expire;
}
