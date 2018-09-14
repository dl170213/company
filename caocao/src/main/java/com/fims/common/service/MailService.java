package com.fims.common.service;

/**
 * Created by Administrator on 2018/7/26.
 */
public interface MailService {

    void sendMessageMail(Object params, String title, String templateName);
}
