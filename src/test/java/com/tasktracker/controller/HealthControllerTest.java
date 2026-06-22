
package com.tasktracker.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
class HealthControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataSource dataSource;

    @Test
    void shouldReturnHealth() throws Exception {

        mockMvc.perform(get("/healthz"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.status")
                                .value("ok")
                );
    }

    @Test
    void shouldReturnReady() throws Exception {

        Connection connection =
                mock(Connection.class);

        when(dataSource.getConnection())
                .thenReturn(connection);

        when(connection.isValid(2))
                .thenReturn(true);

        mockMvc.perform(get("/readyz"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.status")
                                .value("ready")
                );
    }

    @Test
    void shouldReturnNotReady() throws Exception {

        when(dataSource.getConnection())
                .thenThrow(
                        new SQLException()
                );

        mockMvc.perform(get("/readyz"))
                .andExpect(
                        status()
                                .isServiceUnavailable()
                )
                .andExpect(
                        jsonPath("$.status")
                                .value("not-ready")
                );
    }
}
