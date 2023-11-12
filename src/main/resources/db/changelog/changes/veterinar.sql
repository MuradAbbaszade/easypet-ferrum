CREATE TABLE `veterinar` (
                             `id` integer AUTO_INCREMENT PRIMARY KEY,
                             `name` varchar(255),
                             `surname` varchar(255),
                             `user_id` integer,
                             `location` varchar(255),
                             `phone_number` varchar(255),
                             FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);