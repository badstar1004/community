package com.kims.community.users.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kims.community.exception.custom.CustomException;
import com.kims.community.exception.custom.ErrorCode;
import com.kims.community.users.entity.Users;
import com.kims.community.users.model.form.UsersForm;
import com.kims.community.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    @Captor
    private ArgumentCaptor<Users> usersArgumentCaptor;

    private UsersForm validUsersForm;


    @BeforeEach
    public void setup() {
        validUsersForm = UsersForm.builder()
            .email("aaa@aaa.aaa")
            .name("기믄정")
            .nickName("이너프")
            .password("password")
            .build();
    }

    @Test
    @DisplayName("유저 저장")
    void addUsersTest() {
        // given when
        // 저장
        usersService.addUsers(validUsersForm);

        // then
        // UsersRepository 의 save() 메서드가 호출되었는지 검증
        verify(usersRepository).save(usersArgumentCaptor.capture());

        Users capture = usersArgumentCaptor.getValue();
        assertEquals("aaa@aaa.aaa", capture.getEmail());
        assertEquals("기믄정", capture.getName());
        assertEquals("이너프", capture.getNickName());
        assertEquals("password", capture.getPassword());
    }

    @Test
    @DisplayName("중복된 이메일이 존재(ALREADY_REGISTER_USER)")
    void throwException__when__already_register_user() {

        // 이메일이 이미 존재하는 경우를 가정하여 Mock 데이터 설정
        when(usersRepository.existsByEmail(validUsersForm.getEmail())).thenReturn(true);

        // 예외가 발생해야 함
        assertThatThrownBy(
            () -> usersService.addUsers(validUsersForm))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ALREADY_REGISTER_USER);
    }

    @Test
    @DisplayName("중복된 닉네임이 존재(DUPLICATE_NICKNAME)")
    void throwException__when__duplicate_nickname() {

        // 이메일이 이미 존재하는 경우를 가정하여 Mock 데이터 설정
        when(usersRepository.existsByNickName(validUsersForm.getNickName())).thenReturn(true);

        // 예외가 발생해야 함
        assertThatThrownBy(
            () -> usersService.addUsers(validUsersForm))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATE_NICKNAME);
    }

}