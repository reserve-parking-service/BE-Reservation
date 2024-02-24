package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.reservationv2.domain.request.PhoneRequest;
import com.icebear2n2.reservationv2.security.auth.service.AuthCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Balance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verification")
@Tag(name = "Auth Code", description = "본인 인증 API")
public class AuthCodeController {

    private final AuthCodeService authCodeService;

    @PostMapping
    @Operation(summary = "인증 코드 전송", description = "전화번호로 인증 코드를 전송합니다.")
    public ResponseEntity<String> sendAuthCode(
            @RequestBody PhoneRequest phoneRequest
    ) {
        authCodeService.sendAuthCode(phoneRequest);
        return new ResponseEntity<>("SEND AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @PostMapping("/check")
    @Operation(summary = "인증 코드 확인", description = "전송된 인증 코드를 확인합니다.")
    public ResponseEntity<String> checkAuthCode(
            @RequestBody CheckAuthCodeRequest checkAuthCodeRequest
    ) {
        authCodeService.checkAuthCode(checkAuthCodeRequest);
        return new ResponseEntity<>("CHECK AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @GetMapping("/balance")
    @Operation(summary = "잔액 조회", description = "SMS 발송 잔액을 조회합니다.")
    public ResponseEntity<Balance> getBalance() {
        Balance balance = authCodeService.getBalance();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
