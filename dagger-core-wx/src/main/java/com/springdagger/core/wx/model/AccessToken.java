package com.springdagger.core.wx.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qiaomu
 * @date: 2020/11/9 17:37
 * @Description: TODO
 */
@Data
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200s
     */

    private String access_token;
    private int expires_in;
}
