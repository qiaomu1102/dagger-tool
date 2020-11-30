package com.springdagger.core.wx.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qiaomu
 * @date: 2020/11/9 16:53
 * @Description: TODO
 */
@Data
public class WxBaseAuthOpenIdModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

}
