package com.dybek.timeme.IT;

import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.dto.TaskDTO;
import com.dybek.timeme.dto.UserDTO;
import com.dybek.timeme.exception.KeycloakUserCreationFailedException;
import com.dybek.timeme.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.net.URISyntaxException;
import java.util.UUID;

import static com.dybek.timeme.domain.jooq.Tables.WORKSPACE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TaskControllerTest extends AbstractIT {

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
        taskDTO.setTitle("Testowe zadanie");
        taskDTO.setWorkspaceId(workspaceUser.getWorkspaceId());
        taskDTO.setWorkspaceUserId(workspaceUser.getId());

        mockMvc.perform(post("/api/task/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk());
    }
}
