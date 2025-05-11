package com.order_service.order_service.Controller;


import com.order_service.order_service.dto.OrderInformation;
import com.order_service.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

@Autowired
    OrderService orderService ;
@PostMapping("/")
    ResponseEntity<?> placeOrder(@RequestBody OrderInformation orderInformation){
     return  orderService.createOrder(orderInformation);

}


}

