package com.kims.community.users.service;

import static com.kims.community.exception.custom.ErrorCode.ALREADY_REGISTER_USER;
import static com.kims.community.exception.custom.ErrorCode.DUPLICATE_NICKNAME;

import com.kims.community.exception.custom.CustomException;
import com.kims.community.users.entity.Users;
import com.kims.community.users.model.dto.UsersDto;
import com.kims.community.users.model.form.UsersForm;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;


    /**
     * 유저 저장
     * @param usersForm userForm
     * @return  UsersDto
     */
    @Transactional
    public UsersDto addUsers(UsersForm usersForm) {

        // 이메일 체크
        if (usersRepository.existsByEmail(usersForm.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        }

        // 닉네임 체크
        if (usersRepository.existsByNickName(usersForm.getNickName())) {
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        Users users = Users.builder()
            .email(usersForm.getEmail())
            .name(usersForm.getName())
            .nickName(usersForm.getNickName())
            .password(usersForm.getPassword())
            .build();
        
        // 저장
        usersRepository.save(users);

        return UsersDto.of(users);
    }

}
