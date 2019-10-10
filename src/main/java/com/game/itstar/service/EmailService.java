package com.game.itstar.service;

/**
 * @Author 朱斌
 * @Date 2019/10/10  11:06
 * @Desc
 */
public interface EmailService {
    Boolean sendHtmlEmailCZ(String sender);
    Boolean checkSMSCode(String sender, String code);
}
