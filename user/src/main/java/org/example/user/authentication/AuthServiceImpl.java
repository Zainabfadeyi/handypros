package org.example.user.authentication;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.authentication.dtos.AuthRequest;
import org.example.user.authentication.dtos.AuthResponse;
import org.example.user.authentication.dtos.RegisterRequest;
import org.example.user.authentication.security.JwtService;
import org.example.user.user.dto.ApiResponse;
import org.example.user.user.enums.Role;
import org.example.user.user.models.User;
import org.example.user.user.repository.UserRepository;
import org.example.user.user.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements  AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private  final JwtService jwtService;

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
    public ApiResponse authenticateAndGetToken(AuthRequest request) {
        System.out.println("e no reach here ooo");
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        log.info("this is the auth request {}", request);

        var user =userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("user not found"));

        log.info("this is this user {}", user);

        var jwtToken= jwtService.generateToken(user);
        System.out.println("i dont understand oo");
       var authResponse= AuthResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
        return ApiResponse.builder()
                .data(authResponse)
                .statusCode(200)
                .message("user successfully logged in")
                .build();
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
