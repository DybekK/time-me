package com.dybek.timeme.IT;

import com.dybek.timeme.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends AbstractIT {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenUserRegisterRequestIsNotValidShouldReturn400() throws Exception {
        // email is missing
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("test");

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());

        //email is invalid
        userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setEmail("test@.");
        userDTO.setPassword("test");

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());

        // username is missing
        userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");
        userDTO.setPassword("test");

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());

        // password is missing
        userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setEmail("test@email.com");

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }
}
