//package com.springdagger.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.springdagger.core.tool.api.R;
//import com.springdagger.core.tool.utils.Base64Util;
//import com.springdagger.core.tool.utils.Func;
//import com.springdagger.core.tool.utils.RedisUtil;
//import com.springdagger.core.tool.utils.StringUtil;
//import com.springdagger.core.wx.WxUtils;
//import com.springdagger.core.wx.model.AccessToken;
//import com.springdagger.core.wx.model.AuthorizeBody;
//import com.springdagger.core.wx.model.WxAuthUserInfoModel;
//import com.springdagger.core.wx.model.WxBaseAuthOpenIdModel;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Assert;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.WebUtils;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.Map;
//
///**
// * @author: qiaomu
// * @date: 2020/12/21 11:31
// * @Description: 微信授权相关
// */
//@Slf4j
//@Api(tags = "微信相关")
//@IgnoreUserToken
//@Controller
//@RequestMapping("/weChat")
//public class WxController {
//
//    @Autowired
//    RedisUtil redisUtil;
//    @Autowired
//    UserBizService userBizService;
//
//    @ResponseBody
//    @ApiOperation(value = "微信授权")
//    @PostMapping(value = "/tx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public R<Object> tx(@Validated @RequestBody WxAuthBody wxAuthBody) {
//        AuthorizeBody authorBody = new AuthorizeBody();
//        authorBody.setAppId(CustomProperties.APP_ID);
//        authorBody.setScope(wxAuthBody.getAuthor());
//        authorBody.setState(wxAuthBody.getExtra());
//        String encodeUrls = Base64Util.encode(wxAuthBody.getReturnUrl());
//        authorBody.setCallBackUrl(CustomProperties.TX_AUTH_RETURN_URL + "?url=" + encodeUrls);
//        String redirectUrl = WxUtils.authorize(authorBody);
//        log.info("微信授权重定向url: " + redirectUrl);
//        return R.data(redirectUrl);
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "微信授权", hidden = true)
//    @PostMapping(value = "/testTx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public R<Object> tx2(HttpServletResponse response, @Validated @RequestBody WxAuthBody wxAuthBody) throws IOException {
//        AuthorizeBody authorBody = new AuthorizeBody();
//        authorBody.setAppId(CustomProperties.APP_ID);
//        authorBody.setScope(wxAuthBody.getAuthor());
//        authorBody.setState(wxAuthBody.getExtra());
//        String encodeUrls = Base64Util.encode(wxAuthBody.getReturnUrl());
//        authorBody.setCallBackUrl(CustomProperties.TX_AUTH_RETURN_URL + "?url=" + encodeUrls);
//        String redirectUrl = WxUtils.authorize(authorBody);
//        log.info("微信授权重定向url: " + redirectUrl);
//        //        response.reset();
////        response.setContentType("application/x-www-form-urlencoded");
////        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
////        response.sendRedirect(redirectUrl);
////        response.sendRedirect(wxAuthBody.getReturnUrl());
//
////        return "redirect:" + redirectUrl;
//        return R.data(redirectUrl);
//    }
//
//    @ApiOperation(value = "静默授权接口回调")
//    @GetMapping(value = "/basetotx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public void basetotx(HttpServletResponse response, String url, String code, String state) throws IOException {
//        String decodeUrl = Base64Util.decode(url);
//        log.info(String.format("微信静默授权回调 url: %s, code: %s, state: %s", decodeUrl, code, state));
//        WxBaseAuthOpenIdModel baseAuth = WxUtils.getBaseAuth(code, CustomProperties.APP_ID, CustomProperties.APP_SECRET);
//        if (baseAuth == null || StringUtil.isBlank(baseAuth.getOpenid())) return;
//        response.addCookie(WebUtils.setCookie("openId", baseAuth.getOpenid()));
//        log.info("redirectUrl==>" + decodeUrl);
//        response.sendRedirect(decodeUrl);
//    }
//
//    @ApiOperation(value = "手动授权接口回调")
//    @GetMapping(value = "/totx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public void totx(HttpServletResponse response, String url, String code, String state) throws IOException {
//        String decodeUrl = Base64Util.decode(url);
//        log.info(String.format("微信手动授权回调 url: %s, code: %s, state: %s", decodeUrl, code, state));
//        WxBaseAuthOpenIdModel baseAuth = WxUtils.getBaseAuth(code, CustomProperties.APP_ID, CustomProperties.APP_SECRET);
//        if (baseAuth == null || StringUtil.isBlank(baseAuth.getOpenid())) {
//            throw new IllegalArgumentException("微信端获取用户信息失败");
//        }
//        WxAuthUserInfoModel wxAuthUserInfo = WxUtils.getWxAuthUserInfo(baseAuth.getAccess_token(), baseAuth.getOpenid());
//        Assert.notNull(wxAuthUserInfo, "微信端获取用户信息失败");
//        log.info("微信手动授权,获取用户信息： " + JSON.toJSONString(wxAuthUserInfo));
//
//
//        String token = userBizService.wxLogin(wxAuthUserInfo.getOpenid());
//        if (Func.isNotBlank(token)) {
//            log.info("手动授权登录生成的token==>\n" + token);
//            response.addCookie(WebUtils.setCookie(AppConst.ACCESS_TOKEN, token));
//        }
//        if (Func.isNoneBlank(wxAuthUserInfo.getNickname())) {
//            response.addCookie(WebUtils.setCookie("nickName", Base64Util.encode(wxAuthUserInfo.getNickname())));
//            response.addCookie(WebUtils.setCookie("userName", Base64Util.encode(wxAuthUserInfo.getNickname())));
//        }
//        response.addCookie(WebUtils.setCookie("openId", wxAuthUserInfo.getOpenid()));
//        response.addCookie(WebUtils.setCookie("headImgUrl", wxAuthUserInfo.getHeadimgurl()));
//        log.info("redirectUrl==>" + decodeUrl);
//        response.sendRedirect(decodeUrl);
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "获取使用JSSDK权限验证配置信息")
//    @GetMapping(value = "/wxConfig", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public R<Map<String, String>> wxConfig(@ApiParam(value = "页面地址") @RequestParam(value = "url") String url) {
//        String accessToken = redisUtil.getString(CacheKeys.HTBXFW_ACCESS_TOKEN);
//        if (StringUtil.isBlank(accessToken)) {
//            AccessToken accessTokenModel = WxUtils.getAccessToken(CustomProperties.APP_ID, CustomProperties.APP_SECRET);
//            if (accessTokenModel == null || StringUtil.isBlank(accessTokenModel.getAccess_token())) {
//                return R.fail("获取微信accessToken失败");
//            }
//            accessToken = accessTokenModel.getAccess_token();
//            redisUtil.expire(CacheKeys.HTBXFW_ACCESS_TOKEN, accessToken, AppConst.WX_ACCESS_TOKEN_EXPIRE);
//        }
//
//        Map<String, String> map = WxUtils.getJSApiTicket(accessToken, CustomProperties.APP_ID, url);
//        return R.data(map);
//    }
//
//}
