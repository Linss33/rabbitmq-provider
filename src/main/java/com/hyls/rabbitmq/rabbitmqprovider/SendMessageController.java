package com.hyls.rabbitmq.rabbitmqprovider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(Message message) {
        message.setMessageId(String.valueOf(UUID.randomUUID()));
        message.setMessageData("nihao ");
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",message.getMessageId());
        map.put("messageData",message.getMessageData());
        map.put("createTime",message.getCreateTime());
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @PostMapping(value = "/sendMessage")
    public String sendMessage(@RequestParam("messageData") String messageData){
        Message message = new Message();
        message.setMessageId(String.valueOf(UUID.randomUUID()));
        message.setMessageData(messageData);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",message.getMessageId());
        map.put("messageData",message.getMessageData());
        map.put("createTime",message.getCreateTime());
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
        return "ok";
    }
}
