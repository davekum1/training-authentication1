package com.kollaboralabs.auth.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kollaboralabs.auth.config.Assert;
import com.kollaboralabs.auth.domain.UserRequestData;
import com.kollaboralabs.auth.domain.UserResponseData;
import com.kollaboralabs.auth.entity.User;
import com.kollaboralabs.auth.repository.UserRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
    	    List<User> users = userRepository.findAll();
    	    return users;
    }
    
    public User findUserByUuid(UUID uuid) {
        Assert.notNull(uuid, "uuid");
        User user = userRepository.findUserByUuid(uuid);
        Assert.entityNotNull(user, User.class, "uuid", uuid.toString());
        return user;
    }

    public User findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        return user;
    }

    public User findUserByEmailIgnoreCase(String emailAddress) {
        User user = userRepository.findUserByEmailIgnoreCase(emailAddress);
        Assert.entityNotNull(user, User.class, "email", emailAddress);
        return user;
    }

    public User createUser(UserRequestData userRequestData) {
        User user = this.mapToNewUser(userRequestData);
        if ("".equals(user.getEmail())) {
            user.setEmail(null);
        }
        User savedUser = this.userRepository.save(user);
        return savedUser;
    }

    public User mapToNewUser(UserRequestData userRequestData) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRequestData, User.class);
        return user;
    }

    public UserResponseData mapToUserResponseData(User user) {
        UserResponseData userResponseData = new UserResponseData();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(user, userResponseData); // Map this last to get the User dateCreated/dateModified
        return userResponseData;
    }

    public User deleteUserByLogin(String login) {
        User user = this.userRepository.findUserByLogin(login);
        Assert.entityNotNull(user, User.class, "login", login);
        this.userRepository.delete(user);
        return user;
    }
}
