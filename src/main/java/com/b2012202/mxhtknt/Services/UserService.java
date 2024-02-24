package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Request.ResponseObject;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    User findUserByUsername(String username);

    User saveUser(User user);
    User findUserById(Long id);
}
