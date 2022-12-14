package com.example.cookielogin.controller;

import com.example.cookielogin.model.User;
import com.example.cookielogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    //TODO NAV-bar hvis logget ind skal man se welcome, sales og profile samt logud.
    // Hvis ikke logget ind skal man kun se welcome.

    @GetMapping("/")
    public String pageOne(HttpSession httpSession) {
        if (!validateUser(httpSession).equals("validated")) {
            return "index";
        } else {
            return "welcome";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession httpSession) {
        User user = userRepository.findUserByEmail(email, password);
        System.out.println(user);
        if (user == null) {
            return "redirect:/error";
        }
        httpSession.setAttribute("username", email);
        httpSession.setAttribute("password", password);
        httpSession.setAttribute("name", user.getName());
        return "redirect:/welcome";
    }

    @GetMapping("/createuser")
    public String create(HttpSession httpSession) {
        if (!validateUser(httpSession).equals("validated")) {
            return "createuser";
        } else {
            return "welcome";
        }
    }

    @PostMapping("/createuser")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("name") String name, HttpSession httpSession) {
        if (userRepository.createnewUser(email, password, name) == null) {
            return "redirect:/error";
        }
        httpSession.setAttribute("username", email);
        httpSession.setAttribute("password", password);
        httpSession.setAttribute("name", name);

        return "redirect:/welcome";
    }

    public String validateUser(HttpSession httpSession) {
        User user2 = userRepository.findUserByEmail((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if (httpSession.getAttribute("username") == null) {
            return "redirect:/";
        } else if (user2.getEmail().equals(httpSession.getAttribute("username")) && user2.getPassword().equals(httpSession.getAttribute("password"))) {
            return "validated";
        }
        return "redirect:/error";
    }

    @GetMapping("/welcome")
    public String welcomeUser(HttpSession httpSession, Model model) {
        if (!validateUser(httpSession).equals("validated")) {
            return validateUser(httpSession);
        } else {
            model.addAttribute("user", userRepository.findUserByEmail((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("username")));
            httpSession.getAttribute("username");
            return "/welcome";
        }
    }

    @GetMapping("/page-two")
    public String pageTwo(HttpSession httpSession) {
        if (!validateUser(httpSession).equals("validated")) {
            return validateUser(httpSession);
        }
        return "page-two";
    }

    @GetMapping("/sales")
    public String salesPage(HttpSession httpSession) {
        if (!validateUser(httpSession).equals("validated")) {
            return validateUser(httpSession);
        }
        return "sales";
    }

    @GetMapping("/cookieinvalidate")
    public String invalidateCookie(HttpSession session) {
        System.out.println(session.getAttribute("username"));
        session.invalidate();
        return "redirect:/";
    }

}
