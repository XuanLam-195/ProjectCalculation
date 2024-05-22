package com.example.projectcalculation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubProjectModel {
    private Long id;
    private String projectName;
    private Long projectId;
    private List<TaskModel> taskModelList;

    @Override
    public String toString() {
        return "Project{" + "projectID=" + id + ", projectName='" + projectName + '\'' + '}';
    }
}
