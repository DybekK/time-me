package com.dybek.timeme.unit;

import com.dybek.timeme.domain.jooq.tables.pojos.Task;
import com.dybek.timeme.domain.jooq.tables.pojos.Workspace;
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
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    private final ModelMapper modelMapper = new ModelMapper();
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

    @Test
    void shouldReturnTask() throws WorkspaceUserNotFoundException, WorkspaceNotFoundException {
        // given
        String title = "Random name";
        UUID workspaceId = UUID.randomUUID();
        UUID workspaceUserId = UUID.randomUUID();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(title);
        taskDTO.setWorkspaceId(workspaceId);
        taskDTO.setWorkspaceUserId(workspaceUserId);

        Task expected = modelMapper.map(taskDTO, Task.class);

        // when
        when(workspaceUserRepository.fetchById(any(UUID.class))).thenReturn(Collections.singletonList(new WorkspaceUser()));
        when(workspaceRepository.fetchById(any(UUID.class))).thenReturn(Collections.singletonList(new Workspace()));
        doNothing().when(taskRepository).insert(any(Task.class));

        // then
        Task result = taskService.create(taskDTO);

        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getWorkspaceId(), result.getWorkspaceId());
        assertEquals(expected.getWorkspaceUserId(), result.getWorkspaceUserId());
    }
}
