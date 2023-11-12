CREATE TABLE `user_authority` (
                        `authority_id` integer,
                        `user_id` integer,
                        FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
                        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);