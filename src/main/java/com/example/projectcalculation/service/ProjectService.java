package com.example.projectcalculation.service;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public void createProject(ProjectModel newProject) {
        projectRepository.createProject(newProject);
    }


    public void updateProject(ProjectModel updateProject) {
        projectRepository.updateProject(updateProject);
    }

    public ProjectModel findProjectByID(Long id) {
        return projectRepository.findProjectByID(id);
    }

    public List<ProjectModel> findAll(){
        return projectRepository.findAll();
    }

    public String delete(Long id) {
        ProjectModel projectModel = projectRepository.findProjectByID(id);
        if (projectModel == null)
            return "Delete fail, Project is not exist!";
        if (projectModel.getProjectStatus())
            return "Delete fail, Project is active";
        projectRepository.delete(id);
        return "Delete " + projectModel.getProjectName() + " is success";
    }
}
