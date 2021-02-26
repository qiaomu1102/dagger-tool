package com.springdagger.core.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springdagger.core.tool.utils.OkHttpUtil;
import com.springdagger.core.tool.utils.StringUtil;
import com.springdagger.core.wx.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.springdagger.core.wx.WxConstants.*;


/**
 * @author: qiaomu
 * @date: 2020/11/9 16:41
 * @Description: TODO
 */
@Slf4j
public class WxUtils {

    /**
     * 网页授权
     */
    public static String authorize(AuthorizeBody body) {
        log.info("=============== /tx  微信登陆认证  redirectUri ==========" + JSON.toJSONString(body));
        String scope = body.getScope() == 1 ? "snsapi_userinfo" : "snsapi_base";
        log.info("调用授权地址：" + body.getCallBackUrl());
        String callBackUrl = body.getCallBackUrl();
        try {
            callBackUrl = URLEncoder.encode(body.getCallBackUrl(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("微信授权链接编码异常", e);
        }
        String redirectUrl = WxConstants.AUTHORIZE.replace("APPID", body.getAppId()).replace("URL", callBackUrl).replace("SCOPE", scope).replace("STATE", body.getState());
        log.info("redirectUrl:" + redirectUrl);
        return redirectUrl;
    }

    /**
     * 通过code换取网页授权access_token
     */
    public static WxBaseAuthOpenIdModel getBaseAuth(String code, String appId, String appSecret) {
        String accessTokenUrl = ACCESS_TOKEN_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
        String responseStr = OkHttpUtil.get(accessTokenUrl, null);
        log.info("getBaseAuth微信授权获取accesstoken:================ " + responseStr);
        if (StringUtil.isNotBlank(responseStr) && !responseStr.contains("errcode")) {
            return JSON.parseObject(responseStr, WxBaseAuthOpenIdModel.class);
        }
        return null;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    public static WxAuthUserInfoModel getWxAuthUserInfo(String accessToken, String openId) {
        String userInfo = OkHttpUtil.get(USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId), null);
        log.info("getWxAuthUserInfo获取用户信息请求结果:================ " + userInfo);
        if (StringUtil.isNotBlank(userInfo) && !userInfo.contains("errcode")) {
            return JSON.parseObject(userInfo, WxAuthUserInfoModel.class);
        }
        return null;
    }

    /**
     * 获取一般业务所需的access_token
     */
    public static AccessToken getAccessToken(String appId, String appSecret) {
        String url = COMMON_ACCESS_TOKEN.replace("APPID", appId).replace("APPSECRET", appSecret);
        String result = OkHttpUtil.get(url, null);
        log.info("getAccessToken获取一般业务所需的access_token:================ " + result);
        if (StringUtil.isNotBlank(result) && !result.contains("errcode")) {
            return JSON.parseObject(result, AccessToken.class);
        }
        return null;
    }

    /**
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据，jsapi_ticket的有效期为7200秒，必须在自己的服务全局缓存jsapi_ticket
     */
    public static Map<String, String> getJSApiTicket(String accessToken, String appId, String url) {
        String jsapi_url = JSAPI_TICKET.replace("ACCESS_TOKEN", accessToken);
        String result = OkHttpUtil.get(jsapi_url, null);
        log.info("getJSApiTicket获取jsapi_ticket结果:================ " + result);
        JSApiTicketModel jsApiTicketModel = JSON.parseObject(result, JSApiTicketModel.class);
        if (jsApiTicketModel != null && jsApiTicketModel.getErrcode() == 0) {
            return JsApiSign.sign(jsApiTicketModel.getTicket(), url, appId);
        }
        return null;
    }

    /**
     * 获取微信公众号的二维码
     *
     * 整型  场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     *       {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
     * 字符串   场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     *       {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
     */
    public static String getWxQRCodeUrl(String accessToken, String sceneStr) {
        String params = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": " + "\"" + sceneStr + "\"}}}";
        String result = OkHttpUtil.postJson(GET_QRCODE.replace("ACCESS_TOKEN", accessToken), params);
        log.info("getWxQRCodeUrl获取公众号二维码:================ " + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("url");
    }

    /**
     * 创建菜单，待完善
     */
    public static boolean createMenu(String accessToken, String body) {
        String url = CREATE_MENU.replace("ACCESS_TOKEN", accessToken);
        String result = OkHttpUtil.postJson(url, body);
        log.info("getJSApiTicket获取jsapi_ticket结果:================ " + result);
        if (StringUtil.isNotBlank(result) && result.contains("errcode")) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getIntValue("errcode") == 0;
        }
        return false;
    }

    /**
     * 获取用户基本信息（包括UnionID机制）
     */
    public static WxUserInfo getWxUserInfo(String accessToken, String openId) {
        String url = COMMON_USER_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        String result = OkHttpUtil.get(url, null);
        log.info("getJSApiTicket获取jsapi_ticket结果:================ " + result);
        if (StringUtil.isNotBlank(result) && !result.contains("errcode")) {
            return JSON.parseObject(result, WxUserInfo.class);
        }
        return null;
    }


}
