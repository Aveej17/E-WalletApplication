//package com.Jeeva.NotificationService.consumer;
//
//import com.Jeeva.Utils.CommonConstants;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.jose4j.json.internal.json_simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TxnInitiatedConsumer {
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private JavaMailSender sender;
//
//    @KafkaListener(topics = CommonConstants.TXN_INITIATED_TOPIC, groupId = "notification-group")
//    public void sendNotification(String msg) throws JsonProcessingException {
//        JSONObject obj = objectMapper.readValue(msg, JSONObject.class);
//        String amount = (String) obj.get(CommonConstants.TXN_INITIATED_TOPIC_AMOUNT);
//        String email = (String) obj.get(CommonConstants.TXN_INITIATED_TOPIC_SENDER);
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("Wallet@gmail.com");
//        message.setTo(email);
//        message.setText("Transaction is initiated with amount "+amount);
//        message.setSubject("Initiated Transaction");
//        sender.send(message);
//    }
//}
