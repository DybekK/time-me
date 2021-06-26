package com.dybek.timeme.IT;

import com.dybek.timeme.IT.config.AbstractIT;
import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.dto.TaskDTO;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.WorkspaceNotFoundException;
import com.dybek.timeme.exception.WorkspaceUserNotFoundException;
import com.dybek.timeme.service.user.UserService;
import org.junit.jupiter.api.*;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.UUID;

import static com.dybek.timeme.domain.jooq.Tables.WORKSPACE_USER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TaskControllerTest extends AbstractIT {
    @BeforeEach
    public void setUp() {
        clearKeycloakUsers();
    }

    @Autowired
    UserService userService;

    private WorkspaceUser createUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("test");
        userDTO.setUsername("test");
        userDTO.setEmail("test@email.com");
        UserRepresentation keycloakUser = userService.create(userDTO);

        return dsl.select()
                .from(WORKSPACE_USER)
                .where(WORKSPACE_USER.USER_ID.equal(UUID.fromString(keycloakUser.getId())))
                .fetchOneInto(WorkspaceUser.class);
    }

    @Test
    void canAddTask() throws Exception {
        WorkspaceUser workspaceUser = createUser();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Random name");
        taskDTO.setWorkspaceId(workspaceUser.getWorkspaceId());
        taskDTO.setWorkspaceUserId(workspaceUser.getId());

        mockMvc.perform(post("/api/task/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionIfWorkspaceUserWasNotFound() throws Exception {
        WorkspaceUser workspaceUser = createUser();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Random name");
        taskDTO.setWorkspaceId(workspaceUser.getWorkspaceId());
        // generating random UUID in order to throw exception
        taskDTO.setWorkspaceUserId(UUID.randomUUID());

       mockMvc.perform(post("/api/task/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getCause() instanceof WorkspaceUserNotFoundException));
    }

    @Test
    void shouldThrowExceptionIfWorkspaceWasNotFound() throws Exception {
        WorkspaceUser workspaceUser = createUser();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Random name");
        // generating random UUID in order to throw exception
        taskDTO.setWorkspaceId(UUID.randomUUID());
        taskDTO.setWorkspaceUserId(workspaceUser.getUserId());

        mockMvc.perform(post("/api/task/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getCause() instanceof WorkspaceNotFoundException));
    }
}
