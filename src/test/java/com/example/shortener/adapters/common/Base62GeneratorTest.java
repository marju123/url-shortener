package com.example.shortener.adapters.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Base62GeneratorTest {

    @Test
    void shouldGenerateBase62EncodedString() {
        // given
        String input = "www.test.com";
        // when
        String encoded = Base62Generator.encodeFromString(input);
        // then
        assertNotNull(encoded);
        assertEquals(8, encoded.length());
    }
}