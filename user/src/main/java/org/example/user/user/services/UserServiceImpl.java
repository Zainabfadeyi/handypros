package org.example.user.user.services;

import lombok.RequiredArgsConstructor;
import org.example.user.user.enums.Media;
import org.example.user.user.models.User;
import org.example.user.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;

    @Override
    public void validateEmail(String emailAddress, Media media) {
        if (emailAddress == null || emailAddress .isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!emailAddress .matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        Optional<User> existingUser = userRepository.findByEmail(emailAddress );
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already in use");
        }


    }

    @Override
    public void sendVerificationMail(User user) {

    }

}
