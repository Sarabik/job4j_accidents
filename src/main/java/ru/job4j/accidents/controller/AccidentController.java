package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        model.addAttribute("rules", ruleService.getAllRules());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(typeId));
        String[] ruleIds = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(ruleService.getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        accidentService.addAccident(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        model.addAttribute("accident", accidentService.getAccident(id));
        model.addAttribute("rules", ruleService.getAllRules());
        return "accidents/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req) {
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(typeId));
        String[] ruleIds = req.getParameterValues("rIds");
        if (ruleIds != null) {
            Set<Rule> rules = new HashSet<>();
            for (String id : ruleIds) {
                rules.add(ruleService.getRule(Integer.parseInt(id)));
            }
            accident.setRules(rules);
        }
        accidentService.editAccident(accident);
        return "redirect:/index";
    }
}