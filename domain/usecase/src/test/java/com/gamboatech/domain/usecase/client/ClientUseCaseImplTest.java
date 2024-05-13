package com.gamboatech.domain.usecase.client;

import com.gamboatech.application.adapters.driveradapters.sql.ClientRepositoryAdapter;
import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientUseCaseImplTest {


    @InjectMocks
    private ClientUseCaseImpl underTest;

    @Mock
    private ClientRepositoryAdapter repositoryAdapter;

    private Client client;

    @BeforeEach
    void setup(){
        this.client = new Client()
                .setClientId("someId");
    }

    @Test
    void shouldCreateANewClient() {

        when(repositoryAdapter.save(refEq(this.client)))
                .thenReturn(this.client);

        var result = underTest.create(this.client);

        assertTrue(Objects.nonNull(result));
        verify(repositoryAdapter, times(1)).save(refEq(this.client));
    }

    @Test
    void ShouldUpdateAnExistentClient() {
        when(repositoryAdapter.update(refEq(this.client)))
                .thenReturn(this.client);

        var result = underTest.update(this.client);

        assertTrue(Objects.nonNull(result));
        verify(repositoryAdapter,times(1)).update(refEq(this.client));
    }

    @Test
    void ShouldDeleteAClientById() {
        doNothing().when(repositoryAdapter).delete(123L);
        underTest.delete(123L);
        verify(repositoryAdapter,times(1)).delete(123L);
    }

}