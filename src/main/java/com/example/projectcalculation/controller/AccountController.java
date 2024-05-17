package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.repository.UserRepository;
import com.example.projectcalculation.utilities.Constant;
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
    UserRepository userRepository;

    @GetMapping("/list")
    public String getAccountList(Model model){
        model.addAttribute("accountList", userRepository.getAllUsers());
        return "account/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model){
        model.addAttribute("account", new AccountModel());
        return "account/create";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable Long id){
        model.addAttribute("account", userRepository.getById(id));
        return "account/create";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable Long id, RedirectAttributes redirectAttributes){
        userRepository.delete(id);
        redirectAttributes.addFlashAttribute("message", "Delete account success!");
        return "redirect:/account/list";
    }

    @PostMapping("/save")
    public String createAccount(@ModelAttribute("account") AccountModel accountModel, RedirectAttributes redirectAttributes, Model model, HttpSession session){
        try{
            userRepository.createAccount(accountModel);
        }catch (Exception e){
            return "account/create";
        }
        redirectAttributes.addFlashAttribute("message", "Create account" + accountModel.getEmail() + "success!");
        return "redirect:/account/list";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") AccountModel accountModel, RedirectAttributes redirectAttributes){
        try{
            userRepository.updateAccount(accountModel);
        }catch (Exception e){
            return "account/create";
        }
        redirectAttributes.addFlashAttribute("message", "Update account " + accountModel.getEmail() + "is success!");
        return "redirect:/account/list";
    }
}
