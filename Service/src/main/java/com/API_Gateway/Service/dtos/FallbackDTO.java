package com.API_Gateway.Service.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FallbackDTO {
 private String message ;
 private boolean active ;


}
