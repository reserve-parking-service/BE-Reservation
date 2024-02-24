package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.AuthenticationRequest;
import com.icebear2n2.reservationv2.domain.request.RegisterRequest;
import com.icebear2n2.reservationv2.domain.response.AuthenticationResponse;
import com.icebear2n2.reservationv2.security.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 API")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  @Operation(summary = "인증", description = "사용자를 인증합니다.")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "액세스 토큰 갱신", description = "액세스 토큰을 갱신합니다.")
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }
}
