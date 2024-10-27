package com.spring.mvc.controller;

import com.spring.mvc.dto.AccountDto;
import com.spring.mvc.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "/login"})
    public String login(Model model) {
        model.addAttribute("accountForm", new AccountDto());
        return "login";
    }

    @PostMapping({"/", "/login"})
    public String login(@Valid @ModelAttribute("accountForm") AccountDto accountDto,
                        Model model, BindingResult bindingResult) {
        boolean valid = accountService.verifyAccount(accountDto);
        if (valid) {
            model.addAttribute("message", "Log in successful");
//            return "redirect:/";
        } else {
            model.addAttribute("message", "Email or password is not valid");
        }
        model.addAttribute("accountForm", accountDto);
            return "login";
    }

}
