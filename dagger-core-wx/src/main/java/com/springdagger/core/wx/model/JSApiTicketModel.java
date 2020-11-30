package com.springdagger.core.wx.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qiaomu
 * @date: 2020/11/9 17:59
 * @Description: TODO
 */
@Data
public class JSApiTicketModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int errcode;
    private String errmsg;
    private String ticket;
    private int expires_in;

}
