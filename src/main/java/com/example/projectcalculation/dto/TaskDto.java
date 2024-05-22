package com.example.projectcalculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    Long id;
    Integer level = 0;
    String name;
    Long start;
    Long end;
    Integer status;
    String color = "#9acff5";
    Object milestones;
    Integer duration;
    Integer progress;
    String label;
    Boolean collapsed;
}
