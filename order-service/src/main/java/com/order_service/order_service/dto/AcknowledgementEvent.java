package com.order_service.order_service.dto;


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
