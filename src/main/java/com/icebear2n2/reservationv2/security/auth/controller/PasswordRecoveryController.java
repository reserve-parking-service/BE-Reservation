package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.reservationv2.security.auth.service.PasswordRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/password/recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/{userId}")
    public String requestCode(@PathVariable Long userId
    ) {
        passwordRecoveryService.requestPasswordRecovery(userId);
        return "SEND AUTH CODE SUCCESSFULLY.";
    }


    @PutMapping("/update")
    public String resetPassword(
            @RequestBody
            PasswordRecoveryRequest passwordRecoveryRequest) {
        passwordRecoveryService.verifyAuthCodeAndResetPassword(passwordRecoveryRequest);
        return "Password reset was successful.";
    }
}