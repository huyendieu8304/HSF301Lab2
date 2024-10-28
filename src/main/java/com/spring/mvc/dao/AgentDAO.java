package com.spring.mvc.dao;

import com.spring.mvc.entity.Agent;

import java.util.List;

public interface AgentDAO {

    Agent insert(Agent agent);
    Agent findByEmail(String email);
    Agent findById(Integer id);
    Agent findByName(String name);
    Agent update(Agent agent);

    List<Agent> searchAgent(String email, String status, String name, int pageSize, int pageNo);
    Long countAgent(String email, String status, String name);
}
