package com.icebear2n2.reservationv2.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
