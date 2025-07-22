package org.example.app_gateway.app_gateways;

//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.user.authentication.AuthService;
import org.example.user.authentication.dtos.AuthRequest;
import org.example.user.authentication.dtos.RegisterRequest;
import org.example.user.user.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/v1/auth")
@Tag(name="Auth Controller")
public class AuthenticationGateway {
    private final  AuthService authService;


    @PostMapping(value = "/register" )
    public ResponseEntity<ApiResponse> signup (
            @RequestBody final RegisterRequest registerRequest
    ){
        return  new ResponseEntity<>(authService.signup(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping(value ="/login" )
    public ResponseEntity<ApiResponse>login(
            @RequestBody AuthRequest authRequest
    ){
        log.info("Login endpoint hit with: {}", authRequest.getEmail());
        System.out.println("it reach this place");
        return ResponseEntity.ok(

                authService.authenticateAndGetToken(authRequest)
        );

    }

}
