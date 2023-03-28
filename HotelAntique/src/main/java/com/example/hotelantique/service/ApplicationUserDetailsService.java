package com.example.hotelantique.service;

import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;



    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " is not found!"));
    }

    private UserDetails map(UserEntity user){
       return new User(
                user.getUsername(),
                user.getPassword(),
                extractAuthorities(user)
        );
    }

    private List<GrantedAuthority> extractAuthorities(UserEntity user){
        return user
                .getRoles()
                .stream()
                .map(this::mapRole)
                .collect(Collectors.toList());
    }

    private GrantedAuthority mapRole(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }

}
