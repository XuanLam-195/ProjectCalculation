package com.example.projectcalculation.model;


import com.example.projectcalculation.dto.ReportUserTime;
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

    private List<ReportUserTime> reportUserTimes;




    @Override
    public String toString() {
        return "Project{" + "projectID=" + id + ", projectName='" + projectName + '\'' + '}';
    }
}
