package com.order_service.order_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderInformation {

    private String orderId;
    private  String email;
    private  String userId;
    private  String userPhone;
    private  boolean orderPaymentStatus=false;
    private  boolean orderStatus=false;
    private  String courseId;
}