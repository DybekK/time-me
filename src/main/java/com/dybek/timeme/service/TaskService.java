package com.dybek.timeme.service;

import com.dybek.timeme.domain.jooq.tables.pojos.Task;
import com.dybek.timeme.domain.repository.TaskRepository;
import com.dybek.timeme.dto.TaskDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public TaskService(TaskRepository taskDao) {
        this.taskRepository = taskDao;
    }

    public Task create(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        taskRepository.insert(task);
        return task;
    }
}
