package com.springdagger.core.wx.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qiaomu
 * @date: 2020/11/9 17:06
 * @Description: TODO
 */
@Data
public class WxAuthUserInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String openid;
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;
}
