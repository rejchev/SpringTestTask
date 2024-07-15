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
import pw.rejchev.springtesttask.entities.Client;
import pw.rejchev.springtesttask.services.IClientService;
import pw.rejchev.springtesttask.utils.SearcherStruct;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientControllersTest {

    final static String END_POINT = "/api/clients";

    @Autowired
    IClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createClientTest_success() throws Exception {
        Client expected = Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(END_POINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expected));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.short_name", is(expected.getShortName())))
                .andExpect(jsonPath("$.address", is(expected.getAddress())))
                .andExpect(jsonPath("$.okopf", is(expected.getOkopf())));

    }

    @Test
    public void getClientsTest_success() throws Exception {
        Client expected = clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build());


        mockMvc.perform(MockMvcRequestBuilders
                        .get(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(expected.getId())))
                .andExpect(jsonPath("$[0].name", is(expected.getName())))
                .andExpect(jsonPath("$[0].short_name", is(expected.getShortName())))
                .andExpect(jsonPath("$[0].address", is(expected.getAddress())))
                .andExpect(jsonPath("$[0].okopf", is(expected.getOkopf())));

        clientService.deleteClient(expected.getId());
    }

    @Test
    public void getClientTest_success() throws Exception {
        Client expected = clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build());


        mockMvc.perform(MockMvcRequestBuilders
                        .get(END_POINT + "/" + expected.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(expected.getId())))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.short_name", is(expected.getShortName())))
                .andExpect(jsonPath("$.address", is(expected.getAddress())))
                .andExpect(jsonPath("$.okopf", is(expected.getOkopf())));


        clientService.deleteClient(expected.getId());
    }

    @Test
    public void putClientTest_success() throws Exception {
        Client source = clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build());

        Client expected = Client.builder()
                .id("")
                .name("АО ТестКлиент")
                .shortName("АОТК")
                .address("Самара ул")
                .okopf(20000)
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(END_POINT + "/" + source.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(expected));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(source.getId())))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.short_name", is(expected.getShortName())))
                .andExpect(jsonPath("$.address", is(expected.getAddress())))
                .andExpect(jsonPath("$.okopf", is(expected.getOkopf())));

        clientService.deleteClient(source.getId());
    }

    @Test
    public void deleteClientTest_success() throws Exception {
        Client source = clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build());


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete(END_POINT + "/" + source.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().is(204));

        assertThat(clientService.findClient(source.getId())).isEmpty();
    }

    @Test
    public void searchClientTest_success() throws Exception {
        Client expected = clientService.createClient(Client.builder()
                .id("")
                .name(UUID.randomUUID().toString())
                .shortName(UUID.randomUUID().toString())
                .address("Пермь ул")
                .okopf(10000)
                .build());

        SearcherStruct searcherStruct = SearcherStruct.builder()
                .search("Пермь")
                .limit(Integer.MAX_VALUE)
                .offset(0)
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(END_POINT + "/search?" + searcherStruct)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(expected.getId())))
                .andExpect(jsonPath("$[0].name", is(expected.getName())))
                .andExpect(jsonPath("$[0].short_name", is(expected.getShortName())))
                .andExpect(jsonPath("$[0].address", is(expected.getAddress())))
                .andExpect(jsonPath("$[0].okopf", is(expected.getOkopf())));


        clientService.deleteClient(expected.getId());
    }
}
