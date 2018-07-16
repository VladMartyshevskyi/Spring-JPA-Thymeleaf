package com.kemal.spring.web.controllers.viewControllers.adminControllers;

import com.kemal.spring.domain.Role;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/adminPage")
public class RolesController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/roles")
	public ModelAndView showRoles() {
		ModelAndView modelAndView = new ModelAndView("adminPage/role/roles");
		modelAndView.addObject("roles", roleService.findAll());
		return modelAndView;
	}

	@GetMapping("/roles/{id}")
	public ModelAndView getEditRoleForm(@PathVariable Long id) {
		Role role = roleService.findById(id);
		ModelAndView modelAndView = new ModelAndView("adminPage/role/editRole");
		modelAndView.addObject("role", role);
		return modelAndView;
	}

	@PostMapping("/roles/{id}")
	public String updateRole(Model model, @PathVariable Long id, @ModelAttribute("oldRole") @Valid final Role role,
			RedirectAttributes redirectAttributes) {
		Role existingRole = roleService.findByName(role.getName());
		if (existingRole != null && !existingRole.getName().equals(role.getName())) {
			throw new RuntimeException("Role name is already taken");
		} else {
			roleService.save(role);
			redirectAttributes.addFlashAttribute("roleHasBeenUpdated", true);
			return "redirect:/adminPage/roles";
		}

	}

	@GetMapping("/roles/newRole")
	public String getAddNewRoleForm(Model model) {
		model.addAttribute("newUser", new UserDto());
		return "adminPage/role/newRole";
	}

	@PostMapping("/roles/newRole")
	public String saveNewRole(Model model, @ModelAttribute("newRole") @Valid final Role role,
			RedirectAttributes redirectAttributes) {
		Role existingRole = roleService.findByName(role.getName());
		if (existingRole != null && !existingRole.getName().equals(role.getName())) {
			throw new RuntimeException("Role name is already taken");
		} else {
			roleService.save(role);
			redirectAttributes.addFlashAttribute("roleHasBeenSaved", true);
			return "redirect:/adminPage/roles";
		}
	}
}
