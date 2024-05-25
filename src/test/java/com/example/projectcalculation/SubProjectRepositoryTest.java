package com.example.projectcalculation;

import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.repository.SubProjectRepository;
import com.example.projectcalculation.utilities.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest

@ExtendWith(MockitoExtension.class)
public class SubProjectRepositoryTest {

    @Mock
    private ConnectionManager connectionManager;

    @Mock
    private Connection connection;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private SubProjectRepository subProjectRepository;

    @BeforeEach
    void setUp() throws SQLException {
       lenient().when(connectionManager.getConnection()).thenReturn(connection);
        lenient().when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        lenient().when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testCreateProject() throws SQLException {
        SubProjectModel newProject = new SubProjectModel();
        newProject.setProjectName("Test Project");
        newProject.setProjectId(1L);

        subProjectRepository.createProject(newProject);

        verify(preparedStatement).setString(1, "Test Project");
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }


    @Test
    void testUpdateProject() throws SQLException {
        SubProjectModel updateProject = new SubProjectModel();
        updateProject.setId(1L);
        updateProject.setProjectName("Updated Project");
        updateProject.setProjectId(2L);

        subProjectRepository.updateProject(updateProject);

        verify(preparedStatement).setString(1, "Updated Project");
        verify(preparedStatement).setLong(2, 2L);
        verify(preparedStatement).setLong(3, 1L);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testFindProjectByID() throws SQLException {
        Long projectId = 1L;

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(2)).thenReturn("Found Project");
        when(resultSet.getLong(3)).thenReturn(2L);

        SubProjectModel foundProject = subProjectRepository.findProjectByID(projectId);

        assertNotNull(foundProject);
        assertEquals(projectId, foundProject.getId());
        assertEquals("Found Project", foundProject.getProjectName());
        assertEquals(2L, foundProject.getProjectId());

        verify(preparedStatement).setLong(1, projectId);
        verify(resultSet).next();
    }

    @Test
    void testFindAllByProjectId() throws SQLException {
        Long projectId = 1L;

        // Set up the mock ResultSet to return multiple rows
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong(1)).thenReturn(1L, 2L);
        when(resultSet.getString(2)).thenReturn("Project A", "Project B");

        List<SubProjectModel> projects = subProjectRepository.findAllByProjectId(projectId);

        assertNotNull(projects);
        assertEquals(2, projects.size());

        SubProjectModel firstProject = projects.get(0);
        assertEquals(1L, firstProject.getId());
        assertEquals("Project A", firstProject.getProjectName());
        assertEquals(projectId, firstProject.getProjectId());

        SubProjectModel secondProject = projects.get(1);
        assertEquals(2L, secondProject.getId());
        assertEquals("Project B", secondProject.getProjectName());
        assertEquals(projectId, secondProject.getProjectId());

        verify(preparedStatement).setLong(1, projectId);
        verify(resultSet, times(3)).next();  // called three times: two true, one false
    }

    @Test
    void testDelete() throws SQLException {
        Long Id = 1L;

        subProjectRepository.delete(Id);

        verify(preparedStatement).setLong(1, Id);
        verify(preparedStatement).execute();
    }

}



