package com.example.projectcalculation.controller;

import com.example.projectcalculation.utilities.Constant;
import com.example.projectcalculation.utilities.Utils;
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

    @GetMapping("/error-403")
    public String error403(HttpSession session) {
      if (!Utils.validSession(session))
        return Constant.RETURN_LOGIN;
      return "error/403";
    }

    @GetMapping("")
    public String getHome() {
        return "redirect:/login";
    }
}