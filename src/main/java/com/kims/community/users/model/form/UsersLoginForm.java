package com.kims.community.users.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsersLoginForm {

    /**
     * 이메일
     */
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
