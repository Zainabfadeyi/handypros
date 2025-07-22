package org.example.user.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.user.models.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {
    private  String token;
    private UserDto userDto;
}
