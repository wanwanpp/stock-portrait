package com.wp.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaProducerConfig.class)
public class KafkaApp {

    public static String TOPIC = "hello";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void sendMessage() throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(TOPIC, "this is a test data");
        SendResult<String, String> result = sendResult.get();
        RecordMetadata recordMetadata = result.getRecordMetadata();
        System.out.println(recordMetadata.toString());

        Thread.sleep(20000);
    }
}
