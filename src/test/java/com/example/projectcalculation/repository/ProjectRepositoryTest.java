package com.example.projectcalculation.repository;

import com.example.projectcalculation.model.ProjectModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2schema.sql")
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void findAllProject(){
        int exceptedResult = 1;
        int actualResult = projectRepository.findAll().size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }

    @ Test
    void createProject(){
        ProjectModel projectModel = new ProjectModel(null, "projectName", "projectManager", true, LocalDate.now(), "projectDescription", 100L);
        projectRepository.createProject(projectModel);
        int exceptedResult = 2;
        int actualResult = projectRepository.findAll().size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);

    }

    @Test
    void updateProject() {

        ProjectModel updateProject = new ProjectModel();
        updateProject.setId(1L);
        updateProject.setProjectName("Updated Project Name");
        updateProject.setProjectDescription("Updated Description");
        updateProject.setProjectManager("New Manager");
        updateProject.setProjectStatus(true);
        updateProject.setTargetDate(LocalDate.of(2023,12,31));
        updateProject.setBudget(50000L);

        // Thực hiện cập nhật
        projectRepository.updateProject(updateProject);

        // Truy xuất đối tượng sau khi cập nhật
        ProjectModel updatedProject = projectRepository.findProjectByID(1L);

        // Kiểm tra các giá trị
        assertNotNull(updatedProject);
        assertEquals("Updated Project Name", updatedProject.getProjectName());
        assertEquals("Updated Description", updatedProject.getProjectDescription());
        assertEquals("New Manager", updatedProject.getProjectManager());
        assertEquals(true, updatedProject.getProjectStatus());
        assertEquals(LocalDate.of(2023,12,31), updatedProject.getTargetDate());
        assertEquals(50000L, updatedProject.getBudget());
    }

    @Test
    void findProjectByID() {
        Long projectId = 1L;

        // Thực hiện truy vấn
        ProjectModel foundProject = projectRepository.findProjectByID(projectId);

        // Kiểm tra kết quả
        assertNotNull(foundProject);
        assertEquals(projectId, foundProject.getId());
        assertEquals("Project test", foundProject.getProjectName());
        assertEquals("Project Description", foundProject.getProjectDescription());
        assertEquals("Lam", foundProject.getProjectManager());
        assertEquals(true, foundProject.getProjectStatus());
        assertEquals(LocalDate.of(2024, 1, 3), foundProject.getTargetDate());
        assertEquals(100000L, foundProject.getBudget());
    }

    @Test
    void findAll() {
        // Thực hiện truy vấn
        List<ProjectModel> projects = projectRepository.findAll();

        // Kiểm tra kết quả
        assertNotNull(projects);
        assertEquals(1, projects.size());

        ProjectModel project1 = projects.get(0);
        assertEquals(1L, project1.getId());
        assertEquals("Project test", project1.getProjectName());
        assertEquals("Project Description", project1.getProjectDescription());
        assertEquals("Lam", project1.getProjectManager());
        assertEquals(true, project1.getProjectStatus());
        assertEquals(LocalDate.of(2024, 1, 3), project1.getTargetDate());
        assertEquals(null, project1.getBudget());
    }
}



