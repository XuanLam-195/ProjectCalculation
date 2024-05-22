package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.service.AccountService;
import com.example.projectcalculation.utilities.Constant;
import com.example.projectcalculation.utilities.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.projectcalculation.utilities.PasswordHashing;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/list")
    public String getAccountList(Model model, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        model.addAttribute("accountList", accountService.getAllUsers());
        return "account/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        model.addAttribute("account", new AccountModel());
        return "account/create";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable Long id, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        model.addAttribute("account", accountService.getById(id));
        return "account/create";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        accountService.delete(id);
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        redirectAttributes.addFlashAttribute("message", "Delete account success!");
        return "redirect:/account/list";
    }

    @PostMapping("/save")
    public String createAccount(@ModelAttribute("account") AccountModel accountModel, RedirectAttributes redirectAttributes, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        try{
            accountService.createAccount(accountModel);
        }catch (Exception e){
            return "account/create";
        }
        redirectAttributes.addFlashAttribute("message", "Create account" + accountModel.getEmail() + "success!");
        return "redirect:/account/list";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") AccountModel accountModel, RedirectAttributes redirectAttributes, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        if (!Utils.validAdmin(session)){
            return Constant.RETURN_ERROR_403;
        }
        try{
            accountService.updateAccount(accountModel);
        }catch (Exception e){
            return "account/create";
        }
        redirectAttributes.addFlashAttribute("message", "Update account " + accountModel.getEmail() + "is success!");
        return "redirect:/account/list";
    }

    @PostMapping("/doLogin")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
                            Model model,HttpSession session){
        String enteredPassword = PasswordHashing.doHashing(password);
        AccountModel accountModel = accountService.getByEmail(email);
        if (accountModel != null && enteredPassword.equals(accountModel.getPassword())){
            session.setAttribute(Constant.CURRENT_USER, accountModel);
            return "redirect:/workspace";
        }
        model.addAttribute("message", "Login is not success!");
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        return "account/login";
    }
}
