package org.example.user.authentication;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.user.authentication.dtos.AuthRequest;
import org.example.user.authentication.dtos.RegisterRequest;
import org.example.user.user.dto.ApiResponse;
import org.example.user.user.enums.Role;
import org.example.user.user.models.User;
import org.example.user.user.repository.UserRepository;
import org.example.user.user.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public ApiResponse signup(RegisterRequest registerRequest) {
        String emailAddress= preRegistrationRequest(registerRequest);
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .emailAddress(emailAddress)
                .media(registerRequest.getMedia())
                .roles(Role.USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return  new ApiResponse(201,"Successful! Please check your email to complete registration", registerRequest );

    }

    @Override
    public ApiResponse authenticateAndGetToken(AuthRequest authRequest) {
        return null;
    }

    @NotNull
    private String preRegistrationRequest(RegisterRequest registerRequest){
        String emailAddress= trimInput(registerRequest.getEmail().toLowerCase());
        userService.validateEmail(emailAddress, registerRequest.getMedia());
        return emailAddress;
    }
    public static String trimInput(String input){
        return input == null? null:input.trim();
    }


}
