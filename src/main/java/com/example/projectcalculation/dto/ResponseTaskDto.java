package com.example.projectcalculation.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseTaskDto {
    String tasksHeader;
    List<TaskDto> tasks;

    }

