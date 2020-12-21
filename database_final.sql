SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pword` varchar(100) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id_login` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id_project_idx` (`user_id`),
  CONSTRAINT `user_id_projectrel` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `subproject`;
CREATE TABLE `subproject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `project_id_idx` (`project_id`),
  CONSTRAINT `project_id_rel` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subproject_id` int NOT NULL,
  `workhours_needed` int DEFAULT NULL,
  `extra_costs` int DEFAULT NULL,
  `man_hour_cost` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `subproject_id_idx` (`subproject_id`),
  CONSTRAINT `task_to_subproject` FOREIGN KEY (`subproject_id`) REFERENCES `subproject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `project_object_info`;
CREATE TABLE `project_object_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `deadline` date DEFAULT NULL,
  `baseline_man_hour_cost` int DEFAULT NULL,
  `baseline_hours_pr_workday` int DEFAULT NULL,
  `total_work_hours` int DEFAULT NULL,
  `total_work_days` int DEFAULT NULL,
  `est_finished_by_date` date DEFAULT NULL,
  `deadline_difference` varchar(45) DEFAULT NULL,
  `change_to_workhours_needed` int DEFAULT NULL,
  `est_total_cost` int DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `subproject_id` int DEFAULT NULL,
  `task_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `project_id_idx` (`project_id`),
  KEY `subproject_id_idx` (`subproject_id`),
  KEY `task_id_idx` (`task_id`),
  KEY `subproject_id_idxxx` (`subproject_id`),
  KEY `task_id_idxxx` (`task_id`),
  KEY `subproject_id_idxx` (`subproject_id`),
  KEY `task_id_idxx` (`task_id`),
  CONSTRAINT `poi_to_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `poi_to_subproject` FOREIGN KEY (`subproject_id`) REFERENCES `subproject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `poi_to_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
SET foreign_key_checks = 1;




