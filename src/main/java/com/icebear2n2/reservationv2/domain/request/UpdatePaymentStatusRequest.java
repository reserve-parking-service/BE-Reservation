package com.icebear2n2.reservationv2.domain.request;

import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {
    private PaymentStatus paymentStatus;
}
