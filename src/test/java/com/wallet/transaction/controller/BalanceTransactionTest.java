package com.wallet.transaction.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.model.TransactionType;
import com.wallet.transaction.services.TransactionSenderService;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceTransactionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionSenderService service;

    @Test
     void test_create_user_success_200_ok() throws Exception {
        UUID walletId = UUID.randomUUID();
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto();
        balanceTransactionDto.setWalletId(walletId);
        balanceTransactionDto.setAmount(new BigDecimal(1100));
        balanceTransactionDto.setTransactionType(TransactionType.REPLENISH);
        doNothing().when(service).sendTransaction(balanceTransactionDto);

        ObjectMapper objectMapper = getObjectMapper();
        String s = objectMapper.writeValueAsString(balanceTransactionDto);

        mockMvc.perform(
                put("/balance/change/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isOk());

        verify(service, times(1)).sendTransaction(any());
    }

    @Test
    void test_create_user_fail_500_InternalServerError() throws Exception {
        UUID walletId = UUID.randomUUID();
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto();
        balanceTransactionDto.setWalletId(walletId);
        balanceTransactionDto.setAmount(new BigDecimal(1100));
        balanceTransactionDto.setTransactionType(TransactionType.REPLENISH);
        doThrow(new RuntimeException("Error occurred")).when(service).sendTransaction(any());
        ObjectMapper objectMapper = getObjectMapper();
        String s = objectMapper.writeValueAsString(balanceTransactionDto);

        mockMvc.perform(
                put("/balance/change/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void test_create_user_fail_400_BadRequest() throws Exception {
        UUID walletId = UUID.randomUUID();
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto();
        ObjectMapper objectMapper = getObjectMapper();
        String s = objectMapper.writeValueAsString(balanceTransactionDto);

        mockMvc.perform(
                put("/balance/change/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isBadRequest());

    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS, false);
        JacksonTester.initFields(this, objectMapper);
        return objectMapper;
    }



}
