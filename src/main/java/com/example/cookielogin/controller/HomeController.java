package com.example.cookielogin.controller;

import com.example.cookielogin.model.User;
import com.example.cookielogin.repository.UserRepository;
import com.example.cookielogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;


    @GetMapping("/")
    public String pageOne() {
//        httpSession.setAttribute("user", "fred1234");
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession httpSession) {
        User user = new User(email, password);
        User user2 = userRepository.findUserByEmail(email);

        if (user.getEmail().equals(user2.getEmail()) && user.getPassword().equals(user2.getPassword())) {
            httpSession.setAttribute("username", email);
            httpSession.setAttribute("password", password);
            System.out.println(httpSession.getAttribute("username"));
            System.out.println(httpSession.getAttribute("password"));
            model.addAttribute("user", email);
            return "redirect:/welcome";
        } else return "redirect:/error";
    }

    public boolean validateUser(HttpSession httpSession) {
        User user2 = userRepository.findUserByEmail((String) httpSession.getAttribute("username"));
        System.out.println(user2.getEmail() + " vs " + httpSession.getAttribute("username"));
        System.out.println(user2.getPassword() + " vs " + httpSession.getAttribute("password"));
        if (user2.getEmail().equals(httpSession.getAttribute("username")) && user2.getPassword().equals(httpSession.getAttribute("password"))) {
            return false;
        }
        return true;
    }

    @GetMapping("/welcome")
    public String welcomeUser(HttpSession httpSession, Model model) {

        if (httpSession == null) {
            return "redirect:/notloggedin";
        } else if (validateUser(httpSession)) {
            return "redirect:/error";
        } else {

            model.addAttribute("user", userRepository.findUserByEmail((String) httpSession.getAttribute("username")).getEmail());
            httpSession.getAttribute("username");

            return "/welcome";
        }
    }

    @GetMapping("/page-two")
    public String pageTwo(HttpSession httpSession) {
        httpSession.getAttribute("username");
        return "page-two";
    }

    @GetMapping("/cookieinvalidate")
    public String invalidateCookie(HttpSession session) {
        System.out.println(session.getAttribute("username"));
        session.invalidate();
        return "redirect:/";
    }

}
