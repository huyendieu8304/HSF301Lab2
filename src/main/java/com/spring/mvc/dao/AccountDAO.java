package com.spring.mvc.dao;

import com.spring.mvc.entity.Account;

import java.util.Optional;

public interface AccountDAO {

    Account getAccountByID(int id);
    Account getAccountByEmail (String email);
}
