package com.hyls.rabbitmq.rabbitmqprovider;

import java.util.Date;

public class Message {
    private String MessageId;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMessageData() {
        return MessageData;
    }

    public void setMessageData(String messageData) {
        MessageData = messageData;
    }


    private String MessageData;
    private String CreateTime;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
