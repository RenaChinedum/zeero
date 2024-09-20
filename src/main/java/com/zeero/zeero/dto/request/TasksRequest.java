package com.zeero.zeero.dto.request;

import com.zeero.zeero.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class TasksRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
}
