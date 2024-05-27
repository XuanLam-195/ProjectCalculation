# ProjectCalculation Tool

## A fully minimalistic planning tool for projects of all sizes

This project has been carried out by two programming students as part of their second semester examinations. The project was realized in collaboration with our educational institution (Københavns Erhvers Akademi) and Alpha Solutions. We've developed a planning tool that allows companies to structure their projects and break them down into smaller components. The purpose of this project is to create a planning tool that is simple, quick, and intuitive to use, and isn't burdened with unnecessary features.

Some of the highlights of our program is:

* Role system to deefaault are ADMINISTRATOR and EMPLOYEE.
* Intuitive design and "helping text" to clarify what inputs is legal.
* Build-in safety features in front-end to make sure users cant input illegal inputs.
* Fail-safe options, that will take user to login if and unexpected error happens.


### Program features

Our program represents a CRUD system, which stands for create, read, update, and delete. The program consists of 4 major parts.

* Users, that can be one of two roles.

1. [ ] EMPLOYEE, this role is the normal role, with limited functionality and access to sites.
2. [ ] ADMINISTRATOR, this is the administrator role with full access to functions and sites.

* Projects.
* Subprojects, that are connected to its own single project.
* Tasks, that are connected to its own single subproject.
* Projects, Subprojects, tasks, and users have all the CRUD commands.

### Roles and their features

#### EMPLOYEES

EMPLOYEES are the standard role, and do only have access to the tasks page. On the task page they will only see the tasks they have been assigned too. Besides that, they can do the following.

* See list of all assigned members to the desired task.
* Update any of the task's information.
* Delete any task.
* See the description of any task.
* See list of all project
* See list of all sub Project
* See list of all task
* See list of number of hours of employee

#### ADMINISTRATOR

ADMINISTRATOR role have access to all functions and sites of the program. In addition to the previous mentioned functions, they can also do/access the following.

* CRUD any number of projects, subprojects and tasks, User.
* See all information of any projects, subprojects and tasks.
* Assign members to any amount of tasks.
* Unassigned members to any amount of tasks.