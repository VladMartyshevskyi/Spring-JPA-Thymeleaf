package com.kemal.spring.service.userDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.User;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.service.UserService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private RoleService roleService;

   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user, roleService.getAssignedRoles(user));
    }
}
