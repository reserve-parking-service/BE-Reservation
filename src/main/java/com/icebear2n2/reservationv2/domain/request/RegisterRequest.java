package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import com.icebear2n2.reservationv2.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(description = "사용자명")
    private String username;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이메일 주소")
    private String email;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "역할")
    private Role role;
}
