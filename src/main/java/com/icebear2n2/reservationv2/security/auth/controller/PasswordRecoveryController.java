package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.reservationv2.security.auth.service.PasswordRecoveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/password/recovery")
@Tag(name = "Password Recovery", description = "비밀번호 재설정 API")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/{userId}")
    @Operation(summary = "비밀번호 재설정 코드 요청", description = "사용자에게 비밀번호 재설정 코드를 전송합니다.")
    public ResponseEntity<String> requestCode(
            @PathVariable Long userId
    ) {
        passwordRecoveryService.requestPasswordRecovery(userId);
        return new ResponseEntity<>("SEND AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "비밀번호 재설정", description = "사용자의 비밀번호를 재설정합니다.")
    public ResponseEntity<String> resetPassword(
            @RequestBody PasswordRecoveryRequest passwordRecoveryRequest
    ) {
        passwordRecoveryService.verifyAuthCodeAndResetPassword(passwordRecoveryRequest);
        return new ResponseEntity<>("Password reset was successful.", HttpStatus.OK);
    }
}
