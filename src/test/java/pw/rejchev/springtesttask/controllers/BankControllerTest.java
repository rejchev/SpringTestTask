package pw.rejchev.springtesttask.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
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
import pw.rejchev.springtesttask.services.IBankService;
import pw.rejchev.springtesttask.utils.SearcherStruct;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankControllerTest {

    final static String END_POINT = "/api/banks";

    @Autowired
    IBankService bankService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createBankTest_success() throws Exception {
        Bank expected = Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(END_POINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expected));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bic", is(expected.getBic())))
                .andExpect(jsonPath("$.name", is(expected.getName())));
    }

    @Test
    public void getBanksTest_success() throws Exception {
        Bank expected = bankService.createBank(Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build());


        mockMvc.perform(MockMvcRequestBuilders
                        .get(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bic", is(expected.getBic())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(expected.getName())));
    }

    @Test
    public void getBankTest_success() throws Exception {
        Bank expected = bankService.createBank(Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build());


        mockMvc.perform(MockMvcRequestBuilders
                        .get(END_POINT + "/" + expected.getBic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bic", is(expected.getBic())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(expected.getName())));
    }

    @Test
    public void putBankTest_success() throws Exception {
        Bank source = bankService.createBank(Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build());

        Bank expected = Bank.builder()
                .bic(source.getBic())
                .name("AO TestTestBank")
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(END_POINT + "/" + source.getBic())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expected));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bic", is(expected.getBic())))
                .andExpect(jsonPath("$.name", is(expected.getName())));
    }

    @Test
    public void deleteBankTest_success() throws Exception {
        Bank source = bankService.createBank(Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build());


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete(END_POINT + "/" + source.getBic())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().is(204));

        assertThat(bankService.getBank(source.getBic())).isEmpty();
    }

    @Test
    public void searchBankTest_success() throws Exception {
        Bank source = bankService.createBank(Bank.builder()
                .bic(777777777)
                .name("АО ТЕСТБанк")
                .build());

        SearcherStruct searcherStruct = SearcherStruct.builder()
                .search("ТЕСТБ")
                .limit(Integer.MAX_VALUE)
                .offset(0)
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(END_POINT + "/search?" + searcherStruct)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].bic", is(source.getBic())))
                .andExpect(jsonPath("$[0].name", is(source.getName())));
    }

}
