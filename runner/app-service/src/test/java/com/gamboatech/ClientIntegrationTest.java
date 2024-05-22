package com.gamboatech;

import com.gamboatech.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientIntegrationTest {

    private static final String REQUEST_BODY = "{\n" +
            "  \"id\": \"999\",\n" +
            "  \"name\": \"Nixon Gamboa\",\n" +
            "  \"gender\": \"MALE\",\n" +
            "  \"age\": 30,\n" +
            "  \"identificationNumber\": \"ABC123456\",\n" +
            "  \"address\": \"123 Main Street\",\n" +
            "  \"phoneNumber\": \"+1234567890\",\n" +
            "  \"password\": \"secretpassword\",\n" +
            "  \"status\": true,\n" +
            "  \"clientId\": \"client123\"\n" +
            "}";
    private static final String SQL_INSERT = "INSERT INTO clients(id, name, gender, age, identification_number, address, phone_number, password, status, client_id)" +
            " VALUES (999, 'Juan', 'MALE', 30, '1234567890', 'Calle Principal 123', '1234567890', 'password123', true, 'client-999')";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Sql(statements = SQL_INSERT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM clients WHERE id='999'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void get() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort() + "/1234567890",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<Client>() {
                });
        var result = response.getBody();

        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Juan", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("1234567890", result.getIdentificationNumber());
        assertEquals("Calle Principal 123", result.getAddress());
        assertEquals("1234567890", result.getPhoneNumber());
        assertEquals("password123", result.getPassword());
        assertEquals("client-999", result.getClientId());
    }

    @Test
    @Sql(statements = "DELETE FROM clients WHERE id='999'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCreateClient() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @Sql(statements =SQL_INSERT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM clients WHERE id='999'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdateClient() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    void shouldNotUpdateClientCauseNotFound () throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
    @Test
    @Sql(statements =SQL_INSERT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteClient () throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/clientes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    void shouldValidateIdForDeleteClient () throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/clientes/abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Valor invalido para el parametro 'id': abc"));
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/clientes";
    }
}