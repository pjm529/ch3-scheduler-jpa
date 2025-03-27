CREATE TABLE `member` (
      `id` bigint NOT NULL AUTO_INCREMENT,
      `email` varchar(255) NOT NULL,
      `name` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      `created_date` datetime(6) NOT NULL,
      `modified_date` datetime(6) DEFAULT NULL,
      `deleted` bit(1) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `schedule` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `title` varchar(255) NOT NULL,
        `contents` varchar(1024) NOT NULL,
        `member_id` bigint NOT NULL,
        `created_date` datetime(6) NOT NULL,
        `modified_date` datetime(6) DEFAULT NULL,
        `deleted` bit(1) NOT NULL,
        PRIMARY KEY (`id`),
        KEY `idx_schedule_01` (`member_id`),
        CONSTRAINT `FK_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reply` (
         `id` bigint NOT NULL AUTO_INCREMENT,
         `contents` varchar(1024) NOT NULL,
         `member_id` bigint NOT NULL,
         `schedule_id` bigint NOT NULL,
         `deleted` bit(1) NOT NULL,
         `created_date` datetime(6) NOT NULL,
         `modified_date` datetime(6) DEFAULT NULL,
         PRIMARY KEY (`id`),
         KEY `idx_reply_01` (`schedule_id`),
         KEY `idx_reply_02` (`member_id`),
         CONSTRAINT `FK_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
         CONSTRAINT `FK_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;