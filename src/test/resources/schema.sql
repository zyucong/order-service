DROP TABLE `orders` IF EXISTS;
CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,
    `origin_latitude` varchar(100) NOT NULL,
    `origin_longitude` varchar(100) NOT NULL,
    `destination_latitude` varchar(100) NOT NULL,
    `destination_longitude` varchar(100) NOT NULL,
    `distance` int NOT NULL,
    `status` tinyint NOT NULL,
    `create_time` bigint DEFAULT NULL,
    `update_time` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;