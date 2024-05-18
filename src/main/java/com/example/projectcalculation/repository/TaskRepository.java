package com.example.projectcalculation.repository;

import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.utilities.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    @Autowired
    ConnectionManager connectionManager;

    public void create(TaskModel taskModel){
        try{
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO task (subproject_id, task_name, task_description, " +
                    "planned_start_date, planned_finish_date) " +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(CREATE_QUERY);
            pstmt.setLong(1, taskModel.getSubProjectId());
            pstmt.setString(2, taskModel.getTaskName());
            pstmt.setString(3, taskModel.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(taskModel.getPlannedStartDate()));
            pstmt.setDate(5, Date.valueOf(taskModel.getPlannedFinishDate()));
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Could not add Task");
            e.printStackTrace();
        }
    }

    public void update (TaskModel taskModel){
        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "UPDATE task SET subproject_id= ?, task_name =?, task_description=?," +
                    "planned_start_date = ?, planned_finish_date = ?, assigned =?, " +
                    "duration = ?, actual_hours = ?, actual_resource_cost =? " +
                    " WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, taskModel.getSubProjectId());
            pstmt.setString(2, taskModel.getTaskDescription());
            pstmt.setString(3, taskModel.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(taskModel.getPlannedStartDate()));
            pstmt.setDate(5, Date.valueOf(taskModel.getPlannedFinishDate()));
            pstmt.setLong(6, taskModel.getAssigned());
            pstmt.setInt(7, taskModel.getDuration());
            pstmt.setInt(8, taskModel.getActualHours());
            pstmt.setLong(9, taskModel.getActualResourceCost());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not update Task");
        }
    }

    public List<TaskModel> findAllByTask(Long taskId){
        List<TaskModel> taskModelList = new ArrayList<>();

        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM task WHERE project_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, taskId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                TaskModel taskModel = new TaskModel();
                int number = 1;
                taskModel.setId(resultSet.getLong(number++));
                taskModel.setSubProjectId(resultSet.getLong(number++));
                taskModel.setTaskName(resultSet.getString(number++));
                taskModel.setTaskDescription(resultSet.getString(number++));
                taskModel.setPlannedStartDate(resultSet.getDate(number++).toLocalDate());
                taskModel.setPlannedFinishDate((resultSet.getDate(number++).toLocalDate()));
                taskModel.setDuration((resultSet.getInt(number++)));
                taskModel.setAssigned(resultSet.getLong(number++));
                taskModel.setActualHours(resultSet.getInt(number++));
                taskModel.setActualResourceCost(resultSet.getLong(number++));

                taskModelList.add(taskModel);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find Task by ID");
        }
        return taskModelList;
    }
}
