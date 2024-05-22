package com.example.projectcalculation.repository;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.utilities.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Autowired
    ConnectionManager connectionManager;

    public void createProject(ProjectModel newProject) {
        try {
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO project (project_name, project_manager, target_date, project_description, budget) VALUES (?,?,?,?,?)";
            //prepare QUERY
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());
            preparedStatement.setString(2, newProject.getProjectManager());
            preparedStatement.setDate(3, Date.valueOf(newProject.getTargetDate()));
            preparedStatement.setString(4, newProject.getProjectDescription());
            preparedStatement.setLong(5, newProject.getBudget());

            //EXECUTE QUERY
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add Project");
            e.printStackTrace();
        }
    }


    public void updateProject(ProjectModel updateProject) {
        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "UPDATE project SET project_name = ?, project_description = ?, project_manager = ?, project_status = ?, target_date = ?, budget = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);


            preparedStatement.setString(1, updateProject.getProjectName());
            preparedStatement.setString(2, updateProject.getProjectDescription());
            preparedStatement.setString(3, updateProject.getProjectManager());
            preparedStatement.setBoolean(4, updateProject.getProjectStatus());
            preparedStatement.setDate(5, Date.valueOf(updateProject.getTargetDate())); //sætter den konverteret java.sql.date som project deadline
            preparedStatement.setLong(6, updateProject.getBudget());
            preparedStatement.setLong(7, updateProject.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not update project");
        }
    }

    //Take a integer parameter, and finds the project object that has this id and creates a new object to return
    public ProjectModel findProjectByID(Long id) {
        ProjectModel foundProject = new ProjectModel();
        foundProject.setId(id);
        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM project WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                foundProject.setProjectName(resultSet.getString(2));
                foundProject.setProjectDescription(resultSet.getString(3));
                foundProject.setProjectManager(resultSet.getString(4));
                foundProject.setProjectStatus(resultSet.getBoolean(5));
                foundProject.setTargetDate(resultSet.getDate(6).toLocalDate());
                foundProject.setBudget(resultSet.getLong(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find Project");
        }
        return foundProject;
    }


    public List<ProjectModel> findAll() {
        List<ProjectModel> workspaceProjects = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM project ";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                ProjectModel foundProject = new ProjectModel();
                foundProject.setId(resultset.getLong(1));
                foundProject.setProjectName(resultset.getString(2));
                foundProject.setProjectDescription(resultset.getString(3));
                foundProject.setProjectManager(resultset.getString(4));
                foundProject.setProjectStatus(resultset.getBoolean(5));
                foundProject.setTargetDate(resultset.getDate(6).toLocalDate());
                workspaceProjects.add(foundProject);
                //toLocalDate tilføjet for at kunne omskrive java.SQL.date fra databasen til LocalDate objet. da vi bruger localdate some dato attribut
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find project by id");
        }
        return workspaceProjects;
    }

    public void delete(Long id){

        try {
            Connection connection = connectionManager.getConnection();
            final String SQL_QUERY = "DELETE FROM project WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, id);
            pstmt.execute();
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllProject. ");
            e.printStackTrace();
        }
    }
}



