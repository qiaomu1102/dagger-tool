package com.springdagger.core.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: qiaomu
 * @date: 2020/12/7 17:01
 * @Description: TODO
 */
@Data
public class EncryptedReq<T> {
    /** 签名 */
    @NotBlank(message = "用户签名不能为空")
    private String sign;
    /** 加密请求数据 */
    @NotBlank(message = "加密数据不能为空")
    private String encryptedData;
    /** 原始请求数据（解密后回填到对象） */
    private T data;
    /** 请求的时间戳 */
    @NotNull(message = "时间戳不能为空")
    private Long timestamp;
}
