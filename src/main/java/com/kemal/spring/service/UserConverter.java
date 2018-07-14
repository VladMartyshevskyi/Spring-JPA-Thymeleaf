package com.kemal.spring.service;

import com.kemal.spring.domain.User;
import com.kemal.spring.web.dto.UserDto;

public class UserConverter {

	public static User convertToUser(UserDto userDto, String password) {
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
	
	public static UserDto convertToUserDto(User user, String password) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(password);
		userDto.setEnabled(user.isEnabled());
		return userDto;
	}

}
