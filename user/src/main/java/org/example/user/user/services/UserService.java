package org.example.user.user.services;

import org.example.user.user.enums.Media;
import org.example.user.user.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService  {

    void validateEmail(String email, Media media);

    void sendVerificationMail(User user);

}
