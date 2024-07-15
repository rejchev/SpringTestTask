package pw.rejchev.springtesttask.controllers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.entities.Client;
import pw.rejchev.springtesttask.entities.Deposit;
import pw.rejchev.springtesttask.entities.dto.DepositDto;
import pw.rejchev.springtesttask.services.IBankService;
import pw.rejchev.springtesttask.services.IClientService;
import pw.rejchev.springtesttask.services.IDepositService;
import pw.rejchev.springtesttask.services.IDepositService;
import pw.rejchev.springtesttask.utils.SearcherStruct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositControllerTest {

    final static String END_POINT = "/api/deposits";

    @Autowired
    IDepositService depositService;

    @Autowired
    IBankService bankService;

    @Autowired
    IClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    public Client getClientInstance() {
        return clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address(UUID.randomUUID().toString())
                .okopf(40000)
                .build());
    }

    public Bank getBankInstance() {
        return bankService.createBank(Bank.builder()
                .bic(888888888)
                .name(UUID.randomUUID().toString())
                .build());
    }

    @Test
    public void createDepositTest_success() throws Exception {
        Bank bank = getBankInstance();

        Client client = getClientInstance();

        DepositDto expected = DepositDto.builder()
                .id("")
                .bankId(bank.getBic().toString())
                .clientId(client.getId())
                .durationInMonth(14)
                .createdAt(LocalDate.now())
                .rate(1.18)
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(END_POINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expected));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.bank.bic", is(bank.getBic())))
                .andExpect(jsonPath("$.client.id", is(client.getId())))
                .andExpect(jsonPath("$.created_at", is(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(expected.getCreatedAt()))))
                .andExpect(jsonPath("$.duration_in_month", is(expected.getDurationInMonth())))
                .andExpect(jsonPath("$.rate", is(expected.getRate())));

        depositService.deleteAllDeposits();
        clientService.deleteAllClients();
        bankService.clean();
    }




}
