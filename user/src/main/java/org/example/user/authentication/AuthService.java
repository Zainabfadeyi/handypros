package org.example.user.authentication;

import jakarta.validation.Valid;
import org.example.user.authentication.dtos.AuthRequest;
import org.example.user.authentication.dtos.RegisterRequest;
import org.example.user.user.dto.ApiResponse;

public interface AuthService {
    ApiResponse signup (@Valid RegisterRequest registerRequest);

    ApiResponse authenticateAndGetToken(AuthRequest authRequest);
}
