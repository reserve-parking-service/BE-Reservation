package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "전화번호")
    private String phone;
}
