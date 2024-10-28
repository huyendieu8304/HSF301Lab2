package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AgentDAO;
import com.spring.mvc.entity.Agent;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class AgentDAOImpl implements AgentDAO {

    private final SessionFactory sessionFactory;

    public AgentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Agent insert(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(agent);
        return agent;
    }

    @Override
    public Agent findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Agent where email = :email";
        TypedQuery<Agent> query = session.createQuery(hql, Agent.class);
        query.setParameter("email", email);
        List<Agent> agents = query.getResultList();
        return agents.isEmpty() ? null : agents.get(0);
    }

    @Override
    public Agent findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Agent.class, id);
    }

    @Override
    public Agent findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Agent where name = :name";
        TypedQuery<Agent> query = session.createQuery(hql, Agent.class);
        query.setParameter("name", name);
        List<Agent> agents = query.getResultList();
        return agents.isEmpty() ? null : agents.get(0);
    }

    @Override
    public Agent update(Agent agent) {
        Session session = sessionFactory.getCurrentSession();

        Agent updatedAgent = session.find(Agent.class, agent.getId());
        updatedAgent.setName(agent.getName());
        updatedAgent.setEmail(agent.getEmail());
        updatedAgent.setAddress(agent.getAddress());
        updatedAgent.setAccountBalance(agent.getAccountBalance());
        updatedAgent.setStatus(agent.getStatus());
        updatedAgent.setRegisterDate(agent.getRegisterDate());

        return session.merge(updatedAgent);
    }

    @Override
    public Agent delete(Integer agentId) {
        Session session = sessionFactory.getCurrentSession();
        Agent deletedAgent = session.get(Agent.class, agentId);
        session.remove(deletedAgent);
        return deletedAgent;
    }

    @Override
    public List<Agent> searchAgent(String email, String status, String name, int pageSize, int pageNo) {
        Session session = sessionFactory.getCurrentSession();
        try {
            StringBuilder hql = new StringBuilder("FROM Agent where 1 = 1");
            Map<String, Object> params = new HashMap<String, Object>();
            if (status != null && !status.equals("")) {
                hql.append(" AND LOWER(status) = :status");
                params.put("status", status);
                System.out.println(name);

            }
            if (email != null && !email.equals("")) {
                hql.append(" AND LOWER(email) LIKE :email");
                params.put("email", "%" + email.toLowerCase() + "%");
                System.out.println(email);

            }
            if (name != null && !name.equals("")) {
                hql.append(" AND LOWER(name) LIKE :name");
                params.put("name", "%" + name.toLowerCase() + "%");
                System.out.println(name);

            }
            hql.append(" ORDER BY registerDate ASC, LOWER(name) ASC");

            TypedQuery<Agent> query = session.createQuery(hql.toString(), Agent.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            System.out.println(hql.toString());
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
            List<Agent> agents = query.getResultList();
            return agents;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Long countAgent(String email, String status, String name) {
        Session session = sessionFactory.getCurrentSession();
        try {
            StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Agent where 1 = 1");
            Map<String, Object> params = new HashMap<String, Object>();
            if (status != null && !status.equals("")) {
                hql.append(" AND LOWER(status) = :status");
                params.put("status", status);
                System.out.println(status);
            }
            if (email != null && !email.equals("")) {
                hql.append(" AND LOWER(email) LIKE :email");
                params.put("email", "%" + email.toLowerCase() + "%");
                System.out.println(email);
            }
            if (name != null && !name.equals("")) {
                hql.append(" AND LOWER(name) LIKE :name");
                params.put("name", "%" + name.toLowerCase() + "%");
                System.out.println(name);
            }

            TypedQuery<Long> query = session.createQuery(hql.toString(), Long.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            Long count = query.getSingleResult();
            return count;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
