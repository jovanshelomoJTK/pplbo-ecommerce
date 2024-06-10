CREATE TYPE OrderStatus AS ENUM (
    'PENDING',
    'PAID',
    'CANCELLED'
);

CREATE TABLE IF NOT EXISTS Orders (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer_id BIGINT,
    order_date DATE,
    order_status VARCHAR(255),
    shipping_address VARCHAR(255),
    order_total DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS order_items (
    order_item_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id BIGINT REFERENCES Orders(order_id) ON DELETE CASCADE,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(10, 2)
);

INSERT INTO Orders (customer_id, order_date, order_status, shipping_address, order_total) VALUES (1, '2024-05-30', 'PENDING', '123 Main St', 80000.00);
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 1, 2, 10.00);
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 2, 1, 20.00);
