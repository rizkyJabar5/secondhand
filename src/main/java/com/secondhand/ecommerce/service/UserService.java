package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.User;

public interface UserService {
    User addUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
