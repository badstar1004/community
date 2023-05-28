package com.kims.community.users.service;

import static com.kims.community.exception.business.ErrorCode.ALREADY_REGISTER_USER;
import static com.kims.community.exception.business.ErrorCode.DUPLICATE_NICKNAME;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_USER;
import static com.kims.community.exception.business.ErrorCode.WRONG_PASSWORD;

import com.kims.community.config.JwtAuthenticationProvider;
import com.kims.community.exception.business.BusinessException;
import com.kims.community.users.entity.Users;
import com.kims.community.users.model.dto.UsersResponse;
import com.kims.community.users.model.form.UsersForm;
import com.kims.community.users.model.form.UsersLoginForm;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 저장
     * @param usersForm 회원가입 폼
     * @return UsersDto
     */
    @Transactional
    public UsersResponse addUsers(UsersForm usersForm) {

        // 이메일 체크
        if (usersRepository.existsByEmail(usersForm.getEmail())) {
            throw new BusinessException(ALREADY_REGISTER_USER);
        }

        // 닉네임 체크
        if (usersRepository.existsByNickName(usersForm.getNickName())) {
            throw new BusinessException(DUPLICATE_NICKNAME);
        }

        // 비밀번호 암호화
        Users user = Users.from(usersForm, passwordEncoder.encode(usersForm.getPassword()));

        // 저장
        usersRepository.save(user);

        return UsersResponse.builder()
            .message("회원가입이 완료되었습니다.")
            .usersDto(UsersResponse.UsersDto.from(user))
            .build();
    }

    /**
     * 로그인
     * @param usersLoginForm 로그인 폼
     * @return String
     */
    public String loginUsers(UsersLoginForm usersLoginForm) {

        Users user = usersRepository.findByEmail(usersLoginForm.getEmail())
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // 비밀번호 확인
        if (!passwordEncoder.matches(usersLoginForm.getPassword(), user.getPassword())) {
            throw new BusinessException(WRONG_PASSWORD);
        }

        return jwtAuthenticationProvider.generateToken(user);
    }
}
