package com.icebear2n2.reservationv2.domain.request;

import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class UpdatePaymentStatusRequest {
    private PaymentStatus paymentStatus;

    public Optional<PaymentStatus> paymentStatus() { return Optional.ofNullable(this.paymentStatus); }

    public boolean isCanceled() { return paymentStatus == PaymentStatus.PAYMENT_CANCELLED; }
}
