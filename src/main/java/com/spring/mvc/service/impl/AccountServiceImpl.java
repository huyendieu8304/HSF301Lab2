package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.dto.AccountDto;
import com.spring.mvc.entity.Account;
import com.spring.mvc.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public boolean verifyAccount(AccountDto accountDto) {
        try {
            Account account = accountDAO.getAccountByEmail(accountDto.getEmail());
            return account != null &&
                    account.getPassword().equals(accountDto.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        Account account = accountDAO.getAccountByEmail(email);
        System.out.println(account.getEmail());

        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .build();
    }


}
