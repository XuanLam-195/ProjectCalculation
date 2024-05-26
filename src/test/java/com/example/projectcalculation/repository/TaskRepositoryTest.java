package com.example.projectcalculation.repository;

import com.example.projectcalculation.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2schema.sql")
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void findAllTaskBySupProject_1(){
        int exceptedResult = 1;
        int actualResult = taskRepository.findAllBySubproject(1L).size();
        System.out.println("Size subproject belong to project 1" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }

    @ Test
    void createTaskBySubProject_1(){
        TaskModel taskModel = new TaskModel(null, 1L, "taskName", "taskDescription", LocalDate.now(), LocalDate.now(), 1, 1L, 1, 1L, null);
        taskRepository.create(taskModel);
        int exceptedResult = 2;
        int actualResult = taskRepository.findAllBySubproject(1L).size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);

    }
}


