package com.example.projectcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {
    private Long id;
    private String projectName;
    private String projectManager;
    private Boolean projectStatus;
    private LocalDate targetDate;
    private String projectDescription;
    private Long budget;

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
