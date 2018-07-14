package com.kemal.spring.web.dto;

import java.util.List;

import com.kemal.spring.customAnnotations.PasswordMatches;
import com.kemal.spring.customAnnotations.ValidEmail;
import com.kemal.spring.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@Data
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private String username;

    @ValidEmail
    private String email;

    private String password;

    private boolean enabled;

}
