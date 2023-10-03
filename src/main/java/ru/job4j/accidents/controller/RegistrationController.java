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
import ru.job4j.accidents.repository.AuthorityDataRepository;
import ru.job4j.accidents.repository.UserDataRepository;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserDataRepository userRepository;
    private final AuthorityDataRepository authorityRepository;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityRepository.findByAuthority("ROLE_USER"));
        try {
            userRepository.save(user);
            return "redirect:/login";
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("errorMessage", "User with that username already exists");
            return "reg";
        }
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }

}