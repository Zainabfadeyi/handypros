package org.example.user.authentication.dtos;

import lombok.*;
import org.example.user.user.enums.Media;


@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Media media;

}
