CREATE TABLE `customer` (
                             `id` integer AUTO_INCREMENT PRIMARY KEY,
                             `name` varchar(255),
                             `surname` varchar(255),
                             `user_id` integer,
                             FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);