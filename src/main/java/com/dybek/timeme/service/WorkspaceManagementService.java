package com.dybek.timeme.service;

import com.dybek.timeme.domain.jooq.tables.pojos.Workspace;
import com.dybek.timeme.domain.repository.WorkspaceRepository;
import com.dybek.timeme.dto.WorkspaceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceManagementService {
    private final WorkspaceRepository workspaceRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public WorkspaceManagementService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public void createWorkspace(WorkspaceDTO workspaceDTO) {
        Workspace workspace = modelMapper.map(workspaceDTO, Workspace.class);
        workspaceRepository.insert(workspace);
    }
}
