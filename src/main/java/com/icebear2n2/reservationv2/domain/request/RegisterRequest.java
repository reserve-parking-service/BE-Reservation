package com.icebear2n2.reservationv2.domain.request;

import com.icebear2n2.reservationv2.domain.entity.Role;
import lombok.*;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String nickname;
    private String email;
    private String password;
    private Role role;
}
