package com.hyls.rabbitmq.rabbitmqprovider;

import org.apache.camel.ProducerTemplate;
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
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;


@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    CamelContext camelContext;


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
        map.put("createTime1",message.getCreateTime());
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
        return "ok";
    }
    @PostMapping(value = "/camelSendMessage")
    public String camelSendMessage(@RequestParam("camelSendMessage") String camelSendMessage){
        Message message = new Message();
        message.setMessageId(String.valueOf(UUID.randomUUID()));
        message.setMessageData(camelSendMessage);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",message.getMessageId());
        map.put("messageData",message.getMessageData());
        map.put("createTime",message.getCreateTime());


        String HOST="localhost";
        String PORT="5672";
        String USER_NAME="guest";
        String PASSWORD="guest";


        String route="rabbitmq://"+HOST+":"+PORT+"/TestDirectExchange?"
                + "username="+USER_NAME
                + "&password="+PASSWORD
                + "&autoDelete=false"
                + "&autoAck=false"
                + "&declare=false"
                + "&vhost=/"
                +"&routingKey=TestDirectRouting"
                +"&guaranteedDeliveries=true";

        ProducerTemplate producerTemplate=camelContext.createProducerTemplate();
        producerTemplate.sendBody(route,map);

        return "ok";
    }
}
