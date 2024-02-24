package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Repositories.UserRepository;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Not found by Username"));
            }
        };
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get() : null;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        var user = userRepository.findById(id);
        return user.orElse(null);
    }


}
