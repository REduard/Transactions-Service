package com.n26.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractRestControllerTest {

    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
