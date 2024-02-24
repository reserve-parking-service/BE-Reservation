package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
public class PasswordRecoveryRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "코드")
    private String code;

    @Schema(description = "새로운 비밀번호")
    private String newPassword;

    @Schema(description = "새로운 비밀번호 확인")
    private String confirmNewPassword;
}
