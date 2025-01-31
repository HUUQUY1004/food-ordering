package com.huuquy.service;

import com.huuquy.config.JwtProvider;
import com.huuquy.model.User;
import com.huuquy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserSeviceImp implements  UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        System.out.println("JWT User Service:" + jwt);
        return findUserByEmail( jwtProvider.getEmailFromJwtToken(jwt));
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        System.out.println("Find Email : " + email);
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw  new Exception("User not found");
        }

        return user;
    }
}
