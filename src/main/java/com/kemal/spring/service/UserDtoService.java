package com.kemal.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.User;
import com.kemal.spring.web.dto.UserDto;

@Service
public class UserDtoService {

	private static Logger logger = LoggerFactory.getLogger(UserDtoService.class);
	
	@Autowired
    private UserService userService;
	
    public List<UserDto> findAll(){
    	logger.debug("Method findAll was invoked");
        return userService.findAll().stream().map(user -> UserConverter.convertToUserDto(user)).collect(Collectors.toList());
    }

    public Page<UserDto> findAllPageable(Pageable pageable) {
    	logger.debug("Method findAllPageable was invoked with pageable {}", pageable);
        Page<User> users = userService.findAllPageable(pageable);
        List <UserDto> userDtos = new ArrayList<>();
        for (User user: users) {
            userDtos.add(UserConverter.convertToUserDto(user));
        }
        return new PageImpl<>(userDtos, pageable, users.getTotalElements());
    }

    public UserDto findById(Long id){
    	logger.debug("Method findById was invoked with id {}", id);
        User user = userService.findById(id);
        return UserConverter.convertToUserDto(user);
    }

    public UserDto findByEmail(String email){
    	logger.debug("Method findByEmail was invoked with email {}", email);
    	User user = userService.findByEmail(email);
        return UserConverter.convertToUserDto(user);
    }

    public UserDto findByUsername(String username){
    	logger.debug("Method findByUsername was invoked with username {}", username);
    	User user = userService.findByUsername(username);
        return UserConverter.convertToUserDto(user);
    }
}
