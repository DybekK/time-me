package com.dybek.timeme.service;

import com.dybek.timeme.domain.jooq.tables.pojos.Task;
import com.dybek.timeme.domain.jooq.tables.pojos.Workspace;
import com.dybek.timeme.domain.jooq.tables.pojos.WorkspaceUser;
import com.dybek.timeme.domain.repository.TaskRepository;
import com.dybek.timeme.domain.repository.WorkspaceRepository;
import com.dybek.timeme.domain.repository.WorkspaceUserRepository;
import com.dybek.timeme.dto.TaskDTO;
import com.dybek.timeme.exception.WorkspaceNotFoundException;
import com.dybek.timeme.exception.WorkspaceUserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public TaskService(TaskRepository taskRepository, WorkspaceUserRepository workspaceUserRepository, WorkspaceRepository workspaceRepository) {
        this.taskRepository = taskRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public Task create(TaskDTO taskDTO) throws WorkspaceUserNotFoundException, WorkspaceNotFoundException {
        // null checking
        Optional<WorkspaceUser> workspaceUser = workspaceUserRepository.fetchById(taskDTO.getWorkspaceUserId()).stream().findFirst();
        workspaceUser.orElseThrow(WorkspaceUserNotFoundException::new);

        // null checking
        Optional<Workspace> workspace = workspaceRepository.fetchById(taskDTO.getWorkspaceId()).stream().findFirst();
        workspace.orElseThrow(WorkspaceNotFoundException::new);


        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.insert(task);
        return task;
    }
}
