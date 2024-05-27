package com.example.projectcalculation.repository;


import com.example.projectcalculation.model.TaskModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void updateTask() {
        // Prepare test data
        TaskModel originalTask = taskRepository.findByID(1L); // Assuming task with ID 1 exists
        TaskModel updatedTask = new TaskModel(originalTask.getId(), originalTask.getSubProjectId(), "Updated Task Description",
                "Updated Task Description", LocalDate.now(), LocalDate.now(), 1, 2L, 2, 2000L, null);

        // Call update method
        taskRepository.update(updatedTask);

        // Retrieve updated task
        TaskModel retrievedTask = taskRepository.findByID(1L);

        // Assertions
        assertEquals(updatedTask.getId(), retrievedTask.getId());
        assertEquals(updatedTask.getSubProjectId(), retrievedTask.getSubProjectId());
        assertEquals(updatedTask.getTaskName(), retrievedTask.getTaskName());
        assertEquals(updatedTask.getTaskDescription(), retrievedTask.getTaskDescription());
        assertEquals(updatedTask.getPlannedStartDate(), retrievedTask.getPlannedStartDate());
        assertEquals(updatedTask.getPlannedFinishDate(), retrievedTask.getPlannedFinishDate());
        assertEquals(updatedTask.getAssigned(), retrievedTask.getAssigned());
        assertEquals(updatedTask.getDuration(), retrievedTask.getDuration());
        assertEquals(updatedTask.getActualHours(), retrievedTask.getActualHours());
        assertEquals(updatedTask.getActualResourceCost(), retrievedTask.getActualResourceCost());
    }

    @Test
    void deleteTask() {
        // Forbered testdata - Opret en ny opgave
        TaskModel taskModel = new TaskModel(null, 1L, "taskName", "taskDescription", LocalDate.now(), LocalDate.now(), 1, 1L, 1, 1L, null);
        taskRepository.create(taskModel);

        // Indhent opgaven fra databasen for at få dens ID
        TaskModel createdTask = taskRepository.findAllBySubproject(1L).get(0);
        Long taskId = createdTask.getId();

        // Kald delete-metoden
        taskRepository.delete(taskId);

        // Bekræft at opgaven ikke længere findes i databasen
        TaskModel deletedTask = taskRepository.findByID(taskId);

        // Assertion: Kontroller om den slettede opgave er null
        assertNull(deletedTask, "Opgaven blev ikke slettet korrekt fra databasen.");
    }

}


