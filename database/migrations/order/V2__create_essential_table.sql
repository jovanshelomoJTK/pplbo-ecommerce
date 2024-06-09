-- Drop the existing ENUM types if they exist
DROP TYPE IF EXISTS OrderStatus;
DROP TYPE IF EXISTS ShippingStatus;

-- Create the ENUM type for order status
CREATE TYPE OrderStatus AS ENUM (
    'PENDING',
    'PAID',
    'CANCELLED'
);

-- Create the ENUM type for shipping status
CREATE TYPE ShippingStatus AS ENUM (
    'PENDING',
    'PACKED',
    'SHIPPED',
    'DELIVERED',
    'RETURNED'
);

-- Drop the existing tables if they exist
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS shippings;

-- Create the shippings table
CREATE TABLE shippings (
    shipping_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    shipping_name VARCHAR(255) NOT NULL,
    shipping_price DOUBLE PRECISION NOT NULL,
    shipping_status ShippingStatus NOT NULL,
    shipping_address VARCHAR(255) NOT NULL
);

-- Create the orders table
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_date TIMESTAMP NOT NULL,
    order_status OrderStatus NOT NULL,
    total_price DOUBLE PRECISION,
    shipping_id BIGINT,
    CONSTRAINT fk_shipping
        FOREIGN KEY (shipping_id) 
        REFERENCES shippings (shipping_id)
        ON DELETE SET NULL
);

-- Create the order_items table
CREATE TABLE order_items (
    order_item_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id BIGINT,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) 
        REFERENCES orders (order_id)
        ON DELETE CASCADE
);

-- Insert sample data into orders table
INSERT INTO orders (order_date, order_status, total_price, shipping_id) 
VALUES ('2024-05-30 00:00:00', 'PENDING', NULL, NULL);

-- Retrieve the generated order ID
WITH new_order AS (
    SELECT order_id FROM orders WHERE order_date = '2024-05-30 00:00:00'
)
-- Insert sample data into order_items table using the retrieved order ID
INSERT INTO order_items (order_id, product_id, quantity, price)
SELECT order_id, 1, 2, 10.00 FROM new_order;

INSERT INTO order_items (order_id, product_id, quantity, price)
SELECT order_id, 2, 1, 20.00 FROM new_order;
