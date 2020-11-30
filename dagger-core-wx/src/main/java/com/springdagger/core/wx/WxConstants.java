package com.springdagger.core.wx;

/**
 * @author: qiaomu
 * @date: 2020/11/9 14:47
 * @Description: TODO
 */
public interface WxConstants {

    /* =====================  微信网页授权相关接口  start ======================*/

    /**
     * 第一步：用户同意授权，获取code
     */
    String AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=SCOPE&state=STATE#wechat_redirect&connect_redirect=1";
    /**
     * 第二步：通过code换取网页授权access_token
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 第三步：拉取用户信息(需scope为 snsapi_userinfo)
     */
    String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /* ========================  微信网页授权相关接口  end =======================*/

    /**
     * 获取一般业务所需的access_token
     */
    String COMMON_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取用户信息(通过关注公众号可以获取openId，可以获取用户信息）
     */
    String COMMON_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取jsapi_ticket
     */
    String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 微信公众号二维码
     */
    String GET_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

    /**
     * 创建菜单
     */
    String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
}
