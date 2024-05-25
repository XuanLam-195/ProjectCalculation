package com.example.projectcalculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUserTime {
    private Long assigned;
    private String name;
    Integer totalTime;
}
