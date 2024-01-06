# CREATE TABLE IF NOT EXISTS `products`
# (
#     `id`            varchar(255)   NOT NULL,
#     `description`   varchar(255)   NOT NULL,
#     `restaurant_id` varchar(255)   NOT NULL,
#     `cost`          decimal(10, 2) NOT NULL,
#     PRIMARY KEY (`id`)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;

INSERT INTO `products` (id, description, restaurant_id, cost)
VALUES ('abc123', 'Product 1', 'res1', 100.00),
       ('abc124', 'Product 2', 'res1', 200.00),
       ('abc125', 'Product 3', 'res1', 300.00),
       ('abc126', 'Product 4', 'res1', 400.00),
       ('abc127', 'Product 5', 'res1', 500.00),
       ('abc128', 'Product 6', 'res1', 600.00);

