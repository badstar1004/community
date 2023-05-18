package com.kims.community.users.controller;

import com.kims.community.users.model.dto.UsersResponse;
import com.kims.community.users.model.form.UsersForm;
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
    @PostMapping("/logup")
    public ResponseEntity<UsersResponse> addUsers(@RequestBody @Valid UsersForm usersForm) {
        return ResponseEntity.ok(usersService.addUsers(usersForm));
    }
}
