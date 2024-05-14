DELETE FROM orders;
insert into `orders` (`id`, `user_id`, `product_id`, `unit_price`, `qty`, `total_price`)
values ('UUID','testId', 'testpid',5000,10,500000);