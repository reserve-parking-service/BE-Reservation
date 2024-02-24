package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.Role;
import com.icebear2n2.reservationv2.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "사용자명")
    private String username;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이메일 주소")
    private String email;

    @Schema(description = "사용자 전화번호")
    private String userPhone;

    @Schema(description = "역할")
    private Enum<Role> role;

    @Schema(description = "생성일시")
    private String createdAt;

    @Schema(description = "수정일시")
    private String updatedAt;

    @Schema(description = "삭제일시")
    private String deletedAt;


    public UserResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPhone = user.getUserPhone();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt().toString();
        this.updatedAt = user.getUpdatedAt().toString();
        this.deletedAt = user.getDeletedAt() != null ? user.getDeletedAt().toString() : null;
    }
}
