package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoginAndRegisterFacadeTest {
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new InMemoryUserRepositoryTestImpl()
    );

    @Test
    public void should_register_user_with_given_username() {
        //given
        RegisterUserDto SampleRegisterUserDto = new RegisterUserDto("username", "password");
        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(SampleRegisterUserDto);
        //then
        assertAll(
                () -> assertThat(registrationResultDto.isCreated()).isTrue(),
                () -> assertThat(registrationResultDto.username()).isEqualTo(SampleRegisterUserDto.username())
        );
    }

    @Test
    public void should_find_user_by_username_if_user_exists() {
        //given
        RegisterUserDto SampleRegisterUserDto = new RegisterUserDto("username", "password");
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(SampleRegisterUserDto);
        //when
        UserDto foundUser = loginAndRegisterFacade.findByUsername("username");
        //then
        UserDto expectedUser = new UserDto(registrationResultDto.userId(), "username", "password");
        assertThat(foundUser).isEqualTo(expectedUser);
    }

    @Test
    public void should_throw_exception_if_user_does_not_exist() {
        //given
        String sampleUsername = "username";
        //then
        assertThatThrownBy(() -> loginAndRegisterFacade.findByUsername("username"))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("User with username '%s' not found".formatted(sampleUsername));
    }
}