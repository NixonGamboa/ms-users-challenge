package com.gamboatech.infrastructure.entrypoints.rest.client;

import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import com.gamboatech.domain.usecase.client.ClientUseCase;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    private ClientController underTest;

    @Mock
    private ClientUseCase clientUseCase;

    private ClientDto dto;
    private Client model;

    @BeforeEach
    void setup(){
        this.dto = new ClientDto()
                .setId(123L)
                .setClientId("someId")
                .setName("Juan Perez")
                .setGender(Gender.MALE.name())
                .setAge(30)
                .setIdentificationNumber("123456789")
                .setAddress("Calle falsa 123")
                .setPhoneNumber("555-1234")
                .setPassword("password")
                .setStatus(true);
        this.model = (Client) new Client()
                .setPassword("password")
                .setStatus(true)
                .setClientId("someId")
                .setId(123L)
                .setName("Juan Perez")
                .setGender(Gender.MALE)
                .setAge(30)
                .setIdentificationNumber("123456789")
                .setAddress("Calle falsa 123")
                .setPhoneNumber("555-1234");
    }

    @Test
    void shouldCreateANewClientSuccessfully() {

        when(clientUseCase.create(refEq(this.model)))
                .thenReturn(this.model);

        var result = underTest.create(this.dto);

        assertEquals(this.dto.getId(), result.getId());
        assertEquals(this.dto.getClientId(), result.getClientId());
        assertEquals(this.dto.getName(), result.getName());
        assertEquals(this.dto.getGender(), result.getGender());
        assertEquals(this.dto.getAge(), result.getAge());
        assertEquals(this.dto.getIdentificationNumber(), result.getIdentificationNumber());
        assertEquals(this.dto.getAddress(), result.getAddress());
        assertEquals(this.dto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(this.dto.getPassword(), result.getPassword());
        assertEquals(this.dto.getStatus(), result.getStatus());
        verify(clientUseCase, times(1)).create(refEq(this.model));
    }

    @Test
    void ShouldUpdateAnExistentClientSuccessfully() {
        when(clientUseCase.update(refEq(this.model)))
                .thenReturn(this.model);

        var result = underTest.update(this.dto);

        verify(clientUseCase,times(1)).update(refEq(this.model));
    }

    @Test
    void ShouldDeleteAClientByIdSuccessfully() {
        doNothing().when(clientUseCase).delete(123L);
        underTest.delete(dto.getId());
        verify(clientUseCase,times(1)).delete(123L);
    }
}
