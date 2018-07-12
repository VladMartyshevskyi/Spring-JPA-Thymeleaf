package com.kemal.spring.service;

import com.kemal.spring.domain.User;
import com.kemal.spring.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDtoService {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserDtoService(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> findAll(){
        List<User> userList = userService.findAll();
        List<UserDto> userDtosList = new ArrayList<>();

        for(User user : userList){
            userDtosList.add(modelMapper.map(user, UserDto.class));
        }

        return userDtosList;
    }

    public Page<UserDto> findAllPageable(Pageable pageable) {
        Page<User> users = userService.findAllPageable(pageable);
        List <UserDto> userDtos = new ArrayList<>();

        for (User user: users) {
            userDtos.add(modelMapper.map(user, UserDto.class));
        }
        return new PageImpl<>(userDtos, pageable, users.getTotalElements());
    }

    public UserDto findById(Long id){
        UserDto userDto = new UserDto();
        for(UserDto userDto1 : findAll()){
            if(userDto1.getId().equals(id)){
                userDto = userDto1;
            }
        }
        return userDto;
    }

    public UserDto findByEmail(String email){
        for(UserDto userDto : findAll()){
            if(userDto.getEmail().equals(email)){
                return userDto;
            }
        }
        return null;
    }

    public UserDto findByUsername(String username){
        for(UserDto userDto : findAll()){
            if(userDto.getUsername().equals(username)){
                return userDto;
            }
        }
        return null;
    }
}
