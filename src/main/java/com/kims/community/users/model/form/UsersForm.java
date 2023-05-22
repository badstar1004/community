package com.kims.community.users.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class UsersForm {

    /**
     * 이메일
     */
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    /**
     * 이름
     */
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 2자이상 10자이하입니다.")
    private String name;

    /**
     * 닉네임
     */
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 20, message = "닉네임은 2자이상 20자이하입니다.")
    private String nickName;

    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 12, message = "비밀번호는 12자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$])[a-zA-Z\\d!@#$]+$",
        message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!@#$)를 포함해야 합니다.")
    private String password;

}
