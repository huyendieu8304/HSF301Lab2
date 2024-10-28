package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AgentDAO;
import com.spring.mvc.dto.AgentDto;
import com.spring.mvc.entity.Agent;
import com.spring.mvc.service.AgentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentDAO agentDAO;

    public AgentServiceImpl(AgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }
    @Override
    public boolean createAgent(AgentDto agentDto) {
        //            EAgentStatus status = EAgentStatus.valueOf(agentDto.getStatus());
//            EAgentStatus status = agentDto.getStatus();

        Double balance = Double.parseDouble(agentDto.getAccountBalance());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(agentDto.getRegisterDate(), formatter);


        Agent agent = Agent.builder()
                .name(agentDto.getName())
                .email(agentDto.getEmail())
                .address(agentDto.getAddress())
                .status(agentDto.getStatus())
                .accountBalance(balance)
                .registerDate(date)
                .build();
        try {
            agentDAO.insert(agent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isNameExist(String name, Integer agentId) {
        Agent agent = agentDAO.findByName(name);
        System.out.println(agentId);
        System.out.println(agent + "agent id: " + agent.getId());
        return agent != null && !agent.getId().equals(agentId);
    }

    @Override
    public boolean isEmailExist(String email, Integer agentId) {
        try {
            Agent agent = agentDAO.findByEmail(email);
//            System.out.println(agent + "agent id: " + agent.getId());
            return agent != null && !agent.getId().equals(agentId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AgentDto getAgentById(Integer id) {
        try {
            Agent agent = agentDAO.findById(id);
            AgentDto agentDto = AgentDto.builder()
                    .id(agent.getId())
                    .name(agent.getName())
                    .email(agent.getEmail())
                    .status(agent.getStatus())
                    .address(agent.getAddress())
                    .registerDate(agent.getRegisterDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .accountBalance(agent.getAccountBalance().toString())
                    .build();
            return agentDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateAgent(AgentDto agentDto) {
        Double balance = Double.parseDouble(agentDto.getAccountBalance());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(agentDto.getRegisterDate(), formatter);

        Agent agent = Agent.builder()
                .id(agentDto.getId())
                .name(agentDto.getName())
                .email(agentDto.getEmail())
                .address(agentDto.getAddress())
                .status(agentDto.getStatus())
                .accountBalance(balance)
                .registerDate(date)
                .build();
        try {
            agentDAO.update(agent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AgentDto> searchAgent(String email, String status, String name, int pageSize, int pageNo) {
        List<Agent> agents = agentDAO.searchAgent(email, status, name, pageSize, pageNo);
        return agents.stream().map(agent -> {
            AgentDto agentDto = AgentDto.builder()
                    .id(agent.getId())
                    .name(agent.getName())
                    .email(agent.getEmail())
                    .status(agent.getStatus())
                    .address(agent.getAddress())
                    .registerDate(agent.getRegisterDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .accountBalance(agent.getAccountBalance().toString())
                    .build();
            return agentDto;
        }).toList();
    }

    @Override
    public int getTotalPage(String email, String status, String name, int pageSize, int pageNo) {
        Long numberOfAgents = agentDAO.countAgent(email, status, name);
        return (int) Math.ceil((double)numberOfAgents / pageSize);
    }

    @Override
    public boolean deleteAgentById(Integer id) {
        if (agentDAO.delete(id) == null) {
            return false;
        }
        return true;
    }


}
