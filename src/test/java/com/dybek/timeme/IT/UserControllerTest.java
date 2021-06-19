package com.dybek.timeme.IT;

import com.dybek.timeme.dto.UserDTO;
import org.junit.jupiter.api.*;

import static com.dybek.timeme.domain.jooq.Tables.WORKSPACE_USER;
import static org.junit.jupiter.api.Assertions.*;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends AbstractIT {
    @BeforeEach
    public void setUp() {
        clearKeycloakUsers();
    }

    @Test
    void whenUserRegisterShouldReturn200() throws Exception {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");
        userDTO.setUsername("test");
        userDTO.setPassword("test");

        // when
        MvcResult result = mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();


        String body = result.getResponse().getContentAsString();
        UserRepresentation keycloakUser = objectMapper.readValue(body, UserRepresentation.class);

       var userId =  dsl.select(WORKSPACE_USER.USER_ID)
                .from(WORKSPACE_USER)
                .where(WORKSPACE_USER.USER_ID.equal(UUID.fromString(keycloakUser.getId())))
                .fetchOne();

       // then
        assertEquals(userDTO.getEmail(), keycloakUser.getEmail());
        assertEquals(keycloakUser.getId(), userId.get(WORKSPACE_USER.USER_ID).toString());
    }

    @Test
    void whenUserRegisterWithSameUsernameShouldReturn503() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");
        userDTO.setUsername("test");
        userDTO.setPassword("test");

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isServiceUnavailable());
    }

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
