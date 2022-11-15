package com.example.cookielogin.controller;

import com.example.cookielogin.model.User;
import com.example.cookielogin.repository.UserRepository;
import com.example.cookielogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String pageOne(HttpSession httpSession) {
//        httpSession.setAttribute("user", "fred1234");
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession httpSession) {
        User user = new User(email, password);
        User user2 = userRepository.findUserByEmail(email);

        if (user.getEmail().equals(user2.getEmail()) && user.getPassword().equals(user2.getPassword())) {
            httpSession.setAttribute("user", email);
            model.addAttribute("username", email);
            return "redirect:/welcome";
        } else return "redirect:/error";
    }

    @GetMapping("/welcome")
    public String welcomeUser(HttpSession httpSession, Model model) {

        model.addAttribute("username", userRepository.findUserByEmail((String) httpSession.getAttribute("user")).getEmail());
        httpSession.getAttribute("user");

        return "welcome";
    }

    @GetMapping("/page-two")
    public String pageTwo(HttpSession httpSession) {
        httpSession.getAttribute("user");
        return "page-two";
    }

    @GetMapping("/cookieinvalidate")
    public String invalidateCookie(HttpSession session){
        System.out.println(session.getAttribute("username"));
        session.invalidate();
        return "redirect:/";
    }

}
