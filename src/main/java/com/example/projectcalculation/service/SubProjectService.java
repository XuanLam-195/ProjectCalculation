package com.example.projectcalculation.service;

import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.repository.SubProjectRepository;
import com.example.projectcalculation.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubProjectService {

    @Autowired
    SubProjectRepository subProjectRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<SubProjectModel> finAllSubProject(Long projectId) {
        List<SubProjectModel> subProjectModelList = subProjectRepository.findAllByProjectId(projectId);
        for (SubProjectModel subProject : subProjectModelList) {
            subProject.setTaskModelList(taskRepository.findAllBySubproject(subProject.getId()));
        }
        return subProjectModelList;
    }


    public void createProject(SubProjectModel newProject) {
        subProjectRepository.createProject(newProject);
    }


    public void updateProject(SubProjectModel updateProject) {
        subProjectRepository.updateProject(updateProject);
    }

    public SubProjectModel findProjectByID(Long id) {
        return subProjectRepository.findProjectByID(id);
    }

    public List<SubProjectModel> findAllByProjectId(Long projectId) {
        List<SubProjectModel> subProjectModelList = subProjectRepository.findAllByProjectId(projectId);
        for (SubProjectModel subProject : subProjectModelList) {
            subProject.setTaskModelList(taskRepository.findAllBySubproject(subProject.getId()));
        }

        return subProjectModelList;
    }


    public String delete(Long id) {
        SubProjectModel subProjectModel = subProjectRepository.findProjectByID(id);
        if (subProjectModel == null)
            return "Delete fail, SubProject is not exist!";
        List<TaskModel> taskModelList = taskRepository.findAllBySubproject(id);
        if (taskModelList != null && !taskModelList.isEmpty()) {
            return "Delete fail, SubProject have multitasks!";
        }
        subProjectRepository.delete(id);
        return "Delete " + subProjectModel.getProjectName() + " is success!";
    }
}

