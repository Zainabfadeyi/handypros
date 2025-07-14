package org.example.user.user.services;

import org.example.user.user.enums.Media;
import org.springframework.http.ResponseEntity;

public interface UserService  {

    ResponseEntity<String> validateEmail(String email, Media media);
}
