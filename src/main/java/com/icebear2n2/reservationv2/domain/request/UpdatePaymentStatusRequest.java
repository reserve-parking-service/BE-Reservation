package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {

    @Schema(description = "결제 상태")
    private PaymentStatus paymentStatus;
}
