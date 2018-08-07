package com.kemal.spring.web.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    private String email;
    private String password;
    private boolean enabled = true;
    
    public UserDto(String name, String surname, String username, String email, String password) {
    	this.name = name;
    	this.surname = surname;
    	this.username = username;
    	this.email = email;
    	this.password = password;
    }
    
}
