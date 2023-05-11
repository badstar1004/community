package com.kims.community.users.model.dto;

import com.kims.community.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersDto {

    /**
     * 이메일
     */
    private String email;

    /**
     * 이름
     */
    private String name;

    /**
     * 닉네임
     */
    private String nickName;


    /**
     * Users -> UserResponse
     *
     * @param users Users
     * @return UserResponse
     */
    public static UsersDto of(Users users) {
        return UsersDto.builder()
            .email(users.getEmail())
            .name(users.getName())
            .nickName(users.getNickName())
            .build();
    }

}