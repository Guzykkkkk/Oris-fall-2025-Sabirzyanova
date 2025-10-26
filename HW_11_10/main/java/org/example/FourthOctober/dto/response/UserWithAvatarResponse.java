package org.example.FourthOctober.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserWithAvatarResponse {
        private Long id;
        private String username;
        private String email;
        private boolean hasAvatar;
        private String avatarUrl;

}
