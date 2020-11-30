package com.springdagger.core.wx.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qiaomu
 * @date: 2020/11/9 15:18
 * @Description: TODO
 */
@Data
public class AuthorizeBody implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 授权后重定向的前端页面地址
     */
    private String redirectUri;

    /**
     * 应用授权作用域，
     * 0 ~ snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
     * 1 ~ snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     *
     */
    private int scope;

    /**
     * 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private String state;

    /**
     * appId
     */
    private String appId;

    /**
     * 授权回调的后端地址
     */
    private String callBackUrl;

}
