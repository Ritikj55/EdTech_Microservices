package com.microservices.course.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomMessage {
String message ;
boolean isSuccess ;
}
