package com.notification_service.notification.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class AcknowledgementEvent {

    private String orderId ;
    private String message ;
}
