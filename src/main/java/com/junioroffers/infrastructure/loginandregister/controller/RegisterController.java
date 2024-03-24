package com.junioroffers.infrastructure.loginandregister.controller;

import com.junioroffers.domain.loginandregister.LoginAndRegisterFacade;

import com.junioroffers.infrastructure.loginandregister.controller.dto.RegisterUserDto;
import com.junioroffers.infrastructure.loginandregister.controller.dto.RegistrationResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerUserDto.password());
        RegistrationResultDto registerResult = loginAndRegisterFacade.register(
                new RegisterUserDto(registerUserDto.username(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }
}
