package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @Schema(description = "현재 비밀번호")
    private String currentPassword;

    @Schema(description = "새로운 비밀번호")
    private String newPassword;

    @Schema(description = "새로운 비밀번호 확인")
    private String confirmationPassword;
}
