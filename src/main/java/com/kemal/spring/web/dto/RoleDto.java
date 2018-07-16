package com.kemal.spring.web.dto;

import com.kemal.spring.customAnnotations.ValidRoleName;
import lombok.Data;

@Data
public class RoleDto {
    Long id;
    @ValidRoleName
    String name;
}
