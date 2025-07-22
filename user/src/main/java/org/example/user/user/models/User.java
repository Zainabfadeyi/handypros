package org.example.user.user.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.user.enums.Media;
import org.example.user.user.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private  String password;
    @Enumerated(EnumType.STRING)
    private Media media;
    private String dob;
    private String profileImage;
    private boolean isVerified;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDateTime deactivatedAt;
    @OneToOne(targetEntity = UserAddress.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAddress userAddress;
    @OneToOne(targetEntity = UserDocument.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserDocument userDocument;
    private Role roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roles.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
