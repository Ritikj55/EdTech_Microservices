package com.order_service.order_service.service;

import com.order_service.order_service.dto.AcknowledgementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AcknowledgementListener {

    Logger LOG = LoggerFactory.getLogger(AcknowledgementListener.class);

    @Bean
    public Consumer<AcknowledgementEvent> receiveOrderAcknowledgement(){
        return acknowledgementEventMessage -> {
            AcknowledgementEvent acknowledgementEvent =  acknowledgementEventMessage;
            if(acknowledgementEvent.getMessage().equals("Order received !!")){
              LOG.info("received acknowledgement for orderId {} ",acknowledgementEvent.getOrderId());
            }
            else{
                LOG.info("Order was not received");
                LOG.info("The order was : ");
                LOG.info(acknowledgementEvent.toString());
            }
        };}


}
