package com.junioroffers.domain.loginandregister;

import com.junioroffers.infrastructure.loginandregister.controller.dto.RegisterUserDto;

interface LoginAndRegisterMapper {

    static User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
    }
}
