package com.example.projectcalculation.repository;

import com.example.projectcalculation.dto.ReportUserTime;
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
                    "planned_start_date, planned_finish_date, assigned) " +
                    "VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(CREATE_QUERY);
            pstmt.setLong(1, taskModel.getSubProjectId());
            pstmt.setString(2, taskModel.getTaskName());
            pstmt.setString(3, taskModel.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(taskModel.getPlannedStartDate()));
            pstmt.setDate(5, Date.valueOf(taskModel.getPlannedFinishDate()));
            pstmt.setLong(6, taskModel.getAssigned());
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Could not add Task");
            e.printStackTrace();
        }
    }

    public void update(TaskModel taskModel){
        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "UPDATE task SET subproject_id= ?, task_name =?, task_description=?," +
                    "planned_start_date = ?, planned_finish_date = ?, assigned =?, " +
                    "duration = ?, actual_hours = ?, actual_resource_cost =? " +
                    " WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, taskModel.getSubProjectId());
            pstmt.setString(2, taskModel.getTaskName());
            pstmt.setString(3, taskModel.getTaskDescription());
            pstmt.setDate(4, Date.valueOf(taskModel.getPlannedStartDate()));
            pstmt.setDate(5, Date.valueOf(taskModel.getPlannedFinishDate()));
            pstmt.setLong(6, taskModel.getAssigned());
            pstmt.setInt(7, taskModel.getDuration());
            pstmt.setInt(8, taskModel.getActualHours());
            pstmt.setLong(9, taskModel.getActualResourceCost());
            pstmt.setLong(10, taskModel.getId());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not update Task");
        }
    }


    public TaskModel findByID(Long id) {

        try {
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM task where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                TaskModel taskModel = new TaskModel();
                int number = 1;
                taskModel.setId(resultset.getLong(number++));
                taskModel.setSubProjectId(resultset.getLong(number++));
                taskModel.setTaskName(resultset.getString(number++));
                taskModel.setTaskDescription(resultset.getString(number++));
                taskModel.setPlannedStartDate(resultset.getDate(number++).toLocalDate());
                taskModel.setPlannedFinishDate(resultset.getDate(number++).toLocalDate());
                taskModel.setDuration(resultset.getInt(number++));
                taskModel.setAssigned(resultset.getLong(number++));
                taskModel.setActualHours(resultset.getInt(number++));
                taskModel.setActualResourceCost(resultset.getLong(number++));
                return  taskModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not find task by id");

        }
        return null;
    }

    public List<TaskModel> findAllBySubproject(Long projectId){
        List<TaskModel> taskModelList = new ArrayList<>();
        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM task t LEFT JOIN users u ON t.assigned = u.id WHERE subproject_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, projectId);
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
                taskModel.setAssignedName(resultSet.getString("email"));
                taskModelList.add(taskModel);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find Subproject by ID");
        }
        return taskModelList;
    }

    public void delete(Long id){

        try {
            Connection connection = connectionManager.getConnection();

            final String SQL_QUERY = "DELETE FROM task WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, id);
            pstmt.execute();
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllTask. ");
            e.printStackTrace();
        }
    }

    public List<TaskModel> findAllByCurrentUser(Long userId){
        List<TaskModel> taskModelList = new ArrayList<>();
        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT * FROM task t LEFT JOIN users u ON t.assigned = u.id WHERE t.assigned = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, userId);
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
                taskModel.setAssignedName(resultSet.getString("email"));
                taskModelList.add(taskModel);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find current user by ID");
        }
        return taskModelList;
    }


    public List<ReportUserTime> getAllReportUserTime(Long subProjectId){
        List<ReportUserTime> reportUserTimes = new ArrayList<>();

        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT u.id, u.email, SUM(t.actual_hours)" +
                    "FROM task t JOIN users u ON t.assigned = u.id WHERE t.subproject_id = ? GROUP BY t.assigned";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, subProjectId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                ReportUserTime reportUserTime = new ReportUserTime();
                int number = 1;
                reportUserTime.setAssigned(resultSet.getLong(number++));
                reportUserTime.setName(resultSet.getString(number++));
                reportUserTime.setTotalTime((resultSet.getInt(number++)));
                reportUserTimes.add(reportUserTime);
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find project by id");
        }
        return reportUserTimes;
    }



    public List<ReportUserTime> getAllReportUserTimeByProject(Long accountId){
        List<ReportUserTime> reportUserTimes = new ArrayList<>();

        try{
            Connection connection = connectionManager.getConnection();
            String SQL_QUERY = "SELECT p.project_name, SUM(t.actual_hours)" +
                    " FROM task t JOIN subproject sp ON t.subproject_id = sp.id" +
                    " JOIN project p ON p.id = sp.project_id" +
                    " WHERE t.assigned = ? GROUP BY p.id";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, accountId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                ReportUserTime reportUserTime = new ReportUserTime();
                int number = 1;
                reportUserTime.setName(resultSet.getString(number++));
                reportUserTime.setTotalTime((resultSet.getInt(number++)));
                reportUserTimes.add(reportUserTime);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not find project by id");
        }
        return reportUserTimes;
    }
}
