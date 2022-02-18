package com.bogdan.accountservice.rest;

import com.bogdan.accountservice.entity.Account;
import com.bogdan.accountservice.rest.exception.BadRequestException;
import com.bogdan.accountservice.rest.exception.NotFoundException;
import com.bogdan.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountResource {

    private final AccountService accountService;

    @Autowired
    public AccountResource(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Account getById(@PathVariable final Long id) {
        return accountService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Account with id = %s does not exist", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody final Account account) {
        return accountService.add(account);
    }

    @PutMapping(value = "/{id}")
    public Account update(@PathVariable final Long id, @RequestBody final Account account) {
        if (!id.equals(account.getId())) {
            throw new BadRequestException(String.format("Id from path variable = %s is not equal with id from request body = %s", id, account.getId()));
        }
        Account dbAccount = accountService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Account with id = %s does not exist", id)));

        dbAccount.setEmail(account.getEmail());
        dbAccount.setName(account.getName());
        dbAccount.setPassword(account.getPassword());

        return accountService.update(dbAccount);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        Account dbAccount = accountService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Account with id = %s does not exist", id)));
        accountService.delete(dbAccount);
    }

}
