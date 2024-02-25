package com.icebear2n2.reservationv2.domain.request;

import lombok.*;

@Getter
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
}
