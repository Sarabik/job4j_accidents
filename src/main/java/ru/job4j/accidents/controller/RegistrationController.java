package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityDataService;
import ru.job4j.accidents.service.UserDataService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserDataService userService;
    private final AuthorityDataService authorityService;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        Optional<User> optional = userService.addUser(user);
        if (optional.isEmpty()) {
            model.addAttribute("errorMessage", "User with that username already exists");
            return "reg";
        }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }

}
