package com.example.projectcalculation.repository;


import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectRepository {

    @Autowired
    ConnectionManager connectionManager;

    //takes a project as parameter and makes a prepared statement of information from this object, and shoots it to the database
    public void createProject(SubProjectModel newProject) {
        try {
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO subproject (project_name, project_id) VALUES (?,?)";
            //prepare QUERY
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());
            preparedStatement.setLong(2, newProject.getProjectId());

            //EXECUTE QUERY
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add Project");
            e.printStackTrace();
        }
    }


    //Takes an updated project object and shoots it to the database as a preparedstatement
    public void updateProject(SubProjectModel updateProject) {
        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "UPDATE subproject SET project_name = ?, project_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);


            preparedStatement.setString(1, updateProject.getProjectName());
            preparedStatement.setLong(2, updateProject.getProjectId());
            preparedStatement.setLong(3, updateProject.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not update project");
        }
    }

    //Take a integer parameter, and finds the project object that has this id and creates a new object to return
    public SubProjectModel findProjectByID(Long id) {
        SubProjectModel foundProject = new SubProjectModel();
        foundProject.setId(id);
        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM subproject WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                foundProject.setProjectName(resultSet.getString(2));
                foundProject.setProjectId(resultSet.getLong(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find Project");
        }
        return foundProject;
    }


    public List<SubProjectModel> findAllByProjectId(Long projectId) {
        List<SubProjectModel> workspaceProjects = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM subproject where project_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setLong(1, projectId);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                SubProjectModel foundProject = new SubProjectModel();
                foundProject.setId(resultset.getLong(1));
                foundProject.setProjectName(resultset.getString(2));
                foundProject.setProjectId(projectId);
                workspaceProjects.add(foundProject);
                //toLocalDate tilføjet for at kunne omskrive java.SQL.date fra databasen til LocalDate objet. da vi bruger localdate some dato attribut
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find project by id");
        }
        return workspaceProjects;
    }
}
