package com.notification_service.notification.files;

import com.notification_service.notification.dto.AcknowledgementEvent;
import com.notification_service.notification.dto.OrderEvent;
import com.notification_service.notification.dto.OrderInformation;
import jdk.jfr.consumer.EventStream;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class NotificationService {
public static Logger LOG = LoggerFactory.getLogger("NotificationService");

@Autowired
private StreamBridge streamBridge ;
@Bean
Function<Message<OrderEvent>,AcknowledgementEvent> orderEventsReceiver() {
    return (orderEventMessage -> {
        OrderEvent orderEvent =  orderEventMessage.getPayload();
        if (orderEvent.getStatus().equals("CREATED")) {
            sendNotification(orderEvent);
          //  sendAcknowledgement(orderEvent);
        }
        else{
            LOG.info("Order do not have created status !!!");
        }
        return  new AcknowledgementEvent(orderEvent.getOrderId(),"Order received !!");
    });
}
void sendNotification(OrderEvent orderEvent){
    LOG.info("Order Event received !!");
    LOG.info(orderEvent.toString());
}
void sendAcknowledgement(OrderEvent orderEvent){
    AcknowledgementEvent acknowledgementEvent  = new AcknowledgementEvent(orderEvent.getOrderId(),"Order received!!");
    Message<AcknowledgementEvent> message  = MessageBuilder.withPayload(acknowledgementEvent).build();
    streamBridge.send("acknowledgement-event-out-0",message);


}
}
