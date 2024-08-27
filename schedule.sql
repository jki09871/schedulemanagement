CREATE TABLE `schedule`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `content`    varchar(255) NOT NULL,
    `title`      varchar(255) NOT NULL,
    `user_id`    bigint       NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKa50n59y1j4a6qwa42p8jiguds` (`user_id`),
    CONSTRAINT `FKa50n59y1j4a6qwa42p8jiguds` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `schedule_comment`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) DEFAULT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    `content`     varchar(255) NOT NULL,
    `user_name`   varchar(255) NOT NULL,
    `schedule_id` bigint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKrp3hxgltetev3lqtvub0bjtg6` (`schedule_id`),
    CONSTRAINT `FKrp3hxgltetev3lqtvub0bjtg6` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
);
CREATE TABLE `user`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `email`      varchar(255) NOT NULL,
    `username`   varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `user_schedule`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) DEFAULT NULL,
    `schedule_id` bigint      DEFAULT NULL,
    `user_id`     bigint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKdd4cwg959bmy4551iiivx4wdw` (`schedule_id`),
    KEY `FKmsyiiyw4bv8y8sv4dbh6k481a` (`user_id`),
    CONSTRAINT `FKdd4cwg959bmy4551iiivx4wdw` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
    CONSTRAINT `FKmsyiiyw4bv8y8sv4dbh6k481a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);