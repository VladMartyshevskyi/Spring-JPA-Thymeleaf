package com.kemal.spring.service;

import com.kemal.spring.domain.User;
import com.kemal.spring.web.dto.UserDto;

public class UserConverter {

	public static User convertToUser(UserDto userDto, String password) {
		if(userDto != null) {
			User user = new User();
			user.setId(userDto.getId());
			user.setName(userDto.getName());
			user.setSurname(userDto.getSurname());
			user.setUsername(userDto.getUsername());
			user.setEmail(userDto.getEmail());
			user.setPassword(password);
			user.setEnabled(userDto.isEnabled());
			return user;
		}
		return null;
	}
	
	public static UserDto convertToUserDto(User user) {
		if(user != null) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setSurname(user.getSurname());
			userDto.setUsername(user.getUsername());
			userDto.setEmail(user.getEmail());
			userDto.setEnabled(user.isEnabled());
			return userDto;
		}
		return null;
	}

}
