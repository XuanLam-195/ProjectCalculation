package com.example.projectcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {
    Long id;
    String projectName;
    String projectManager;
    Boolean projectStatus;
    LocalDate targetDate;
    String projectDescription;
    Long budget;

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectOwner='" + projectManager + '\'' +
                ", projectStatus=" + projectStatus +
                ", deadline=" + targetDate +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }
}
