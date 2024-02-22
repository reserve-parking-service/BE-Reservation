package com.icebear2n2.reservationv2.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {
    private Long userId;
    private String phone;
}
