package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "/index"})
public class IndexController {

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("user", "Lidija");
        return "index";
    }
}
