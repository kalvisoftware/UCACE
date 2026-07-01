package com.ucace.api.service;

import java.util.List;
import com.ucace.api.entity.User;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    String deleteUser(Long id);

}
