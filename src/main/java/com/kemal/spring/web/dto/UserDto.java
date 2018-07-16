package com.kemal.spring.web.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import com.kemal.spring.domain.Role;
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
    private List<Role> roles;
}
