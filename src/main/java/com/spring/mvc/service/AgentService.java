package com.spring.mvc.service;

import com.spring.mvc.dto.AgentDto;

import java.util.List;

public interface AgentService {
    boolean createAgent(AgentDto agentDto);
    boolean isNameExist(String name, Integer agentId);
    boolean isEmailExist(String email, Integer agentId);
    AgentDto getAgentById(Integer id);
    boolean updateAgent(AgentDto agentDto);

    List<AgentDto> searchAgent(String email, String status, String name, int pageSize, int pageNo);
    int getTotalPage(String email, String status, String name, int pageSize, int pageNo);

    boolean deleteAgentById(Integer id);
}
