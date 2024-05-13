package com.gamboatech.infrastructure.driveradapter.sql.oracle.adapters;

import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.entities.ClientEntity;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.repositories.ClientEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterImplTest {

    @InjectMocks
    private ClientRepositoryAdapterImpl underTest;

    @Mock
    private ClientEntityRepository repository;

    private ClientEntity entity;
    private Client model;

    @BeforeEach
    void setup(){
        this.entity = new ClientEntity()
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
    void shouldSaveANewClient() {

        when(repository.save(refEq(this.entity)))
                .thenReturn(this.entity);

        var result = underTest.save(this.model);

        assertEquals(this.model.getId(), result.getId());
        assertEquals(this.model.getClientId(), result.getClientId());
        assertEquals(this.model.getName(), result.getName());
        assertEquals(this.model.getGender(), result.getGender());
        assertEquals(this.model.getAge(), result.getAge());
        assertEquals(this.model.getIdentificationNumber(), result.getIdentificationNumber());
        assertEquals(this.model.getAddress(), result.getAddress());
        assertEquals(this.model.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(this.model.getPassword(), result.getPassword());
        assertEquals(this.model.getStatus(), result.getStatus());
        verify(repository, times(1)).save(refEq(this.entity));
    }

    @Test
    void ShouldUpdateAnExistentClient() {

        when(repository.findById(123L))
                .thenReturn(Optional.of(entity));

        when(repository.save(any()))
                .thenReturn(this.entity);

        var result = underTest.update(this.model);

        verify(repository,times(1)).save(refEq(this.entity));
    }

    @Test
    void ShouldNotUpdateAnUnexistentClient() {

        when(repository.findById(123L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> underTest.update(this.model));
        verify(repository,times(0)).save(any());
    }

    @Test
    void ShouldDeleteAClientByIdSuccessfully() {
        doNothing().when(repository).deleteById(123L);
        underTest.delete(model.getId());
        verify(repository,times(1)).deleteById(123L);
    }
}