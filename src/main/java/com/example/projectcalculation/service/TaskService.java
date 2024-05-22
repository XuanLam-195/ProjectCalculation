package com.example.projectcalculation.service;

import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public void create(TaskModel taskModel) {
        taskRepository.create(taskModel);
    }


    public void update(TaskModel taskModel) {
        taskRepository.update(taskModel);
    }

    public TaskModel findByID(Long id) {

        return taskRepository.findByID(id);
    }

    public List<TaskModel> findAllBySubProject(Long projectId) {
        return taskRepository.findAllBySubproject(projectId);
    }

    public String delete(Long id) {
        TaskModel taskModel = taskRepository.findByID(id);
        if (taskModel == null)
            return "Delete fail, Task is not exist!";
        taskRepository.delete(id);
        return "Delete " + taskModel.getTaskName() + " is success!";
    }

    public List<TaskModel> findAllByCurrentUser(Long userId) {
        return taskRepository.findAllByCurrentUser(userId);
    }
}
