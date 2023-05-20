package com.kims.community.users.controller;

import com.kims.community.users.model.dto.UsersResponse;
import com.kims.community.users.model.form.UsersForm;
import com.kims.community.users.model.form.UsersLoginForm;
import com.kims.community.users.service.UsersService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;


    /**
     * 회원 가입
     * @param usersForm usersForm
     * @return UsersDto
     */
    @PostMapping("/signup")
    public ResponseEntity<UsersResponse> addUsers(@RequestBody @Valid UsersForm usersForm) {
        return ResponseEntity.ok(usersService.addUsers(usersForm));
    }

    /**
     * 로그인
     * @param usersLoginForm 로그인 폼
     * @return String
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signinUsers(@RequestBody @Valid UsersLoginForm usersLoginForm) {
        return ResponseEntity.ok(usersService.loginUsers(usersLoginForm));
    }
}
