package net.hwyz.iov.cloud.tsp.sgw.enums;

import lombok.AllArgsConstructor;

/**
 * HTTP Headers
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum HttpHeaders {

    /** 客户端ID **/
    CLIENT_ID("clientId"),
    /** 令牌 **/
    TOKEN("token"),
    /** 账号唯一ID **/
    UID("uid");

    public final String value;

}
