CREATE TABLE `pet_walker_announcement` (
                                           `id` integer AUTO_INCREMENT PRIMARY KEY,
                                           `pet_walker_id` integer,
                                           `suitable_date` datetime,
                                           FOREIGN KEY (`pet_walker_id`) REFERENCES `pet_walker` (`id`)
);