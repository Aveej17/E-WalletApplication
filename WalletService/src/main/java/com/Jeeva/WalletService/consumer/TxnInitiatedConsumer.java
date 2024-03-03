package com.Jeeva.WalletService.consumer;
import com.Jeeva.Utils.CommonConstants;
import com.Jeeva.WalletService.model.Wallet;
import com.Jeeva.WalletService.repository.WalletRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TxnInitiatedConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @KafkaListener(topics = CommonConstants.TXN_INITIATED_TOPIC, groupId = "wallet-group")
    public void updateWallet(String msg) throws JsonProcessingException {
        JSONObject jsonObject = objectMapper.readValue(msg, JSONObject.class);
        String sender = (String) jsonObject.get(CommonConstants.TXN_INITIATED_TOPIC_SENDER);
        String receiver = (String) jsonObject.get(CommonConstants.TXN_INITIATED_TOPIC_RECEIVER);

        Object value = jsonObject.get(CommonConstants.TXN_INITIATED_TOPIC_AMOUNT);

        Double amount = 0.0;
// Check if the value is not null and is of type Number
        if (value != null) {
            if (value instanceof Number) {
                // If value is already a number, cast it to Double
                amount = ((Number) value).doubleValue();
            } else if (value instanceof String) {
                // If value is a string, try parsing it to Double
                try {
                    amount = Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    // Handle the case where the string cannot be parsed to a number
                    System.out.println("Error parsing amount: " + e.getMessage());
                }
            }
        }


//        Double amount = (Double) jsonObject.get(CommonConstants.TXN_INITIATED_TOPIC_AMOUNT);
        String txnId = (String) jsonObject.get(CommonConstants.TXN_INITIATED_TOPIC_TXNID);

        Wallet senderWallet = walletRepository.findByContact(sender);
        Wallet receiverWallet = walletRepository.findByContact(receiver);

        String message = "Txn is in initiated state";
        String status = "Pending";

        if (senderWallet == null) {
            message = "sender wallet is not associated with us";
            status = "Failed";
        } else if (receiverWallet == null) {
            message = "receiver wallet is not associated with us";
            status = "Failed";
        } else if (amount > senderWallet.getBalance()) {
            message = "sender wallet amount is lesser than the amount for which he wants to make txn for";
            status = "Failed";
        } else {
            walletRepository.updateWallet(sender, -amount);
            walletRepository.updateWallet(receiver, amount);
            message = "txn is successful";
            status = "SUCCESS";

        }

        // Kafka published

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put(CommonConstants.TXN_UPDATED_TOPIC_MESSAGE, message);
        jsonObject1.put(CommonConstants.TXN_UPDATED_TOPIC_STATUS, status);
        jsonObject1.put(CommonConstants.TXN_UPDATED_TOPIC_TXNID, txnId);
        kafkaTemplate.send(CommonConstants.TXN_UPDATED_TOPIC, jsonObject1);
    }
}
