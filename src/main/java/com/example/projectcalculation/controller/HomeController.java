package com.example.projectcalculation.controller;

import com.example.projectcalculation.utilities.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "account/login";
    }


    @GetMapping("/logout")
    public String getLogoutPage(HttpSession session) {
        session.removeAttribute(Constant.CURRENT_USER);
        return Constant.RETURN_LOGIN;
    }


    @GetMapping("")
    public String getHome() {
        return "redirect:/login";
    }
}