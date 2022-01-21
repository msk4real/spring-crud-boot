package com.example.springcrudboot.controller;

import com.example.springcrudboot.model.User;
import com.example.springcrudboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String printWelcome(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, welcome to the Users world!");
        messages.add("Click the link bellow (:");
        model.addAttribute("messages", messages);
        return "/users";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "/index";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newPerson (@ModelAttribute("user") User user){
        return "/new";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getById(id));
        return "templates/edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id){
        userService.edit(id, user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/index";
    }
}