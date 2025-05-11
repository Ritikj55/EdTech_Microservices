package com.order_service.order_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.order_service.order_service.dto.OrderInformation ;
public interface OrderService {

    ResponseEntity<?> createOrder(@RequestBody OrderInformation orderInformation);

}
