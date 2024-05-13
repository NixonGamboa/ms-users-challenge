package com.gamboatech.infrastructure.entrypoints.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceCheckControllerTest {

    @InjectMocks
    private ServiceCheckController controller;
    @Test
    void serviceCheck() {
        var result = controller.serviceCheck();
        assertEquals("I'm alive!", result);
    }
}