package com.kemal.spring.configuration;

import com.kemal.spring.domain.Role;
import com.kemal.spring.domain.RoleRepository;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
    private boolean alreadySetup = false;
	@Autowired
    private UserService userService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        Role roleUser = createRoleIfNotFound("ROLE_USER");

        List<Role> adminRoles = Collections.singletonList(roleAdmin);
        List<Role> userRoles = Collections.singletonList(roleUser);

        createUserIfNotFound("admin@gmail.com", "Admin", "Admin",
                "admin", "admin", adminRoles);

        for (int i = 1; i < 50; i++) {
            createUserIfNotFound("user" + i + "@gmail.com", "User" + i,
                    "User" + i, "user" + i, "user" + i, userRoles);
        }


        alreadySetup = true;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleService.save(role);
        }
        return role;
    }

    @Transactional
    private final void createUserIfNotFound(final String email, String name,
                                            String surname, String username,
                                            String password, List<Role> userRoles) {
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
            userService.save(user);
            for(Role role : userRoles) {
            	roleService.assignRole(user, role);
            }
        }
    }
}