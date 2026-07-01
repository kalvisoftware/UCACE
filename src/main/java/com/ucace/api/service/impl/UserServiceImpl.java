package com.ucace.api.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ucace.api.entity.User;
import com.ucace.api.repository.UserRepository;
import com.ucace.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            existingUser.setMobileNo(user.getMobileNo());
            existingUser.setStatus(user.getStatus());
            existingUser.setCreatedDate(user.getCreatedDate());
            existingUser.setUpdatedDate(user.getUpdatedDate());
        }
        return existingUser;
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }
}
