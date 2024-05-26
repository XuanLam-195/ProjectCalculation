
CREATE DATABASE IF NOT EXISTS `project_calculation`;
USE `project_calculation`;


CREATE TABLE IF NOT EXISTS `project` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) NOT NULL,
    `project_description` varchar(255) NOT NULL,
    `project_manager` varchar(225) NOT NULL,
    `project_status` tinyint(1) NOT NULL DEFAULT '1',
    `target_date` date NOT NULL,
    `budget` bigint(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `project` (`id`, `project_name`, `project_description`, `project_manager`, `project_status`, `target_date`, `budget`) VALUES
    (1, 'Project test', 'Project Description', 'Lam', 1, '2024-01-03', 100000);


CREATE TABLE IF NOT EXISTS `subproject` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) NOT NULL,
    `project_id` bigint(11) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `FK_subproject_project` (`project_id`),
    CONSTRAINT `FK_subproject_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



INSERT INTO `subproject` (`id`, `project_name`, `project_id`) VALUES
    (1, 'Sub project 123', 1);

CREATE TABLE IF NOT EXISTS `users` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `firstname` varchar(255) NOT NULL,
    `lastname` varchar(255) NOT NULL,
    `permission_level` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`),
    UNIQUE KEY `email` (`email`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users`(id, email, `password`, firstname, lastname, permission_level)
VALUES(01,'Peterlin195@yahoo.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Xuan Loc','Lam', 'project_worker');

INSERT INTO `users`(id, email, `password`, firstname, lastname, permission_level)
VALUES(03,'Jasonwang@yahoo.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Jason','Wang', 'EMPLOYEE');

INSERT INTO `users`(id, email, `password`, firstname, lastname, permission_level)
VALUES(02,'Admin@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Søren','Petersen', 'ADMINISTRATOR');

CREATE TABLE IF NOT EXISTS `task` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `subproject_id` bigint(11) NOT NULL,
    `task_name` varchar(255) NOT NULL,
    `task_description` varchar(255) NOT NULL,
    `planned_start_date` date NOT NULL,
    `planned_finish_date` date NOT NULL,
    `duration` int(11) DEFAULT NULL,
    `assigned` bigint(11) NOT NULL,
    `actual_hours` int(11) DEFAULT NULL,
    `actual_resource_cost` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_task_subproject` (`subproject_id`),
    KEY `FK_task_users` (`assigned`),
    CONSTRAINT `FK_task_subproject` FOREIGN KEY (`subproject_id`) REFERENCES `subproject` (`id`),
    CONSTRAINT `FK_task_users` FOREIGN KEY (`assigned`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `task` (`id`, `subproject_id`, `task_name`, `task_description`, `planned_start_date`, `planned_finish_date`, `duration`, `assigned`, `actual_hours`, `actual_resource_cost`) VALUES
    (1, 1, 'Task 1', 'Description task 1', '2024-01-01', '2024-01-04', 4, 1, 8, 2000),
    (2, 1, 'Task 2', 'Description task 2', '2024-01-05', '2024-01-10', 5, 1, 15, 1000);


