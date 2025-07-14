package org.example.user.user.services;

import lombok.RequiredArgsConstructor;
import org.example.user.user.enums.Media;
import org.example.user.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<String> validateEmail(String email, Media media) {
        System.out.println("Checking existence of email: " + email);
        if(userRepository.existsByEmailAddress(email)){
            System.out.println("Exists: " + email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "User exists with this email address or user identifier"
            );
        } throw new RuntimeException("Looks like you’ve already signed up with this email! Try logging in instead, or use a different email to create a new account");
    }
}
