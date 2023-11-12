CREATE TABLE `job_info` (
                            `id` integer AUTO_INCREMENT PRIMARY KEY,
                            `company` varchar(255),
                            `duration` integer,
                            `veterinar_id` integer,
                            FOREIGN KEY (`veterinar_id`) REFERENCES `veterinar` (`id`)
);

