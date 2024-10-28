package com.spring.mvc.controller;

import com.spring.mvc.dto.AgentDto;
import com.spring.mvc.entity.Paging;
import com.spring.mvc.enumeration.EAgentStatus;
import com.spring.mvc.service.AgentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class AgentController {

    private final AgentService agentService;
//    private static int currentPage = 1;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
//        paging = new Paging(10, 1);
    }

    @GetMapping("/create-agent")
    public String createAgent(Model model, HttpSession session) {

        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);

        model.addAttribute("agentForm", new AgentDto());
        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));
        return "create-agent";
    }

    @PostMapping("/create-agent")
    public String createAgent(@Valid @ModelAttribute("agentForm") AgentDto agentDto,
                              BindingResult bindingResult, Model model, HttpSession session) {

        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);

        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("message", "Has some input errors");
                return "create-agent";
            }

            if (agentService.isEmailExist(agentDto.getEmail(), agentDto.getId())) {
                model.addAttribute("message", "Email already exists");
            } else {
                boolean valid = agentService.createAgent(agentDto);
                if (valid) {
                    model.addAttribute("message", "Agent created successfully");
                } else {
                    model.addAttribute("message", "Agent creation failed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Got some errors whilre processing");
            return "create-agent";
        }

        model.addAttribute("agentForm", agentDto);
        return "create-agent";
    }

    @GetMapping("/agent-detail/{agentId}")
    public String getAgentDetail(@PathVariable("agentId") Integer id, Model model, HttpSession session) {

        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);


        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));
        model.addAttribute("agentForm", agentService.getAgentById(id));
        return "agent-detail";
    }

    @PostMapping("/update-agent")
    public String updateAgent(@Valid @ModelAttribute("agentForm") AgentDto agentDto,
                              BindingResult bindingResult, Model model, HttpSession session) {


        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);

        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("message", "Has some input errors");
                return "agent-detail";
            }
            if (agentService.isNameExist(agentDto.getName(), agentDto.getId())) {
                model.addAttribute("message", "Agent's name already exists");
                return "agent-detail";
            }
            if (agentService.isEmailExist(agentDto.getEmail(), agentDto.getId())) {
                model.addAttribute("message", "Email already exists");
                return "agent-detail";
            }

            boolean valid = agentService.updateAgent(agentDto);
            if (valid) {
                model.addAttribute("message", "Agent updated successfully");
            } else {
                model.addAttribute("message", "Agent updated failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Got some errors while processing");
            return "agent-detail";
        }

        model.addAttribute("agentForm", agentDto);
        return "agent-detail";
    }

    @GetMapping("/agent-list")
    public String getAgentList( @RequestParam("targetPage") Optional<Integer> targetPage,
                        @RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("email") Optional<String> email,
                        @RequestParam("status") Optional<String> status,
                        @RequestParam("name") Optional<String> name,
                                Model model, HttpSession session) {

        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);

        int targetPageValue = targetPage.orElse(1);
        int pageSizeValue = pageSize.orElse(10); //default 10 row/page
        String emailValue = email.orElse(null);
        String statusValue = status.orElse(null);
        String nameValue = name.orElse(null);

        List<AgentDto> agentDtoList =  agentService.searchAgent(emailValue, statusValue, nameValue, pageSizeValue, targetPageValue);
        model.addAttribute("agentList", agentDtoList);

        int totalPage = agentService.getTotalPage(emailValue, statusValue, nameValue, pageSizeValue, targetPageValue);
        model.addAttribute("totalPage", totalPage);

        //send the status of agent back to front end
        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));

        model.addAttribute("currentPage", targetPageValue);
        model.addAttribute("pageSize", pageSizeValue );
        model.addAttribute("email", emailValue );
//        model.addAttribute("status", statusValue != null ? statusValue.toString() : null);
        model.addAttribute("status", statusValue );
        model.addAttribute("name", nameValue );

        return "agent-list";
    }

    @GetMapping("/delete-agent/{agentId}")
    public String deleteAgent(@PathVariable("agentId") Integer agentId, Model model, HttpSession session) {
//        model.addAttribute("statuses", Arrays.asList(EAgentStatus.values()));

//        model.addAttribute("agentForm", agentService.getAgentById(id));

//        if (agentService.deleteAgentById(agentId)) {
//            return "redirect:/agent-list";
//        } else {
//
//        }
        Object account = session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);

        agentService.deleteAgentById(agentId);
        return "redirect:/agent-list";

    }
}
