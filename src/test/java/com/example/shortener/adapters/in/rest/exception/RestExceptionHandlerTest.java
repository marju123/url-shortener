package com.example.shortener.adapters.in.rest.exception;

import com.example.shortener.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestExceptionHandlerTest {

    private final RestExceptionHandler exceptionHandler = new RestExceptionHandler();

    @Test
    void shouldReturnErrorMessage() {
        // given
        String errorMessage = "Alias already in use";
        BusinessException exception = new BusinessException(errorMessage);

        // when
        ResponseEntity<List<String>> response = exceptionHandler.handleBusinessExceptions(exception);

        // then
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(errorMessage));
    }
}