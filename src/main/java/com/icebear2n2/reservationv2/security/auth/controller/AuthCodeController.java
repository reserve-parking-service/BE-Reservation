package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.reservationv2.domain.request.PhoneRequest;
import com.icebear2n2.reservationv2.security.auth.service.AuthCodeService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Balance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verification")
public class AuthCodeController {

    private final AuthCodeService authCodeService;

    @PostMapping
    public String sendAuthCode(@RequestBody PhoneRequest phoneRequest) {
        authCodeService.sendAuthCode(phoneRequest);
        return "SEND AUTH CODE SUCCESSFULLY.";
    }

    @PostMapping("/check")
    public String checkAuthCode(@RequestBody CheckAuthCodeRequest checkAuthCodeRequest) {
        authCodeService.checkAuthCode(checkAuthCodeRequest);
        return "CHECK AUTH CODE SUCCESSFULLY.";
    }

    @GetMapping("/balance")
    public Balance getBalance() {
        return authCodeService.getBalance();
    }
}
