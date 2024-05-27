# CONTRIBUTE

## Prerequisites

- JDK 17: You should have JDK 17 installed on your machine as the project uses Java 17 as mentioned in the `pom.xml`.
- Apache Maven: As this is a Maven project, you need to have Maven installed on your machine.
- MySQL: As the project uses `mysql-connector-j` for database connectivity, you should have MySQL installed and properly set up on your machine.
- An IDE installed like VSCode, IntelliJ, or other.
- Knowledge of SQL database and how to use it and send/retrieve data from it.
- MySQL Workbench, HeidiSQL.
- Microsoft-teams: This is our platform where we keep contact, and you will be added to our group chat.

## Setup

1. Clone the repository.
2. Use git pull
3. thoroughly look through our code and how it's been set up.
4. Set up your own local test database in your MySQL workbench.
5. Set up your own local production database in your MySQL workbench.
6. Run the program locally, we use port 8080, and use all the functions of the program to make sure it is fully functional.

## Workflow

In this project, we follow a feature branch workflow. Each contributor works on a feature in a separate branch. Here is the general process:

1. Create a new branch for your feature. The name of the branch should reflect the feature you're implementing.
2. Implement your feature and commit your changes to this branch.
3. After completing the feature, push the branch to the GitHub repository.
4. Our CI workflow will automatically attempt to rebase and build the branch with Maven. If there are no build failures (excluding tests), the changes will be automatically pushed to the `main` branch.
5. After your changes are in `main`, all team members should pull the latest changes from `main`.


# MySQL database, packet structure, Connector, Render, and Applications.properties

## MySQL database
- We keep two separate folders, with two sql files in each.
    - productionDatabase folder - here we have the production data for MySQL.
        - project_calculation.sql - this file contains the creating of all the tables and data that gets put into the tables.
    - testDatabase folder - here we have the test data for MySQL.
        - h2schema.sql - this file contains the creating of all the tables and data that gets put into the tables.
- All order functions is being handled at the backend, before the data gets send back to the front end.

## Packet structure
Our packets structure looks like this
- controller
- dto
- model
- repository
- service
- util

## How our structure flows
- Project, Subproject, Task, and User all have their own controller, model, repository, and service.
- Each project implements their own interface.
- We implement MVC and follow the principles of it.

## Introduction to our connector

We use a Connector class that looks like this
```
package com.example.projectcalculation.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
    private static Connection connection;

    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String db_username;
    @Value("${spring.datasource.password}")
    String db_password;

    public Connection getConnection() {
        if (connection == null){
            try{
                connection = DriverManager.getConnection(db_url, db_username, db_password);
            }catch (SQLException e){
                System.out.println("Could not connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }
}

```

We use this Connector in all of our classes to establish a single connection, that stays open until the person terminates the program.

## Test database and production database
We have two separate applications.properties files.
- applications.properties.
    - This is our production database that goes live. This database should **never** be altered until the implementations has been tested on our test database, passed all of its test, and every team member have approved the changes.
- applications-local.properties.
    - This is our developer database we run local. This database is **only** for experimental purposes.
- applications-test.properties.
    - This is our test database that we run tests on. This database is **only** for experimental purposes, and can freely be tested on. If major changes to the database is required for a function to work, consult the rest of the team before making these changes, to avoid work going to waste.


## Render
We always have the newest build active on render because of our workflow. When a push happens from a feature branch, it will activate a workflow. If that workflow passes, the new feature branch will be pushed to main, and render will automatically build a container from an image based on our **docker file**, and make it accessible online.
The YML file, docker file, and link to render can be found in the repository on github, or be directly access from here:

[YML file]().

[Render](https://loclahost/login).

Since we are using the free version of Render, keep in mind that it is very slow and the initial startup will take a
couple of minutes and afterwards it will still be quite slow.


# Code style guide for java classes, html files, and SQL

## SQL
- We keep two separate folders, with two sql files in each.
    - productionDatabase folder - here we have the production data for MySQL.
        - 1create-database.sql - this file contains the creating of all the tables.
        - 2insert-data-sql - this file contains the data that gets put into the tables.
    - testDatabase folder - here we have the test data for MySQL.
        - 1create-test-database.sql - this file contains the creating of all the tables.
        - 2insert-test-data-sql - this file contains the data that gets put into the tables.
- Our writing conventions for MySQL and sql files
    - When naming tables, we write the entire name in lower camel case, separating each full word with an underscore _
    - When naming columns in a table, we follow the same convention, as we do with naming tables.
- Calling conventions when retrieving data from the database.
    - Type all SQL command in uppercase for its entire word.
    - When making calls in a repository function, and the length exceeds half the length
      of the screen, we use linebreak and write the rest of the call, on the next line.
- All order functions is being handled at the backend, before the data gets send back to the front end.



## Java
- The name of the function will always reflect what the function does.
- When making a new function, always make it under its respective package.
    - For example, when creating a function that retrieves all "projects", the function should be implemented in the "ProjectRepository" because it has something to do with projects.
- When making a function, always put it under its correct category. If a fitting headline does not exist, inform team so the team can find a new suitable name for the new category.
- All our SQL Strings will always be called **SQL**
- Always leave room when you reach a new "part" of a function.
    - Example can be seen here:
      ```
        public void createProject(ProjectModel newProject) {
        try {
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO project (project_name, project_manager, target_date, project_description, budget) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());
            preparedStatement.setString(2, newProject.getProjectManager());
            preparedStatement.setDate(3, Date.valueOf(newProject.getTargetDate()));
            preparedStatement.setString(4, newProject.getProjectDescription());
            preparedStatement.setLong(5, newProject.getBudget());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add Project");
            e.printStackTrace();
        }
  }
      ```
- When creating any instances of any object, name the instance the same as what it represents, with the first letter being lowercase, and follow each new word withing it, with a uppercase.
    - **Example** --> Instance of PreparedStatement, should be named preparedStatement. Prepare is the first word, so it should be lowercase, while statement is the next word, and should therefore be uppercase. Example above, also shows this is practice.

## HTML files

We have four categories of HTML files.
- Create.
- Update.
- Show.
- delete

All our categories follows the same coding style, since they do the same, but with different objects. We use thymeleaf to add them as models, so we can use thymeleaf to access the objects data, in our HTML file.

Example of one of our HTML create files.

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments/navbar :: head('Create project')">
</head>
<body >

<div th:replace="fragments/navbar :: navbarthree"></div>

<div th:replace="fragments/navbar :: secondnavbar"></div>
<div id="backlog-background2">
  <div class="generalcontainer">
    <div class="overlaytitle">
      <h1>Create Project</h1>
    </div>
    <div class="table-container">
      <div class="columnitems">
        <div>
          <form action="/project/save" method="post">
            <label for="project_name">Project Name</label>
            <p><input id="project_name" name="projectName" type="text" required></p>

            <label for="project_description">Project Description</label>
            <p><input id="project_description" name="projectDescription" type="text" required></p>

            <label for="project_owner">Project Manager</label>
            <p><input id="project_owner" name="projectManager" type="text" required></p>

            <label for="targetDate">Target Date</label>
            <p><input id="targetDate" type="date" name="targetDate" value="2024-01-01" required></p>
            <br>
            <label for="budget">Budget</label>
            <p><input id="budget" name="budget" type="text" required></p>
            </br>
            <p><input class="signup-login-button" value="Create Project" type="submit"> </p>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<div th:replace="fragments/footer :: footer"></div>
</body>
</html>
```
