package com.kemal.spring.web.controllers.viewControllers.adminControllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kemal.spring.domain.Role;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.service.UserDtoService;
import com.kemal.spring.service.UserService;
import com.kemal.spring.web.dto.UserDto;
import com.kemal.spring.web.paging.InitialPagingSizes;
import com.kemal.spring.web.paging.Pager;

@Controller
@RequestMapping("/adminPage")
public class UsersController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserDtoService userDtoService;

	@GetMapping("/users")
	public String showUsers(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {

		int evalPageSize = pageSize.orElse(InitialPagingSizes.getInitialPageSize());
		int evalPage = (page.orElse(0) < 1) ? InitialPagingSizes.getInitialPage() : page.get() - 1;

		Page<UserDto> users = userDtoService.findAllPageable(new PageRequest(evalPage, evalPageSize));
		Pager pager = new Pager(users.getTotalPages(), users.getNumber(), InitialPagingSizes.getButtonsToShow());
		model.addAttribute("users", users);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", InitialPagingSizes.getPageSizes());
		model.addAttribute("pager", pager);
		return "adminPage/user/users";
	}

	@GetMapping("/users/{id}")
	public String getEditUserForm(@PathVariable Long id, Model model) {

		model.addAttribute("user", userDtoService.findById(id));
		model.addAttribute("userRoles", roleService.getAssignedRoles(userService.findById(id)));
		model.addAttribute("allRoles", roleService.findAll());
		return "adminPage/user/editUser";
	}

	@PostMapping("/users/{id}")
	public String updateUser(Model model, @PathVariable Long id, @ModelAttribute("user") @Valid final UserDto userDto,
			@ModelAttribute("userRoles") final ArrayList<Role> roles) {
		userService.updateUser(userDto, roles);
		return "redirect:/adminPage/users";
	}

	@GetMapping("/users/newUser")
	public String getNewUserForm(Model model) {
		UserDto newUser = new UserDto();
		model.addAttribute("newUser", newUser);
		return "adminPage/user/newUser";
	}

	@PostMapping("/users/newUser")
	public String saveNewUser(Model model, @ModelAttribute("newUser") @Valid final UserDto userDto) {
		userService.createUser(userDto);
		return "redirect:/adminPage/users";
	}

}
