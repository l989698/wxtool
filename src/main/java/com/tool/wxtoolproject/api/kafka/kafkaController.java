package com.tool.wxtoolproject.api.kafka;

import com.tool.wxtoolproject.api.sys.annotation.PassToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@Controller
public class kafkaController {
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//
//    /**
//     * 生产消息
//     *
//     * @param normalMessage
//     * @author liuhy
//     * @date 2021/9/17 14:23
//     */
//    @PassToken
//    @GetMapping("/normal")
//    public void sendMessage1(String normalMessage) {
//        kafkaTemplate.send("topic1", normalMessage);
//    }
//
//    /**
//     * 带回调的生产者
//     *
//     * @param callbackMessage
//     * @author liuhy
//     * @date 2021/9/17 14:22
//     */
//    @PassToken
//    @GetMapping("/callbackOne")
//    public void sendMessage2(String callbackMessage) {
//        kafkaTemplate.send("topic1", callbackMessage).addCallback(success -> {
//            // 消息发送到的topic
//            String topic = success.getRecordMetadata().topic();
//            // 消息发送到的分区
//            int partition = success.getRecordMetadata().partition();
//            // 消息在分区内的offset
//            long offset = success.getRecordMetadata().offset();
//            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
//        }, failure -> {
//            System.out.println("发送消息失败:" + failure.getMessage());
//        });
//    }
//
//
//    // 消费监听
//    @KafkaListener(topics = {"topic1"})
//    public void onMessage1(ConsumerRecord<?, ?> record) {
//        // 消费的哪个topic、partition的消息,打印出消息内容
//        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
//    }
}
