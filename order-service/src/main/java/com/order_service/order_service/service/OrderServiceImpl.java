package com.order_service.order_service.service;

import com.order_service.order_service.dto.OrderEvent;
import com.order_service.order_service.dto.OrderInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements  OrderService {
    @Autowired
    private StreamBridge streamBridge;

    @Override
    public ResponseEntity<?> createOrder(OrderInformation orderInformation) {
        sendNotifitcation(orderInformation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed !!");
    }

    void sendNotifitcation(OrderInformation orderInformation) {
        try {
            OrderEvent orderEvent = new OrderEvent(orderInformation.getOrderId(), orderInformation.getUserId(), "CREATED");
            Message<OrderEvent> message = MessageBuilder.withPayload(orderEvent).build();
            boolean sent = streamBridge.send("orderCreated-out-0", message);
            if (sent) {
                System.out.println("Event Published successfully !!");
            } else {
                System.out.println("Error while publishing the event !!");
            }
        } catch (Exception exception){
            System.out.println("Error while publishing message , exception is : "+exception);
        }
    }
}
