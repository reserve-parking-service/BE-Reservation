package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.Role;
import com.icebear2n2.reservationv2.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        private Long userId;
        private String username;
        private String nickname;
        private String email;
        private String userPhone;
        private Enum<Role> role;
        private String createdAt;
        private String updatedAt;
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