package com.dybek.timeme.unit;

import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.domain.repository.TaskRepository;
import com.dybek.timeme.domain.repository.WorkspaceRepository;
import com.dybek.timeme.domain.repository.WorkspaceUserRepository;
import com.dybek.timeme.dto.TaskDTO;
import com.dybek.timeme.exception.WorkspaceNotFoundException;
import com.dybek.timeme.exception.WorkspaceUserNotFoundException;
import com.dybek.timeme.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private WorkspaceUserRepository workspaceUserRepository;
    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldThrowExceptionIfWorkspaceUserWasNotFound() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Random name");
        taskDTO.setWorkspaceId(UUID.randomUUID());
        taskDTO.setWorkspaceUserId(UUID.randomUUID());
        when(workspaceUserRepository.fetchById(any(UUID.class))).thenReturn(new ArrayList<>());
        assertThrows(WorkspaceUserNotFoundException.class, () -> taskService.create(taskDTO));
    }

    @Test
    void shouldThrowExceptionIfWorkspaceWasNotFound() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Random name");
        taskDTO.setWorkspaceId(UUID.randomUUID());
        taskDTO.setWorkspaceUserId(UUID.randomUUID());
        when(workspaceUserRepository.fetchById(any(UUID.class))).thenReturn(Collections.singletonList(new WorkspaceUser()));
        when(workspaceRepository.fetchById(any(UUID.class))).thenReturn(new ArrayList<>());
        assertThrows(WorkspaceNotFoundException.class, () -> taskService.create(taskDTO));
    }

}
