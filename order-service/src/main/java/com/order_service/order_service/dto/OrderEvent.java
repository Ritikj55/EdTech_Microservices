package com.order_service.order_service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent {
    private String orderId;
    private  String userId;
    private String status;

}
