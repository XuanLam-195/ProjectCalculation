package com.example.projectcalculation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data// auto generate getter setter
@AllArgsConstructor
@NoArgsConstructor
public class SubProjectModel {
    Long id;
    String projectName;
    Long projectId;


    @Override
    public String toString() {
        return "Project{" + "projectID=" + id + ", projectName='" + projectName + '\'' + '}';
    }
}
