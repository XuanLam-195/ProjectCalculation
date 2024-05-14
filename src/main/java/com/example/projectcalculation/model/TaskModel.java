package com.example.projectcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    Long id;
    Long subProjectId;
    String taskName;
    String taskDescription;
    LocalDate plannedStartDate;
    LocalDate plannedFinishDate;
    Integer duration;
    Long assigned;
    Integer actualHours;
    Long actualResourceCost;

}
