CREATE SCHEMA IF NOT EXISTS `project_calculation`;
DROP TABLE IF EXISTS project CASCADE;
CREATE TABLE IF NOT EXISTS `project`
(
    `id`                  bigint(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `project_name`        varchar(255) NOT NULL,
    `project_description` varchar(255) NOT NULL,
    `project_manager`     varchar(225) NOT NULL,
    `project_status`      tinyint(1)   NOT NULL DEFAULT '1',
    `target_date`         date         NOT NULL,
    `budget`              bigint(11)   NOT NULL
    );
INSERT INTO `project` (`id`, `project_name`, `project_description`, `project_manager`, `project_status`,
                       `target_date`, `budget`)
VALUES (1, 'Project test', 'Project Description', 'Lam', 1, '2024-01-03', 100000);

DROP TABLE IF EXISTS subproject CASCADE;
CREATE TABLE `subproject`
(
    `id`           bigint(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `project_name` varchar(255) NOT NULL,
    `project_id`   bigint(11)   NOT NULL DEFAULT '0',
    CONSTRAINT FK_subproject_project FOREIGN KEY (project_id)
        REFERENCES project (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `subproject` (`id`, `project_name`, `project_id`)
VALUES (1, 'Sub project 123', 1);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE `users`
(
    `id`               bigint(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email`            varchar(255) NOT NULL,
    `password`         varchar(255) NOT NULL,
    `firstname`        varchar(255) NOT NULL,
    `lastname`         varchar(255) NOT NULL,
    `permission_level` varchar(50)  NOT NULL,
    UNIQUE (email)
);
INSERT INTO `users` (`id`, `email`, `password`, `firstname`, `lastname`, `permission_level`)
VALUES (1, 'employee@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'Xuan Loc', 'Lam', 'EMPLOYEE'),
       (2, 'user@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'Jason', 'Wang', 'EMPLOYEE'),
       (3, 'admin@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'Søren', 'Petersen', 'ADMINISTRATOR');
DROP TABLE IF EXISTS task CASCADE;
CREATE TABLE `task`
(
    `id`                   bigint(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `subproject_id`        bigint(11)   NOT NULL,
    `task_name`            varchar(255) NOT NULL,
    `task_description`     varchar(255) NOT NULL,
    `planned_start_date`   date         NOT NULL,
    `planned_finish_date`  date         NOT NULL,
    `duration`             int(11)    DEFAULT NULL,
    `assigned`             bigint(11)   NOT NULL,
    `actual_hours`         int(11)    DEFAULT NULL,
    `actual_resource_cost` bigint(20) DEFAULT NULL,
    CONSTRAINT FK_task_subproject FOREIGN KEY (subproject_id)
        REFERENCES subproject (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_task_users FOREIGN KEY (assigned)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `task` (`id`, `subproject_id`, `task_name`, `task_description`, `planned_start_date`,
                    `planned_finish_date`, `duration`, `assigned`, `actual_hours`, `actual_resource_cost`)
VALUES (1, 1, 'Task 2', 'Description task 2', '2024-01-05', '2024-01-10', 5, 1, 15, 1000);



