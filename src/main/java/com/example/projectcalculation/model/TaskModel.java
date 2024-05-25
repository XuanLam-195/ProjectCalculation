package com.example.projectcalculation.model;

import com.example.projectcalculation.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    private Long id;
    private Long subProjectId;
    private String taskName;
    private String taskDescription;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate plannedFinishDate;
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate plannedStartDate;
    private Integer duration;
    private Long assigned;
    private Integer actualHours;
    private Long actualResourceCost;
    private String assignedName;

    public TaskDto toTaskDto() {
        TaskDto taskDto = new TaskDto();
        taskDto.setDuration(this.getDuration());
        taskDto.setName(this.getTaskName());
        taskDto.setStart(this.getPlannedStartDate().atStartOfDay().toEpochSecond(ZoneOffset.of("+02:00")) * 1000);
        taskDto.setEnd(this.getPlannedFinishDate().atStartOfDay().toEpochSecond(ZoneOffset.of("+02:00")) * 1000);
        taskDto.setId(this.getId());
        return taskDto;
    }

}
