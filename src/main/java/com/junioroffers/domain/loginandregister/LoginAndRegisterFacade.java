package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import static com.junioroffers.domain.loginandregister.LoginAndRegisterMapper.mapRegisterUserDtoToUser;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {
    private final UserRepository userRepository;
    public RegistrationResultDto register(RegisterUserDto registerUserDto){
        User user = mapRegisterUserDtoToUser(registerUserDto);
        User savedUser = this.userRepository.save(user);
        return new RegistrationResultDto(savedUser.id(), savedUser.username(), true);
    }

    public UserDto findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new BadCredentialsException(
                        "User with username '%s' not found".formatted(username)));
    }
}
