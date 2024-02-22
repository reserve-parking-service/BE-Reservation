package com.icebear2n2.reservationv2.security.auth.service;


import com.icebear2n2.reservationv2.domain.repository.AuthCodeRepository;
import com.icebear2n2.reservationv2.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.reservationv2.domain.request.PhoneRequest;
import com.icebear2n2.reservationv2.domain.entity.AuthCode;
import com.icebear2n2.reservationv2.domain.entity.User;
import com.icebear2n2.reservationv2.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthCodeService {

    //    TODO: 휴대폰번호 인증코드 생성, 발송, 확인 기능
    private static final String AUTH_MESSAGE_FORMAT = "[TECH HEAVEN] 인증번호: %s";

    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;
    private final Random random;
    private DefaultMessageService defaultMessageService;

    @Value("${nurigo.api.key}")
    private String nurigoApiKey;

    @Value("${nurigo.api.secret}")
    private String nurigoApiSecret;

    @PostConstruct
    public void initDefaultMessageService() {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(nurigoApiKey, nurigoApiSecret, "https://api.coolsms.co.kr");
    }

    public void sendAuthCode(PhoneRequest phoneRequest) {
        String code = generateCode(phoneRequest.getUserId());

        Message message = new Message();
        message.setFrom(phoneRequest.getPhone());
        message.setTo(phoneRequest.getPhone());
        message.setText(String.format(AUTH_MESSAGE_FORMAT, code));

        try {
            this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));

            saveAuthCode(phoneRequest.getPhone(), code);
        } catch (Exception e) {
            log.error("FAILED TO SEND AUTH CODE TO PHONE: {}", phoneRequest.getPhone(), e);
            throw new RuntimeException("Failed send auth code.");
        }
    }

    private void saveAuthCode(String phone, String code) {
        AuthCode authCode = AuthCode.builder()
                .phone(phone)
                .code(code)
                .expirationTime(Timestamp.valueOf(LocalDateTime.now().plus(5, ChronoUnit.MINUTES)))
                .build();

        authCodeRepository.save(authCode);
    }

    private String generateCode(Long userId) {
        int code;
        do {
            code = random.nextInt(900000) + 100000;
        } while (isCodeIssuedToUser(userId, String.valueOf(code)));
        return String.valueOf(code);
    }

    private boolean isCodeIssuedToUser(Long userId, String code) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        return authCodeRepository.findByUserAndCode(user, code) != null;
    }


    public void checkAuthCode(CheckAuthCodeRequest checkAuthCodeRequest) {
        // Step 1: 전화번호와 코드로 인증코드가 존재하는지 확인
        AuthCode authCode = getValidAuthCode(checkAuthCodeRequest.getPhone(), checkAuthCodeRequest.getCode());

        // Step 2: 존재한다면, 해당 유저 아이디를 가진 유저를 찾기
        User user = findByUserId(checkAuthCodeRequest.getUserId());

        // Step 3: 유저 전화번호 업데이트
        updateUserPhone(user, checkAuthCodeRequest.getPhone());

        // Step 4: authCode 유저 정보 저장
        authCode.setUser(user);
        authCodeRepository.save(authCode);

        // Step 5: 인증코드 완료 날짜 작성
        completedSaveAuthCode(authCode);

    }

    AuthCode getValidAuthCode(String phone, String code) {
        AuthCode authCode = authCodeRepository.findByPhoneAndCode(phone, code);
        if (authCode != null && authCode.getExpirationTime().toLocalDateTime().isBefore(LocalDateTime.now())) {
            authCodeRepository.delete(authCode);
            throw new RuntimeException("Expired auth code.");
        }

        if (authCode == null) {
            throw new RuntimeException("Invalid credential.");
        }

        return authCode;
    }

    private User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
    }

    private void updateUserPhone(User user, String phone) {
        user.setUserPhone(phone);
        userRepository.save(user);


    }

    //    TODO: completedAt 으로 넣기
    void completedSaveAuthCode(AuthCode authCode) {
        authCode.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        authCodeRepository.save(authCode);
    }


    // 잔액 조회
    public Balance getBalance() {
        return this.defaultMessageService.getBalance();
    }

}
