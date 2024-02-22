package com.icebear2n2.reservationv2.domain.repository;


import com.icebear2n2.reservationv2.domain.entity.AuthCode;
import com.icebear2n2.reservationv2.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    AuthCode findByPhoneAndCode(String phone, String code);

    AuthCode findByUserAndCode(User user, String code);

    @Query("SELECT a FROM AuthCode a WHERE a.phone = ?1 ORDER BY a.completedAt DESC limit 1")
    AuthCode findByPhone(String phone);
}
