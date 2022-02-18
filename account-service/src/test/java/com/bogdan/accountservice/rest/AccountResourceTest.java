package com.bogdan.accountservice.rest;

import com.bogdan.accountservice.entity.Account;
import com.bogdan.accountservice.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountResource.class)
@AutoConfigureMockMvc
public class AccountResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AccountResource accountResource;

    @MockBean
    private AccountServiceImpl accountService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private Account account;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        this.account = new Account();
        this.account.setId(1L);
        this.account.setName("Test account");
        this.account.setPassword("Test password");
        this.account.setEmail("TestEmail@test.com");
        this.account.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void should_returnAccount_whenGetByIdIsCalledAndAccountExists() throws Exception {
        final Long id = 1L;
        Mockito.when(accountService.findById(id)).thenReturn(Optional.of(account));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/accounts/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Account"));
    }

    @Test
    public void should_returnNotFound_whenGetByIdIsCalledAndAccountDoesNotExist() throws Exception {
        final Long id = 1L;
        Mockito.when(accountService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/accounts/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Account with id = 1 does not exist"));
    }

    @Test
    public void should_returnAllAccounts_whenGetAllIsCalled() throws Exception {
        Mockito.when(accountService.findAll()).thenReturn(Collections.singletonList(account));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Test Account"));
    }

    @Test
    public void should_returnCreatedAccount_whenCreateIsCalled() throws Exception {
        Mockito.when(accountService.add(account)).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Account"));
    }

    @Test
    public void should_returnUpdatedAccount_whenUpdateIsCalledAndAccountExists() throws Exception {
        final Long id = 1L;
        final Account updatedAccount = new Account();
        updatedAccount.setName("Updated name");

        Mockito.when(accountService.findById(id)).thenReturn(Optional.of(account));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated name"));
    }

    @Test
    public void should_returnNotFound_whenUpdateIsCalledAndAccountDoesNotExist() throws Exception {
        final Long id = 1L;
        Mockito.when(accountService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/accounts/%s", 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Account with id = 1 does not exist"));
    }

    @Test
    public void should_returnBadRequest_whenUpdateIsCalledAndIdFromPathVariableDoesNotMatchIdFromRequestBody() throws Exception {
        final Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Id from path variable = 2 is not equal with id from request body = 1"));
    }

    @Test
    public void should_deleteEntity_whenDeleteIsCalledAndAccountExists() throws Exception {
        final Long id = 1L;
        Mockito.when(accountService.findById(id)).thenReturn(Optional.of(account));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/accounts/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_returnNotFound_whenDeleteIsCalledAndAccountDoesNotExist() throws Exception {
        final Long id = 1L;
        Mockito.when(accountService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/accounts/%s", id)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Account with id = 1 does not exist"));
    }
}
