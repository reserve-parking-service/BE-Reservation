package com.icebear2n2.reservationv2.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @Schema(description = "액세스 토큰")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "리프레시 토큰")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
