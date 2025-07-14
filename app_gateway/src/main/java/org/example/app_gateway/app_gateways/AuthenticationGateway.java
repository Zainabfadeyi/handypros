package org.example.app_gateway.app_gateways;

//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
           @Valid @RequestBody AuthRequest authRequest
    ){
        return ResponseEntity.ok(
                authService.authenticateAndGetToken(authRequest)
        );
    }

}
