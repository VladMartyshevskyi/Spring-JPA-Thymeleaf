package com.kemal.spring.web.dto;


import javax.validation.constraints.NotNull;
import com.kemal.spring.customAnnotations.PasswordMatches;
import com.kemal.spring.customAnnotations.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@Data
public class UserDto {

    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;

    @ValidEmail
    @NotNull
    private String email;
 
    private String password;

    private boolean enabled = true;

}
