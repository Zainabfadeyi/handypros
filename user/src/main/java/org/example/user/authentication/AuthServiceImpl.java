package org.example.user.authentication;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.authentication.dtos.AuthRequest;
import org.example.user.authentication.dtos.AuthResponse;
import org.example.user.authentication.dtos.RegisterRequest;
import org.example.user.authentication.dtos.UserDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

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
                .createdAt(LocalDate.now())
                .build();

        User savedUser = userRepository.save(user);
        try {
            if(savedUser.isEnabled()){
                userService.sendVerificationMail(user);
            }
            return  new ApiResponse(201,"Successful! Please check your email to complete registration", registerRequest );

        }catch (RuntimeException e){
            throw new RuntimeException("Registration failed");
        }

    }

    @Override
    public ApiResponse authenticateAndGetToken(AuthRequest request) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user =userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("user not found"));

        var jwtToken= jwtService.generateToken(user);
        var userDto= UserDto.builder()
                .id(user.getId())
                .isEnabled(user.isEnabled())
                .emailAddress(user.getEmailAddress())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .media(user.getMedia().toString())
                .build();
       var authResponse= AuthResponse.builder()
               .token(jwtToken)
               .userDto(userDto)
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
