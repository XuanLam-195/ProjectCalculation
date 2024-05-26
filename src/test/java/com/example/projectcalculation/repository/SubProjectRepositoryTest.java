package com.example.projectcalculation.repository;


import com.example.projectcalculation.model.SubProjectModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2schema.sql")
public class SubProjectRepositoryTest {

    @Autowired
    SubProjectRepository subProjectRepository;

    @Test
    void findAllSubProject(){
        int exceptedResult = 1;
        int actualResult = subProjectRepository.findAllByProjectId(1L).size();
        System.out.println("Size subproject belong to project 1" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }

    @ Test
    void createSubProject(){
        SubProjectModel subProjectModel = new SubProjectModel(null, "projectName", 1L, null, null);
        subProjectRepository.createProject(subProjectModel);
        int exceptedResult = 2;
        int actualResult = subProjectRepository.findAllByProjectId(1L).size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);

    }
}

