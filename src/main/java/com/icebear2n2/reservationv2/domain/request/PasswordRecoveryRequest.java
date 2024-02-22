package com.icebear2n2.reservationv2.domain.request;

import lombok.*;

@Data
public class PasswordRecoveryRequest {
    private Long userId;
    private String code;
    private String newPassword;
    private String confirmNewPassword;
}