-- Create the ENUM type for order status
CREATE TYPE OrderStatus AS ENUM (
    'PENDING',
    'PAID',
    'CANCELLED'
);

-- Create the shippings table
CREATE TABLE IF NOT EXISTS shippings (
    shipping_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    shipping_name VARCHAR(255) NOT NULL,
    shipping_price DOUBLE NOT NULL,
    shipping_status VARCHAR(255) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL
);

-- Create the Orders table
CREATE TABLE IF NOT EXISTS Orders (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_date TIMESTAMP NOT NULL,
    order_status OrderStatus NOT NULL,
    total_price DOUBLE,
    shipping_id BIGINT,
    shipping_address VARCHAR(255),
    CONSTRAINT fk_shipping
        FOREIGN KEY (shipping_id) 
        REFERENCES shippings (shipping_id)
        ON DELETE SET NULL
);

-- Create the order_items table
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id BIGINT,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) 
        REFERENCES Orders (order_id)
        ON DELETE CASCADE
);

-- Insert sample data into Orders table
INSERT INTO Orders (order_date, order_status, total_price, shipping_address) 
VALUES ('2024-05-30 00:00:00', 'PENDING', NULL, '123 Main St');

-- Retrieve the generated order ID
WITH new_order AS (
    SELECT order_id FROM Orders WHERE order_date = '2024-05-30 00:00:00' AND shipping_address = '123 Main St'
)
-- Insert sample data into order_items table using the retrieved order ID
INSERT INTO order_items (order_id, product_id, quantity, price)
SELECT order_id, 1, 2, 10.00 FROM new_order;

INSERT INTO order_items (order_id, product_id, quantity, price)
SELECT order_id, 2, 1, 20.00 FROM new_order;
