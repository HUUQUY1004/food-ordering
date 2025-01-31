package com.huuquy.service;

import com.huuquy.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws  Exception ;
    User findUserByEmail(String email) throws  Exception;
}
