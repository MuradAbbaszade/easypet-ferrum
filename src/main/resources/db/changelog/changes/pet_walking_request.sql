CREATE TABLE `pet_walking_request` (
                                       `id` integer AUTO_INCREMENT PRIMARY KEY,
                                       `customer_id` integer,
                                       `suitable_date` datetime,
                                       FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);