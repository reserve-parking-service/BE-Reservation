package com.icebear2n2.reservationv2.security.auth.controller;

import com.icebear2n2.reservationv2.domain.request.AuthenticationRequest;
import com.icebear2n2.reservationv2.domain.request.RegisterRequest;
import com.icebear2n2.reservationv2.domain.response.AuthenticationResponse;
import com.icebear2n2.reservationv2.security.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public AuthenticationResponse register(@RequestBody RegisterRequest request) {
    return service.register(request);
  }
  @PostMapping("/authenticate")
  public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
    return service.authenticate(request);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
